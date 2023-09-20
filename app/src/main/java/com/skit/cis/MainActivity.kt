package com.skit.cis

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val shadowView = findViewById<InnerShadowView>(R.id.shadow_view)
        val tvSize = findViewById<TextView>(R.id.tv_size)
        val seekBar = findViewById<SeekBar>(R.id.seek_bar)
        shadowView.post {
            seekBar.max = min(shadowView.measuredHeight, shadowView.measuredWidth) / 2
        }
        seekBar.also {
            it.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    tvSize.text = "round: $progress"
                    shadowView.setRound(progress.toFloat())
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
    }
}