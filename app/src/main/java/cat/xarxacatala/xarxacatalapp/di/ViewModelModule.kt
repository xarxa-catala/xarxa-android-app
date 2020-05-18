package cat.xarxacatala.xarxacatalapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.xarxacatala.xarxacatalapp.shows.ShowsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ShowsViewModel::class)
    abstract fun bindThemeViewModel(viewModel: ShowsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}