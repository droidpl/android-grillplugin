package com.mobgen.myapplication;

import com.github.droidpl.android.grillplugin.app.MainPresenter;
import com.github.droidpl.android.grillplugin.app.MainViewTranslator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class PresenterUnitTest {

    @Test
    public void presenterAdditionTest() throws Exception {
        final boolean[] incremented = new boolean[1];
        MainViewTranslator translator = mock(MainViewTranslator.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                incremented[0] = true;
                return true;
            }
        }).when(translator).setIncrementalValue(1);
        MainPresenter presenter = new MainPresenter(translator);
        presenter.increment();
        assertTrue(incremented[0]);
    }
}