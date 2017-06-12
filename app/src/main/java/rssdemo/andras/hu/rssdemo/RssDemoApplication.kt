package rssdemo.andras.hu.rssdemo

import android.app.Application

import rssdemo.andras.hu.rssdemo.di.Injector

class RssDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}
