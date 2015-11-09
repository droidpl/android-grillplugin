package com.droidpl.android.bbqpluginapp;

/**
 * Main presenter for the Main Application.
 */
public class MainPresenter {

    /**
     * The view translator for this presenter.
     */
    private MainViewTranslator mViewTranslator;

    /**
     * The value stored in the presenter that will be displayed as incremental one.
     */
    private int mValue;

    /**
     * Creates the view translator with the value.
     * @param translator The translator.
     */
    public MainPresenter(MainViewTranslator translator){
        mViewTranslator = translator;
        mValue = 0;
        increment();
    }

    /**
     * Increments a unit on the value.
     */
    public void increment(){
        mViewTranslator.setIncrementalValue(++mValue);
    }
}
