package co.android.wframadhan.themoviesuperapp

import android.app.Application
import co.android.wframadhan.themoviesuperapp.di.AppComponent
import co.android.wframadhan.themoviesuperapp.di.DaggerAppComponent
import dagger.hilt.android.HiltAndroidApp

open class App : Application() {
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }
}