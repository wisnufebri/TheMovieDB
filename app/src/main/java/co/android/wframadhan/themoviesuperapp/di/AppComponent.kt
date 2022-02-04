package co.android.wframadhan.themoviesuperapp.di

import android.content.Context
import co.android.wframadhan.themoviesuperapp.MainActivity
import co.android.wframadhan.themoviesuperapp.di.modules.ApiModule
import co.android.wframadhan.themoviesuperapp.di.modules.AppModule
import co.android.wframadhan.themoviesuperapp.ui.detail.DetailFragment
import co.android.wframadhan.themoviesuperapp.ui.home.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: DetailFragment)

}