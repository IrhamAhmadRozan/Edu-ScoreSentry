package com.developer.rozan.eduscoresentry.util.extension

import android.animation.LayoutTransition
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import com.developer.rozan.eduscoresentry.util.constant.Constant
import com.facebook.shimmer.ShimmerFrameLayout

fun ViewGroup.animateLayoutChanges() {
    layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
}

fun View.toggleVisibility(
    isLoading: Boolean,
    shouldHide: Boolean = false,
    reverse: Boolean = false
) {
    this.visibility = Constant.toggleVisibility(isLoading, shouldHide, reverse)
}

fun View.clickableOrNot(isLoading: Boolean) {
    this.isClickable = !isLoading
}

fun View.visible() {
    if (!this.isVisible)
        this.visibility = View.VISIBLE
}

fun View.gone() {
    if (this.isVisible)
        this.visibility = View.GONE
}

fun View.invisible() {
    if (this.isVisible)
        this.visibility = View.INVISIBLE
}

fun View.enable() {
    if (!this.isEnabled)
        this.isEnabled = true
}

fun View.disable() {
    if (this.isEnabled)
        this.isEnabled = false
}

fun View.clickable() {
    if (!this.isClickable)
        this.isClickable = true
}

fun View.notClickable() {
    if (this.isClickable)
        this.isClickable = false
}

fun ShimmerFrameLayout.toggleShimmer(isLoading: Boolean) {
    if (isLoading)
        start()
    else
        stop()
}

fun ShimmerFrameLayout.start() {
    this.startShimmer()
    this.visible()
}

fun ShimmerFrameLayout.stop() {
    this.stopShimmer()
    this.gone()
}

@Suppress("DEPRECATION")
fun Activity.makeStatusBarTransparent(darkIconStatusBar: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (darkIconStatusBar) {
                    // set status bar icon to dark
                    decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    // set status bar icon to white
                    decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
}

fun Context.getStatusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else 0
}

fun View.setMargins(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        this.requestLayout()
    }
}