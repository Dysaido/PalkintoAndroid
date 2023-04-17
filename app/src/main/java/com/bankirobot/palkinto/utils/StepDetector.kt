package com.bankirobot.palkinto.utils

import kotlin.math.sqrt

private fun dot(a: FloatArray, b: FloatArray): Float {
    return a[0] * b[0] + a[1] * b[1] + a[2] * b[2]
}

private fun norm(array: FloatArray): Float {
    var result = 0f
    for (raw in array) result += raw * raw
    return sqrt(result.toDouble()).toFloat()
}

private fun sum(array: FloatArray): Float {
    var result = 0f
    for (raw in array) result += raw
    return result
}

class StepDetector(private val listener: StepListener) {

    companion object {
        private const val ACCEL_SIZE = 50
        private const val STEP_DELAY_NANO_SECOND = 250000000
        private const val STEP_THRESHOLD = 50f
        private const val RING_SIZE = 10
        private val accelRingX = FloatArray(ACCEL_SIZE)
        private val accelRingY = FloatArray(ACCEL_SIZE)
        private val accelRingZ = FloatArray(ACCEL_SIZE)
        private val velRing = FloatArray(RING_SIZE)
    }

    private var accelRingCounter = 0
    private var lastStepTimeNs: Long = 0
    private var oldVelocityEstimate = 0f
    private var velRingCounter = 0

    fun updateAccel(nanoSec: Long, x: Float, y: Float, z: Float) {
        val currentAccel = FloatArray(3)
        currentAccel[0] = x
        currentAccel[1] = y
        currentAccel[2] = z
        accelRingCounter++
        accelRingX[accelRingCounter % ACCEL_SIZE] = currentAccel[0]
        accelRingY[accelRingCounter % ACCEL_SIZE] = currentAccel[1]
        accelRingZ[accelRingCounter % ACCEL_SIZE] = currentAccel[2]
        val worldZ = FloatArray(3)
        worldZ[0] = sum(accelRingX) / accelRingCounter.coerceAtMost(ACCEL_SIZE)
        worldZ[1] = sum(accelRingY) / accelRingCounter.coerceAtMost(ACCEL_SIZE)
        worldZ[2] = sum(accelRingZ) / accelRingCounter.coerceAtMost(ACCEL_SIZE)
        val normalizationFactor = norm(worldZ)
        worldZ[0] = worldZ[0] / normalizationFactor
        worldZ[1] = worldZ[1] / normalizationFactor
        worldZ[2] = worldZ[2] / normalizationFactor
        val currentZ = dot(worldZ, currentAccel) - normalizationFactor
        velRingCounter++
        velRing[velRingCounter % RING_SIZE] = currentZ
        val velocityEstimate = sum(velRing)
        if (velocityEstimate > STEP_THRESHOLD && oldVelocityEstimate <= STEP_THRESHOLD &&
            nanoSec - lastStepTimeNs > STEP_DELAY_NANO_SECOND
        ) {
            listener.step()
            lastStepTimeNs = nanoSec
        }
        oldVelocityEstimate = velocityEstimate
    }
}

fun interface StepListener {
    fun step()
}