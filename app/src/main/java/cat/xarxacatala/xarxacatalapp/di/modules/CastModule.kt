package cat.xarxacatala.xarxacatalapp.di.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import cat.xarxacatala.xarxacatalapp.di.ViewModelKey
import cat.xarxacatala.xarxacatalapp.shows.ShowsViewModel
import com.google.android.gms.cast.framework.CastContext
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class CastModule {
    @Provides
    fun provideCastContext(context: Context): CastContext = CastContext.getSharedInstance(context)
}
