package cat.xarxacatala.xarxacatalapp.showDetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import cat.xarxacatala.xarxacatalapp.BaseFragment
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatala.xarxacatalapp.XarxaCatApp
import cat.xarxacatala.xarxacatalapp.di.injectViewModel
import cat.xarxacatalapp.core.CallResult
import cat.xarxacatalapp.core.models.Season
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_show_detail.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class ShowDetailFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ShowDetailViewModel

    val args: ShowDetailFragmentArgs by navArgs()

    private lateinit var episodesAdapter: EpiodesListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application as XarxaCatApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = injectViewModel(viewModelFactory)
        viewModel.showId = args.showId

        //val adapter = ShowsListAdapter()
        //spnSeasons.adapter = adapter

        return inflater.inflate(R.layout.fragment_show_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        episodesAdapter = EpiodesListAdapter()
        rvEpisodes.layoutManager = LinearLayoutManager(requireContext())
        rvEpisodes.adapter = episodesAdapter

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.show.observe(viewLifecycleOwner, Observer { result ->
            if (result.status == CallResult.Status.SUCCESS) {
                result.data?.let {
                    tvShowName.text = it.name
                }
            }
        })

        viewModel.seasons.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                CallResult.Status.SUCCESS -> {
                    //pbLoading.visibility = View.GONE
                    //binding.progressBar.hide()
                    result.data?.let { seasons ->

                        val adapter = SeasonsAdapter(
                            requireContext(),
                            seasons
                        )

                        spnSeasons.adapter = adapter
                    }
                }
                //Result.Status.LOADING -> pbLoading.visibility = View.VISIBLE
                CallResult.Status.ERROR -> {
                    //pbLoading.visibility = View.GONE
                    Snackbar.make(rootView, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        spnSeasons.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                loadEpisodes(
                    spnSeasons.adapter.getItem(
                        position
                    ) as Season
                )
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }
    }

    private fun loadEpisodes(season: Season) {
        viewModel.seasonId = season.id

        viewModel.episodes.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                CallResult.Status.SUCCESS -> {
                    result.data?.let { episodes ->
                        episodesAdapter.submitList(episodes)
                    }
                }
                //Result.Status.LOADING -> pbLoading.visibility = View.VISIBLE
                CallResult.Status.ERROR -> {
                    //pbLoading.visibility = View.GONE
                    Snackbar.make(rootView, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
