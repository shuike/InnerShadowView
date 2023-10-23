package com.skit.cis

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.skit.view.InnerShadowView
import kotlin.math.min
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var shadowView: InnerShadowView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shadowView = findViewById<InnerShadowView>(R.id.shadow_view)
        initRadiusSeekbar()
        initShadowRadiusSeekbar()
    }

    private fun initShadowRadiusSeekbar() {
        val tvSize = findViewById<TextView>(R.id.tv_shadow_radius)
        val seekBar = findViewById<SeekBar>(R.id.shadow_radius_seek_bar)
        seekBar.progress = shadowView.shadowRadius.roundToInt()
        seekBar.max = 100

        seekBar.also {
            it.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    tvSize.text = "Shadow Radius: $progress"
                    shadowView.setShadowRadius(progress.toFloat())
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }

    }

    private fun initRadiusSeekbar() {
        val tvSize = findViewById<TextView>(R.id.tv_radius)
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