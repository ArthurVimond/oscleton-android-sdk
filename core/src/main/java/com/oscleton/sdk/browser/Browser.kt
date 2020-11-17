package com.oscleton.sdk.browser

import com.oscleton.sdk.internal.LiveAPI
import com.oscleton.sdk.internal.MessageManager
import com.oscleton.sdk.models.BrowserItem

/**
 * Browser contains the methods to browse the Live library.
 *
 * @since 1.0
 */
class Browser internal constructor(private val browserDataManager: BrowserDataManager,
                                   private val messageManager: MessageManager) {

    init {
        observeProperties()
    }

    private fun observeProperties() {


    }

    // NB: Blocking call - designed for Paging library
    fun getBrowserItems(uri: String, afterUri: String, pageSize: Int = 20): List<BrowserItem> {
        messageManager.sendMessage(LiveAPI.browserGetBrowserItemChildren, listOf(uri, afterUri, pageSize))
        return browserDataManager.getBrowserItems(uri = uri, afterUri = afterUri)
    }

    fun previewBrowserItem(uri: String) {
        messageManager.sendMessage(LiveAPI.browserPreviewBrowserItem, listOf(uri))
    }

    fun loadBrowserItem(uri: String) {
        messageManager.sendMessage(LiveAPI.browserLoadBrowserItem, listOf(uri))
    }

    // Preview

    fun stopPreview() {
        messageManager.sendMessage(LiveAPI.browserStopPreview)
    }

}