package com.oscleton.sdk.sample.doc;

import com.oscleton.sdk.OscletonSDK;
import com.oscleton.sdk.browser.Browser;
import com.oscleton.sdk.callbacks.CallbackProvider;
import com.oscleton.sdk.callbacks.configuration.ConfigurationCallbacks;
import com.oscleton.sdk.callbacks.configuration.listeners.OnComputerIPDiscoverySuccessListener;
import com.oscleton.sdk.callbacks.devices.DevicesCallbacks;
import com.oscleton.sdk.callbacks.devices.listeners.OnTrackDeviceParameterChangeListener;
import com.oscleton.sdk.callbacks.liveset.LiveSetCallbacks;
import com.oscleton.sdk.callbacks.liveset.listeners.OnTempoChangeListener;
import com.oscleton.sdk.callbacks.tracks.TracksCallbacks;
import com.oscleton.sdk.callbacks.tracks.listeners.OnTrackParameterChangeListener;
import com.oscleton.sdk.configuration.Configuration;
import com.oscleton.sdk.devices.Devices;
import com.oscleton.sdk.liveset.LiveSet;
import com.oscleton.sdk.rx.ConfigurationRx;
import com.oscleton.sdk.rx.DevicesRx;
import com.oscleton.sdk.rx.LiveSetRx;
import com.oscleton.sdk.rx.RxProvider;
import com.oscleton.sdk.rx.TracksRx;
import com.oscleton.sdk.tracks.Tracks;

public class DocSnippetsJava {

    void snippet() {

        Configuration config = OscletonSDK.getInstance().getConfig();
        LiveSet liveSet = OscletonSDK.getInstance().getLiveSet();
        Tracks tracks = OscletonSDK.getInstance().getTracks();
        Devices devices = OscletonSDK.getInstance().getDevices();




        // RxJava
        ConfigurationRx configRx = RxProvider.from(config);
        LiveSetRx liveSetRx = RxProvider.from(liveSet);
        TracksRx tracksRx = RxProvider.from(tracks);
        DevicesRx devicesRx = RxProvider.from(devices);

        devicesRx.getTrackDeviceParameter();




        // Callbacks
        ConfigurationCallbacks configurationCallbacks = CallbackProvider.from(config);
        LiveSetCallbacks liveSetCallbacks = CallbackProvider.from(liveSet);
        TracksCallbacks tracksCallbacks = CallbackProvider.from(tracks);
        DevicesCallbacks devicesCallbacks = CallbackProvider.from(devices);


        configurationCallbacks.set((OnComputerIPDiscoverySuccessListener) computerIP -> {

        });

        liveSetCallbacks.set((OnTempoChangeListener) tempo -> {

        });

        tracksCallbacks.set((OnTrackParameterChangeListener) trackParameter -> {

        });

        devicesCallbacks.set((OnTrackDeviceParameterChangeListener) trackDeviceParameter -> {

        });


        // Test





    }

    void browser() {

        Browser browser = OscletonSDK.getInstance().getBrowser();
        browser.previewBrowserItem("");

        browser.stopPreview();
        browser.loadBrowserItem("");

    }

}
