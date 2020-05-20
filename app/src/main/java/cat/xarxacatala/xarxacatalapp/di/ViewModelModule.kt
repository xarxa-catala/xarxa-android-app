package cat.xarxacatala.xarxacatalapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.xarxacatala.xarxacatalapp.showDetail.ShowDetailViewModel
import cat.xarxacatala.xarxacatalapp.shows.ShowsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ShowsViewModel::class)
    abstract fun bindShowsViewModel(viewModel: ShowsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShowDetailViewModel::class)
    abstract fun bindShowDetailViewModel(viewModel: ShowDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}