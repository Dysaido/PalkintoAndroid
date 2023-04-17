package com.bankirobot.palkinto.menu

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.forEach
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bankirobot.palkinto.R
import com.bankirobot.palkinto.utils.StepDetector
import kotlinx.android.synthetic.main.controller_menu.*

class MenuController : AppCompatActivity(), SensorEventListener {

    private val stepDetector = StepDetector { STEP_COUNT++ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.controller_menu)
        instance = this
        val navController = findNavController(R.id.nav_host_menu)
        nav_bottom.setupWithNavController(navController)
        nav_bottom.menu.forEach {
            TooltipCompat.setTooltipText(findViewById(R.id.nav_basic), "")
            TooltipCompat.setTooltipText(findViewById(R.id.nav_settings), "")
        }
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    var tabVisibility: Int = View.VISIBLE
        set(value) {
            field = value
//            menu_tabs.visibility = field
        }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            stepDetector.updateAccel(
                event.timestamp,
                event.values[0],
                event.values[1],
                event.values[2]
            )
        }
    }

    /**
     * Lepesszamlalo statikus getterre
     */
    companion object {
        const val DATE = "=1&from=now-2y&to=now-1m"
        lateinit var instance: MenuController
            private set

        var STEP_COUNT: Int = 0
            private set(value) {
                field = +value
            }

    }
}