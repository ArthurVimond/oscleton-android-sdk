package com.oscleton.sdk.callbacks.listeners;

import com.oscleton.sdk.models.ReturnParameter;

/**
 * Interface definition for a callback to be invoked
 * when a return parameter changes.
 *
 * @since 1.0
 */
public interface OnReturnParameterChangeListener {
    void onReturnParameterChange(ReturnParameter returnParameter);
}