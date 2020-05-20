package cat.xarxacatala.xarxacatalapp

import android.content.pm.ActivityInfo
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {

    override fun onResume() {
        super.onResume()

        val a = activity as MainActivity?
        if (a != null) {
            a.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
            a.removeFullScreen()
        }
    }
}