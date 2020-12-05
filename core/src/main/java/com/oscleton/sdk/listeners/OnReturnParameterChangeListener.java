package com.oscleton.sdk.listeners;

import com.oscleton.sdk.models.ReturnParameter;

/**
 * Interface definition for a callback to be invoked
 * when a return parameter changes.
 *
 * @since 0.7
 *
 * @deprecated Use the corresponding OnReturnParameterChangeListener from the 'core-callbacks' artifact instead as follow:
 * <p>
 *     import com.oscleton.sdk.callbacks.tracks.listeners.OnReturnParameterChangeListener<br>
 *     Oscleton.instance.liveSet.cb().set(OnReturnParameterChangeListener { })
 * </p>
 */
@Deprecated
public interface OnReturnParameterChangeListener {
    void onReturnParameterChange(ReturnParameter returnParameter);
}