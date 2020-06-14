package cat.xarxacatala.xarxacatalapp.utils

import androidx.fragment.app.Fragment
import cat.xarxacatala.xarxacatalapp.MainActivity

fun Fragment.setToolbarTitle(title: String) {
    (activity as MainActivity?)?.supportActionBar?.title = title
}