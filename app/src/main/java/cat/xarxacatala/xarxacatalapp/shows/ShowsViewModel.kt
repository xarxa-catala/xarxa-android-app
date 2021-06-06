package cat.xarxacatala.xarxacatalapp.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import cat.xarxacatalapp.core.CallResult
import cat.xarxacatalapp.core.models.Show
import cat.xarxacatalapp.core.repository.ShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(showsRepository: ShowsRepository) : ViewModel() {
    val shows: LiveData<CallResult<List<Show>>> by lazy {
        showsRepository.shows.asLiveData()
    }
}
