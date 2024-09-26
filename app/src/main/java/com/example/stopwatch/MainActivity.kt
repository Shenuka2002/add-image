package com.example.stopwatch

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import android.os.SystemClock
import android.os.Handler


        class MainActivity : AppCompatActivity() {
            private var isRunning = false
            private var startTime: Long = 0
            private var elapsedTime: Long = 0

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                val textView = findViewById<TextView>(R.id.textView)
                val startButton = findViewById<Button>(R.id.startButton)
                val stopButton = findViewById<Button>(R.id.stopButton)
                val resetButton = findViewById<Button>(R.id.resetButton)

                startButton.setOnClickListener {
                    if (!isRunning) {
                        startTime = SystemClock.elapsedRealtime() - elapsedTime
                        isRunning = true
                        updateTimer(textView)
                    }
                }

                stopButton.setOnClickListener {
                    if (isRunning) {
                        elapsedTime = SystemClock.elapsedRealtime() - startTime
                        isRunning = false
                    }
                }

                resetButton.setOnClickListener {
                    isRunning = false
                    startTime = 0
                    elapsedTime = 0
                    updateTimer(textView)
                }
            }

            private fun updateTimer(textView: TextView) {
                val handler = Handler()
                handler.post(object : Runnable {
                    override fun run() {
                        val time = SystemClock.elapsedRealtime() - startTime + elapsedTime
                        val hours = (time / 3600000).toInt()
                        val minutes = ((time - hours * 3600000) / 60000).toInt()
                        val seconds = ((time - hours * 3600000 - minutes * 60000) / 1000).toInt()

                        val timeString = String.format(
                            "%02d:%02d:%02d:%03d",
                            hours,
                            minutes,
                            seconds,
                        )

                        textView.text = timeString
                        if (isRunning) {
                            handler.postDelayed(this, 0)
                        }
                    }
                })
            }
        }