package cat.xarxacatala.xarxacatalapp.showDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatalapp.core.models.Playlist


class PlaylistsAdapter(context: Context, playlists: List<Playlist>) :
    ArrayAdapter<Playlist>(context, R.layout.adapter_playlists, playlists) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return setViewPlaylistName(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return setViewPlaylistName(position, convertView, parent)
    }

    private fun setViewPlaylistName(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem = convertView as TextView?
        if (listItem == null)
            listItem = LayoutInflater.from(context)
                .inflate(R.layout.adapter_playlists, parent, false) as TextView?


        val playlistName = getItem(position)!!.name
        listItem?.text = playlistName

        return listItem!!
    }
}