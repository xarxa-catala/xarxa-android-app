package cat.xarxacatala.xarxacatalapp.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ShowsListAdapter : RecyclerView.Adapter<ShowsListAdapter.ShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        return ShowViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }

    inner class ShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
