package cat.xarxacatala.xarxacatalapp.showDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatalapp.core.models.Episode


class EpisodesListAdapter : ListAdapter<Episode, EpisodesListAdapter.EpisodeViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_episodes, parent, false);
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(episode: Episode) {
            itemView.findViewById<TextView>(R.id.tvEpisodeName).text = episode.name
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