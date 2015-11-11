package com.droidpl.android.bbqpluginapp;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import junit.framework.Assert;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mMainActivity;
    private TextView mText;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
        mText = (TextView) mMainActivity.findViewById(R.id.count);
    }


    public void testPreconditions(){
        Assert.assertNotNull(mText);
        Assert.assertNotNull(mMainActivity);
    }

    //TODO add simple test to check that jacoco + testing works
}