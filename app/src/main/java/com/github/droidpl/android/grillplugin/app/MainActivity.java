package com.github.droidpl.android.grillplugin.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.droidpl.android.app.R;

/**
 * The main activity class to display our fancy changing button.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainViewTranslator {

    /**
     * The label test that will be shown and hidden.
     */
    private TextView mText;

    /**
     * The presenter for this activity.
     */
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button_show);
        mText = (TextView) findViewById(R.id.count);
        button.setOnClickListener(this);
        mPresenter = new MainPresenter(this);
    }

    @Override
    public void onClick(View v) {
        mPresenter.increment();
    }

    @Override
    public void setIncrementalValue(int value) {
        mText.setText(String.valueOf(value));
    }
}
