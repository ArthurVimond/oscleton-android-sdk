package com.oscleton.sdk.internal

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbAccessory
import android.hardware.usb.UsbManager
import android.os.ParcelFileDescriptor
import com.oscleton.sdk.utils.Logger
import java.io.FileInputStream
import java.io.FileOutputStream

internal class USBManager(
    private val context: Context
) {

    private val ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"


    private val usbManager: UsbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
    private lateinit var permissionIntent: PendingIntent

    private var accessory: UsbAccessory? = null
    private var fileDescriptor: ParcelFileDescriptor? = null
    private var inputStream: FileInputStream? = null
    private var outputStream: FileOutputStream? = null


    private val usbReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            Logger.d("USB BroadcastReceiver - onReceive - intent.action: ${intent.action}")

            if (ACTION_USB_PERMISSION == intent.action) {
                synchronized(this) {
                    val accessory: UsbAccessory? = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY)

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        accessory?.apply {
                            // call method to set up accessory communication
                        }
                    } else {
                        Logger.d("Permission denied for accessory $accessory", this)
                    }
                }
            }

            if (UsbManager.ACTION_USB_ACCESSORY_DETACHED == intent.action) {
                val accessory: UsbAccessory? = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY)
                accessory?.apply {
                    // call your method that cleans up and closes communication with the accessory
                }
            }
        }
    }

    init {
//        val accessoryList = usbManager.accessoryList
//        accessoryList.forEach { accessory ->
//            Logger.d("forEach accessory: $accessory")
//        }




    }

    fun setupUsbBroadcastReceiver(context: Context) {
//        Logger.d("setupUsbBroadcastReceiver")
//        permissionIntent = PendingIntent.getBroadcast(context, 0, Intent(ACTION_USB_PERMISSION), 0)
//        val filter = IntentFilter(ACTION_USB_PERMISSION)
//        context.registerReceiver(usbReceiver, filter)
    }

    fun requestPermission() {
        usbManager.requestPermission(accessory, permissionIntent)
    }

    private val runnable: Runnable = Runnable {

    }

    private fun openAccessory() {
        Logger.d("openAccessory: $accessory")
        fileDescriptor = usbManager.openAccessory(accessory)
        fileDescriptor?.fileDescriptor?.also { fd ->
            inputStream = FileInputStream(fd)
            outputStream = FileOutputStream(fd)
            val thread = Thread(null, runnable, "AccessoryThread")
            thread.start()
        }
    }
}