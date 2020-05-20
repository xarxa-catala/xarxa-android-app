package cat.xarxacatala.xarxacatalapp.shows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatalapp.core.models.Show

class ShowsListAdapter : ListAdapter<Show, ShowsListAdapter.ShowViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_shows, parent, false);
        return ShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(show: Show) {
            itemView.findViewById<TextView>(R.id.tvShowTitle).text = show.name
            itemView.setOnClickListener {
                val action = ShowsFragmentDirections.actionHomeToShowDetailFragment(show.id)
                it.findNavController().navigate(action)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Show>() {

        override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem == newItem
        }
    }
}
