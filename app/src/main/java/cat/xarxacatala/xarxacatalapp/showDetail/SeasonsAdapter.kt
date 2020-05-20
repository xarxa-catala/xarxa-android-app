package cat.xarxacatala.xarxacatalapp.showDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatalapp.core.models.Season


class SeasonsAdapter(context: Context, seasons: List<Season>) :
    ArrayAdapter<Season>(context, R.layout.adapter_seasons, seasons) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return setViewSeasonName(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return setViewSeasonName(position, convertView, parent)
    }

    private fun setViewSeasonName(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem = convertView as TextView?
        if (listItem == null)
            listItem = LayoutInflater.from(context)
                .inflate(R.layout.adapter_seasons, parent, false) as TextView?


        val seasonName = getItem(position)!!.name
        listItem?.text = seasonName

        return listItem!!
    }
}