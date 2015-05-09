package pl.orak.tocv.di.utils;

import android.content.Context;

import javax.inject.Inject;

import pl.orak.tocv.ToCvApp;

/**
 * Created by Tomek on 2015-04-27.
 */
public class BaseTarget {
    @Inject
    protected Context context;

    public BaseTarget() {
        ToCvApp.inject(this);
    }
}
