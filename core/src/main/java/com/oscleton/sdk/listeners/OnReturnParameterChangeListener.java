package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.ReturnParameter;

/**
 * Interface definition for a callback to be invoked
 * when a return parameter changes.
 *
 * @since 0.7
 */
public interface OnReturnParameterChangeListener {
    void onReturnParameterChange(ReturnParameter returnParameter);
}