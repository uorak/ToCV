package pl.orak.tocv;

import android.app.Application;
import android.content.Context;

import pl.orak.tocv.di.AppComponent;
import pl.orak.tocv.di.AppModule;
import pl.orak.tocv.di.utils.Dagger2Helper;
import pl.orak.tocv.di.utils.MockSetting;

/**
 * Created by Tomek on 2015-04-27.
 */
public class ToCvApp extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent(this, false);
    }

    public static void initComponent(Context context, boolean mock) {
        if (mock) {
            component = Dagger2Helper.buildComponent(AppComponent.class, new AppModule(context, new MockSetting(true)));
        } else {
            component = Dagger2Helper.buildComponent(AppComponent.class, new AppModule(context));
        }
    }


    public static void inject(Object target) {
        Dagger2Helper.inject(AppComponent.class, component, target);
    }

}
