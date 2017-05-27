package rssdemo.andras.hu.rssdemo;

import android.app.Application;

import rssdemo.andras.hu.rssdemo.di.Injector;

public class RssDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Injector.init(this);
    }
}
