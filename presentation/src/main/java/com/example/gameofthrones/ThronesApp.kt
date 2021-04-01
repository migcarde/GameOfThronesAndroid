package com.example.gameofthrones

import android.app.Application
import com.example.commons.di.CommonsKoinConfiguration
import com.example.commons_android.di.CommonAndroidKoinConfiguration
import com.example.data.DataKoinConfiguration
import com.example.domain.di.DomainKoinConfiguration
import com.example.gameofthrones.di.PresentationKoinConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ThronesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ThronesApp)
            modules(
                listOf(
                    CommonsKoinConfiguration().getModule(),
                    CommonAndroidKoinConfiguration(BuildConfig.FLAVOR).getModule(),
                    DataKoinConfiguration(BuildConfig.BASE_URL).getModule(),
                    DomainKoinConfiguration().getModule(),
                    PresentationKoinConfiguration().getModule()
                )
            )
        }
    }
}