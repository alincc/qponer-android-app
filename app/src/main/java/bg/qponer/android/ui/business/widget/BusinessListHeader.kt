package bg.qponer.android.ui.business.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.ViewCompat
import bg.qponer.android.R
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs


class BusinessListHeader @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attributeSet, defStyleAttr) {

    private val offsetChangeListener by lazy {
        object: AppBarLayout.OnOffsetChangedListener {

            private var totalScrollRange: Float = -1F

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (-1F == totalScrollRange) {
                    totalScrollRange = appBarLayout.totalScrollRange.toFloat()
                }

                val progress = abs(verticalOffset).toFloat() / totalScrollRange
                setProgress(progress)
            }
        }
    }

    init {
        View.inflate(context, R.layout.header_business_list, this)
        loadLayoutDescription(R.xml.scene_header_business_list)
        setState(R.id.start, -1, -1)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (parent is AppBarLayout) {
            with (parent as AppBarLayout) {
                this@BusinessListHeader.fitsSystemWindows = fitsSystemWindows
                addOnOffsetChangedListener(offsetChangeListener)
            }
            ViewCompat.requestApplyInsets(this)
        }
    }
}