package com.toan_itc.core.base

import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.toan_itc.core.R
import com.toan_itc.core.base.widget.ToolbarIndicator

/**
 * Created by Toan.IT on 11/30/17.
 * Email:Huynhvantoan.itc@gmail.com
 */
abstract class CoreActivity : AppCompatActivity() {
    private var mToolbarHashCode = 0


    /**
     * Setup main toolbar as ActionBar. Try to tint navigation icon based on toolbar's theme.
     *
     * @param indicator navigation icon (NONE, BACK, CLOSE are predefined). Uses toolbar theme color for tinting.
     * @param title     to be shown as in ActionBar. If it is null, title is not changed! Use empty string to clear it.
     * @param toolbar   may be null, in that case it is looking for R.id.toolbar.
     * @return initialized ActionBar or null
     */
    @JvmOverloads
    fun setupActionBar(indicator: ToolbarIndicator, title: CharSequence? = null, toolbar: Toolbar? = null): ActionBar? {
        var toolbar = toolbar
        if (toolbar == null) {
            toolbar = findViewById(R.id.toolbar)
            if (toolbar == null) {
                throw IllegalStateException("Toolbar not found. Add Toolbar with R.id.toolbar identifier in the activity layout or pass Toolbar as a parameter.")
            }
        }

        // this check is here because if 2 fragments with different indicators share a toolbar in activity,
        // it caused bug that back icon was not shown
        if (mToolbarHashCode != toolbar.hashCode()) {
            setSupportActionBar(toolbar)
        }

        val actionBar = supportActionBar
        actionBar?.let {
            actionBar.setDisplayUseLogoEnabled(false)
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(indicator.isHomeAsUpEnabled)
            actionBar.setHomeButtonEnabled(indicator.isHomeEnabled)

            if (indicator.drawableRes == 0) {
                actionBar.setHomeAsUpIndicator(null)
            } else {
                val iconDrawable = indicator.getTintedDrawable(toolbar)
                actionBar.setHomeAsUpIndicator(iconDrawable)
            }

            if (title != null) {
                actionBar.title = title
            }
        }
        mToolbarHashCode = toolbar.hashCode()
        return actionBar
    }
}
