package com.oscleton.sdk.browser

import com.google.gson.Gson
import com.oscleton.sdk.extensions.string
import com.oscleton.sdk.internal.LiveAPI
import com.oscleton.sdk.internal.MessageManager
import com.oscleton.sdk.models.BrowserItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

internal class BrowserDataManager internal constructor(private val messageManager: MessageManager,
                                                       private val gson: Gson) {

    // Private properties

    // Rx
    private val getBrowserItemCompositeDisposable = CompositeDisposable()

    fun getBrowserItems(uri: String, afterUri: String): List<BrowserItem> {
        
        val browserItemsResult = mutableListOf<BrowserItem>()

        getBrowserItemCompositeDisposable.clear()

        messageManager.oscMessage
                .filter { it.address == LiveAPI.browserBrowserItemChildren }
                .subscribe {
                    val browserItems = it.arguments
                            .map {
                                val jsonString = it.string
                                val item = gson.fromJson(jsonString, BrowserItem::class.java)
                                return@map item
                            }

                    browserItemsResult.addAll(browserItems)
                }
                .addTo(getBrowserItemCompositeDisposable)

        val oscMessage = messageManager.oscMessage
                .filter { it.address == LiveAPI.browserBrowserItemChildren }
                .debounce(150, TimeUnit.MILLISECONDS)
                .blockingFirst()

        return browserItemsResult
    }

}