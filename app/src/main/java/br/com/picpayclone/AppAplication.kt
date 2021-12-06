package br.com.picpayclone

import android.app.Application
import br.com.picpayclone.di.serviceModule
import br.com.picpayclone.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppAplication)
            modules(viewModelModule, serviceModule)
        }
    }

}