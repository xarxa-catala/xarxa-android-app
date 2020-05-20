package cat.xarxacatala.xarxacatalapp

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {

    override fun onResume() {
        super.onResume()

        val a: Activity? = activity
        if (a != null) a.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }
}