package cat.xarxacatala.xarxacatalapp.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import cat.xarxacatala.xarxacatalapp.BaseFragment
import cat.xarxacatalapp.core.CallResult
import cat.xarxacatalapp.core.models.Show
import com.google.accompanist.coil.rememberCoilPainter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowsFragment : BaseFragment() {
    private val viewModel: ShowsViewModel by viewModels()
    private val navController by lazy {
        this.requireView().findNavController()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Shows(viewModel.shows)
            }
        }
        //inflater.inflate(R.layout.fragment_shows, container, false)
    }

    @Composable
    private fun Shows(showsLiveData: LiveData<CallResult<List<Show>>>) {
        val shows by showsLiveData.observeAsState(CallResult.loading())

        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)) {
            Text(
                text = "Xarxa Català",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                ),
                modifier = Modifier
                    .padding(16.dp)
            )
            if (shows.status == CallResult.Status.SUCCESS)
                LazyColumn(Modifier.fillMaxSize()) {
                    items(shows.data.orEmpty()) { show ->
                        Show(show) {
                            val action = ShowsFragmentDirections.actionHomeToShowDetailFragment(show.id)
                            navController.navigate(action)
                        }
                    }

                }
        }
    }

    @Composable
    fun Show(show: Show, onShowClick: () -> Unit) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(Color.Blue)
            .clickable { onShowClick() }
        ) {
            Image(
                painter = rememberCoilPainter(show.thumbnail),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .padding(all = 8.dp)
                    .clip(shape = RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop

            )
            Text(
                text = show.name,
                style = TextStyle(fontSize = 20.sp, color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        rvShows.layoutManager = GridLayoutManager(context, 2)
//        context?.let { context ->
//            val adapter = ShowsListAdapter(context)
//            rvShows.adapter = adapter
//            subscribeUi(adapter)
//        }
//    }
//
//    private fun subscribeUi(adapter: ShowsListAdapter) {
//        viewModel.shows.observe(viewLifecycleOwner, Observer { result ->
//
//            when (result.status) {
//                CallResult.Status.SUCCESS -> {
//                    pbLoading.visibility = GONE
//
//                    result.data?.let {
//                        adapter.submitList(it)
//                    }
//                }
//                CallResult.Status.LOADING -> pbLoading.visibility = VISIBLE
//                CallResult.Status.ERROR -> {
//                    pbLoading.visibility = GONE
//                    Snackbar.make(rootView, result.message!!, Snackbar.LENGTH_LONG).show()
//                }
//            }
//        })
//    }
}
