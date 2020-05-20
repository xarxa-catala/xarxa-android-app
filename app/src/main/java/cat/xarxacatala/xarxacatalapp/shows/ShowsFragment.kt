package cat.xarxacatala.xarxacatalapp.shows

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cat.xarxacatala.xarxacatalapp.BaseFragment
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatala.xarxacatalapp.XarxaCatApp
import cat.xarxacatala.xarxacatalapp.di.injectViewModel
import cat.xarxacatalapp.core.CallResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_shows.*
import javax.inject.Inject

class ShowsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ShowsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application as XarxaCatApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)

        return inflater.inflate(R.layout.fragment_shows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvShows.layoutManager = LinearLayoutManager(context)
        val adapter = ShowsListAdapter()
        rvShows.adapter = adapter
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: ShowsListAdapter) {
        viewModel.shows.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                CallResult.Status.SUCCESS -> {
                    pbLoading.visibility = GONE
                    //binding.progressBar.hide()
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
