package com.oscleton.sdk.utils

import com.oscleton.sdk.extensions.round
import kotlin.math.abs

/**
 * LiveVolumeUtils is a utility class regarding Live audio levels conversion.
 *
 * @since 0.4
 */
internal object LiveVolumeUtils {

    private val trackVolumeFloatToDecibelsMap = mapOf(
            0f to -70f,
            0.002376f to -69f,
            0.005042f to -68f,
            0.008033f to -67f,
            0.011130f to -66f,
            0.014195f to -65f,
            0.017634f to -64f,
            0.021346f to -63f,
            0.025248f to -62f,
            0.029627f to -61f,
            0.034624f to -60f,
            0.040196f to -59f,
            0.045391f to -58f,
            0.051012f to -57f,
            0.056434f to -56f,
            0.062098f to -55f,
            0.067786f to -54f,
            0.073492f to -53f,
            0.079490f to -52f,
            0.085236f to -51f,
            0.091348f to -50f,
            0.097384f to -49f,
            0.103536f to -48f,
            0.110000f to -47f,
            0.116202f to -46f,
            0.122717f to -45f,
            0.129429f to -44f,
            0.136015f to -43f,
            0.142882f to -42f,
            0.150000f to -41f,
            0.156977f to -40f,
            0.164219f to -39f,
            0.171707f to -38f,
            0.179350f to -37f,
            0.187037f to -36f,
            0.194983f to -35f,
            0.203181f to -34f,
            0.211630f to -33f,
            0.220335f to -32f,
            0.229242f to -31f,
            0.238439f to -30f,
            0.247983f to -29f,
            0.257907f to -28f,
            0.268256f to -27f,
            0.279088f to -26f,
            0.290455f to -25f,
            0.302414f to -24f,
            0.315154f to -23f,
            0.328847f to -22f,
            0.343664f to -21f,
            0.360000f to -20f,
            0.378422f to -19f,
            0.400000f to -18f,
            0.424942f to -17f,
            0.450000f to -16f,
            0.474942f to -15f,
            0.500000f to -14f,
            0.524942f to -13f,
            0.550000f to -12f,
            0.574942f to -11f,
            0.600000f to -10f,
            0.624942f to -9f,
            0.650000f to -8f,
            0.674942f to -7f,
            0.700000f to -6f,
            0.724942f to -5f,
            0.750000f to -4f,
            0.774942f to -3f,
            0.800000f to -2f,
            0.824942f to -1f,
            0.850000f to 0f,
            0.874942f to 1f,
            0.900000f to 2f,
            0.924942f to 3f,
            0.950000f to 4f,
            0.974942f to 5f,
            0.987457f to 5.5f,
            0.990000f to 5.6f,
            0.992457f to 5.7f,
            0.994942f to 5.8f,
            0.997457f to 5.9f,
            1f to 6f)

    fun trackVolumeDecibels(volume: Float): Float {
        var min = Float.MAX_VALUE

        var closestLowerKey = volume
        var closestLowerValue = volume

        var closestUpperKey = volume
        var closestUpperValue = volume

        var foundUpperEntry: Boolean? = null

        if (volume == 1f) {
            return trackVolumeFloatToDecibelsMap.getValue(volume)
        }

        for (v in trackVolumeFloatToDecibelsMap) {
            val diff = abs(v.key - volume)

            // Upper entry
            if (foundUpperEntry != null && !foundUpperEntry) {
                closestUpperKey = v.key
                closestUpperValue = v.value
                foundUpperEntry = null
            }

            // Lower entry
            if (diff <= min) {
                min = diff
                closestLowerKey = v.key
                closestLowerValue = v.value
                foundUpperEntry = false
            }

        }

        val closestVolumePercentage = (volume - closestLowerKey) / (closestUpperKey - closestLowerKey)
        val volumeDecibelsRaw = (closestUpperValue - closestLowerValue) * closestVolumePercentage + closestLowerValue

        return volumeDecibelsRaw.round(1)
    }

}