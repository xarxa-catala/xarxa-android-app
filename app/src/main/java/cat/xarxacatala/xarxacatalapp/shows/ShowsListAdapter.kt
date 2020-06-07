package cat.xarxacatala.xarxacatalapp.shows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatalapp.core.models.Show
import com.bumptech.glide.Glide

class ShowsListAdapter(val context: Context) : ListAdapter<Show, ShowsListAdapter.ShowViewHolder>(
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
        private var tvShowTitle: TextView = itemView.findViewById(R.id.tvShowTitle)
        private var ivShowThumbnail: ImageView = itemView.findViewById(R.id.ivShowThumbnail)


        fun bind(show: Show) {
            tvShowTitle.text = show.name

            Glide.with(context)
                .load(show.thumbnail)
                .centerCrop()
                .into(ivShowThumbnail)

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
