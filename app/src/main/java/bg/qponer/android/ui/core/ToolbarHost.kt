package bg.qponer.android.ui.core

import android.view.View
import com.google.android.material.appbar.AppBarLayout

interface ToolbarHost {

    fun getAppContainer(): AppBarLayout

    fun attachToAppBarLayout(appBarContent: View?)

}