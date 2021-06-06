/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cat.xarxacatala.xarxacatalapp

import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cat.xarxacatala.xarxacatalapp.cast.CastManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * An activity that inflates a layout that has a [BottomNavigationView].
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var castManager: CastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }

            if (destination.id == R.id.videoPlayerFragment) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

                fullScreen()

            } else {
                castManager.castPlayerLiveData.value?.let {
                    cvMiniPlayer.visibility = VISIBLE
                }

                showSystemUI()

                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                bottomNav.visibility = VISIBLE
            }

            if (destination.id == R.id.castFragment || destination.id == R.id.videoPlayerFragment) {
                cvMiniPlayer.visibility = GONE
            }


            Log.d("NavigationActivity", "Navigated to $dest")
        }

        castManager.castPlayerLiveData.observe(this, Observer { castPlayer ->
            if (castPlayer != null) {
                cvMiniPlayer.visibility = VISIBLE
                buttonPlayPause.setOnClickListener {
                    castPlayer.playWhenReady = !castPlayer.isPlaying
                }
            } else {
                cvMiniPlayer.visibility = GONE
            }
        })

        castManager.progressLiveData.observe(this, Observer { progress ->
            if (progress > -1) {
                pbCastProgress.progress = progress
            }
        })

        imageView.setOnClickListener {
            navController.navigate(R.id.castFragment)
        }
    }

    fun fullScreen() {
        bottomNav.visibility = GONE

        hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }


    private fun setupBottomNavMenu(navController: NavController) {
        bottomNav?.setupWithNavController(navController)
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_container))
                || super.onOptionsItemSelected(item)
    }
}
