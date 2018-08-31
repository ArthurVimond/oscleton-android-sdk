package fr.arthurvimond.oscletonsdk.sample.utils

import android.support.design.widget.Snackbar
import android.view.View

object SnackbarUtils {

    fun showShortSnackbar(parentLayout: View, stringId: Int) {
        val context = parentLayout.context
        Snackbar.make(parentLayout, context.resources.getString(stringId), Snackbar.LENGTH_SHORT)
                .show()
    }

}