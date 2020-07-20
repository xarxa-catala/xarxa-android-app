package cat.xarxacatala.xarxacatalapp.cast

import androidx.lifecycle.ViewModel
import cat.xarxacatalapp.core.di.scopes.AppScope
import javax.inject.Inject

@AppScope
class CastViewModel @Inject constructor(
    castManager: CastManager
) : CastBaseViewModel(castManager) {
}