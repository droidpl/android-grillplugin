package com.github.droidpl.android.grillplugin.tasks

import com.github.droidpl.android.grillplugin.utils.Utils
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.FileContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.AndroidPublisherScopes
import com.google.api.services.androidpublisher.model.Apk
import com.google.api.services.androidpublisher.model.AppEdit
import com.google.api.services.androidpublisher.model.Track
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
/**
 * Plugin extension to allow configurations on the current plugin.
 */
public class GooglePlayPublishTask extends AbstractTaskConfigurer {

    private String privateKeyId
    private String clientEmail
    private File privateKey
    private Closure customCallback

    void privateKeyId(String privateKeyId) {
        this.privateKeyId = privateKeyId
    }

    void clientEmail(String clientEmail) {
        this.clientEmail = clientEmail
    }

    void privateKey(File privateKey) {
        this.privateKey = privateKey
    }

    void customize(Closure closure) {
        customCallback = closure
    }

    @Override
    void checkPreconditions() {
        if(!privateKey){
            throw new ProjectConfigurationException("You must provide a .pem file to upload content to the google play console. privateKey == null", null)
        }
        if(!privateKeyId){
            throw new ProjectConfigurationException("You must provide a private key id. privateKeyId == null", null)
        }
        if(!clientEmail){
            throw new ProjectConfigurationException("You must provide a client email for the email that uploads content. clientEmail == null", null)
        }
    }

    @Override
    void configureOn(Project project) {
        NetHttpTransport http = GoogleNetHttpTransport.newTrustedTransport()
        JacksonFactory json = JacksonFactory.getDefaultInstance()
        Set<String> scopes = Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER)
        GoogleCredential credential = new GoogleCredential.Builder().
                setTransport(http).
                setJsonFactory(json).
                setServiceAccountPrivateKeyId(privateKeyId).
                setServiceAccountId(clientEmail).
                setServiceAccountScopes(scopes).
                setServiceAccountPrivateKeyFromPemFile(privateKey).
                build()
        Utils.getVariants(project).all { variant ->
            project.tasks.create(name: "googlePlayPublish${variant.getName().capitalize()}", dependsOn:"assemble${variant.getName().capitalize()}") {
                group = "grill"
                String applicationId = variant.getApplicationId()
                AndroidPublisher publisher = new AndroidPublisher.Builder(http, json, credential)
                        .setApplicationName(applicationId)
                        .build()
                //Generate edit
                AndroidPublisher.Edits edits = publisher.edits()
                AppEdit edit = edits.insert(applicationId, null).execute()
                String editId = edit.getId()

                //Upload the apk
                AndroidPublisher.Edits.Apks apks = edits.apks();
                File file = variant.outputFile
                FileContent apkContent = new FileContent("application/vnd.android.package-archive", file)
                Apk apk = apks.upload(applicationId, editId, apkContent).execute()

                // Assign APK to Track
                int version = apk.getVersionCode();
                AndroidPublisher.Edits.Tracks tracks = edits.tracks()
                List<Integer> versions = Collections.singletonList(version) as List<Integer>
                Track track = new Track().setVersionCodes(versions)
                tracks.update(applicationId, editId, "production", track).execute()

                //Callback for further editions
                if (customCallback) {
                    customCallback(edit, editId, apk)
                }

                //Commit results
                edits.validate(applicationId, editId).execute()
                edits.commit(applicationId, editId).execute()
            }
        }

    }
}
