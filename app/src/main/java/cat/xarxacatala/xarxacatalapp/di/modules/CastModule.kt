package cat.xarxacatala.xarxacatalapp.di.modules

import android.content.Context
import com.google.android.gms.cast.framework.CastContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CastModule {
    @Provides
    fun provideCastContext(@ApplicationContext context: Context): CastContext = CastContext.getSharedInstance(context)
}
