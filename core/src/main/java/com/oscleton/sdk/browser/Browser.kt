package com.oscleton.sdk.browser

import com.oscleton.sdk.browser.models.BrowserItem
import com.oscleton.sdk.internal.LiveAPI
import com.oscleton.sdk.internal.MessageManager

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

    /**
     * Return a chunk list of children BrowserItem elements for a specific BrowserItem.
     * The afterUri parameter is used to load the next chunk of BrowserItem elements.
     * For an initial loading, pass an empty String to afterUri.
     * The pageSize parameter lets you control how many BrowserItem elements you want to return on a single call.
     *
     * NB: This method is blocking and is especially designed to work with the Paging library,
     * as it requires synchronous calls in its loadInitial() and loadAfter() methods.
     *
     * @param uri the BrowserItem uri
     * @param afterUri the last BrowserItem uri in the current loaded list
     * @param pageSize the number of BrowserItem chunk list to load
     * @return a chunk list of BrowserItem elements
     * @since 1.0
     */
    fun getBrowserItems(uri: String, afterUri: String, pageSize: Int = 20): List<BrowserItem> {
        messageManager.sendMessage(LiveAPI.browserGetBrowserItemChildren, listOf(uri, afterUri, pageSize))
        return browserDataManager.getBrowserItems(uri = uri, afterUri = afterUri)
    }

    /**
     * Preview a BrowserItem in Live.
     *
     * @param uri the BrowserItem uri
     * @since 1.0
     */
    fun previewBrowserItem(uri: String) {
        messageManager.sendMessage(LiveAPI.browserPreviewBrowserItem, listOf(uri))
    }

    /**
     * Load a BrowserItem in Live.
     *
     * @param uri the BrowserItem uri
     * @since 1.0
     */
    fun loadBrowserItem(uri: String) {
        messageManager.sendMessage(LiveAPI.browserLoadBrowserItem, listOf(uri))
    }

    /**
     * Stop the current BrowserItem preview in Live.
     *
     * @since 1.0
     */
    fun stopPreview() {
        messageManager.sendMessage(LiveAPI.browserStopPreview)
    }

}