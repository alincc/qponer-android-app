package bg.qponer.android

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import bg.qponer.android.ui.core.ToolbarHost
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ToolbarHost {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment).also {
            it.addOnDestinationChangedListener { _, destination, _ ->
                if (R.id.navigation_login == destination.id)  {
                    navView.visibility = View.INVISIBLE
                    supportActionBar?.hide()
                } else {
                    navView.visibility = View.VISIBLE
                    supportActionBar?.show()
                }
            }
        }

        navView.setupWithNavController(navController)
    }

    override fun getAppContainer(): AppBarLayout = appBarLayout

    override fun attachToAppBarLayout(appBarContent: View?) {
        appBarLayout.removeAllViews()
        appBarContent?.let {
            appBarLayout.addView(appBarContent)

            appBarContent.findViewById<Toolbar>(R.id.toolbar)
                .also { setSupportActionBar(it) }
                ?.let {
                    val appBarConfiguration = AppBarConfiguration(setOf(
                        R.id.navigation_home, R.id.navigation_businesses, R.id.navigation_notifications))
                    setupActionBarWithNavController(findNavController(R.id.nav_host_fragment), appBarConfiguration)
                }
        }
    }
}
