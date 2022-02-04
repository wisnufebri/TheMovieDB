package co.android.wframadhan.themoviesuperapp.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

}