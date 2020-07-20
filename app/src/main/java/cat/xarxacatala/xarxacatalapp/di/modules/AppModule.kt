package cat.xarxacatala.xarxacatalapp.di.modules

import dagger.Module

@Module(
    includes = [
        ViewModelModule::class,
        CastModule::class
    ]
)
class AppModule {
}