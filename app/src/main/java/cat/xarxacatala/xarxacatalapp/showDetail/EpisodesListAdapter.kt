package cat.xarxacatala.xarxacatalapp.showDetail

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatalapp.core.models.Episode
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.adapter_episodes.view.*


class EpisodesListAdapter(val context: Context) :
    ListAdapter<Episode, EpisodesListAdapter.EpisodeViewHolder>(
        DiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_episodes, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1)
    }

    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(episode: Episode, position: Int) {

            itemView.tvEpisodeName.text = episode.name
            itemView.tvEpisodeNum.text = position.toString()

            val requestOptions = RequestOptions()
            requestOptions.isMemoryCacheable
//            Glide.with(context)
//                .setDefaultRequestOptions(requestOptions)
//                .load(episode.url)
//                .thumbnail(0.1f)
//                .centerCrop()
//                .into(ivEpisodeThumbnail)

            itemView.setOnClickListener {
                val action =
                    ShowDetailFragmentDirections.actionShowDetailFragmentToVideoPlayerFragment(
                        episode.id
                    )
                it.findNavController().navigate(action)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Episode>() {

        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }
}
