package cat.xarxacatala.xarxacatalapp.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import cat.xarxacatala.xarxacatalapp.BaseFragment
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatalapp.core.CallResult
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shows.*

@AndroidEntryPoint
class ShowsFragment : BaseFragment() {
    private val viewModel: ShowsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvShows.layoutManager = GridLayoutManager(context, 2)
        context?.let { context ->
            val adapter = ShowsListAdapter(context)
            rvShows.adapter = adapter
            subscribeUi(adapter)
        }
    }

    private fun subscribeUi(adapter: ShowsListAdapter) {
        viewModel.shows.observe(viewLifecycleOwner, Observer { result ->

            when (result.status) {
                CallResult.Status.SUCCESS -> {
                    pbLoading.visibility = GONE

                    result.data?.let {
                        adapter.submitList(it)
                    }
                }
                CallResult.Status.LOADING -> pbLoading.visibility = VISIBLE
                CallResult.Status.ERROR -> {
                    pbLoading.visibility = GONE
                    Snackbar.make(rootView, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
