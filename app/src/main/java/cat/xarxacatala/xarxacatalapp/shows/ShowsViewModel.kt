package cat.xarxacatala.xarxacatalapp.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cat.xarxacatalapp.core.Result
import cat.xarxacatalapp.core.models.Show
import cat.xarxacatalapp.core.repository.ShowsRepository
import javax.inject.Inject

class ShowsViewModel @Inject constructor(showsRepository: ShowsRepository) : ViewModel() {
    val shows: LiveData<Result<List<Show>>> = showsRepository.shows
}
