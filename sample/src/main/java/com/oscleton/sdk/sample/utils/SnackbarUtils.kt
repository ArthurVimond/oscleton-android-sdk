package com.oscleton.sdk.sample.utils

import com.google.android.material.snackbar.Snackbar
import android.view.View

object SnackbarUtils {

    fun showShortSnackbar(parentLayout: View, stringId: Int) {
        val context = parentLayout.context
        Snackbar.make(parentLayout, context.resources.getString(stringId), Snackbar.LENGTH_SHORT)
                .show()
    }

}