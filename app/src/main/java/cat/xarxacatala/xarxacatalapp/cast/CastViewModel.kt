package cat.xarxacatala.xarxacatalapp.cast

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    castManager: CastManager
) : CastBaseViewModel(castManager) {
}