@file:Suppress("DEPRECATION", "NonAsciiCharacters")
package com.cem.flashproject

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.Camera.Parameters
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION", "ImplicitThis")
class MainActivity : AppCompatActivity() {
    private var btnSwitch: ImageButton? = null
    private var camera: Camera? = null
    private var flashAcik = false
    private var flashVarmi = false
    private var params: Parameters? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSwitch = findViewById<ImageButton>(R.id.btnSwitch)
        flashVarmi =
            applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        if (!flashVarmi) {
            val alert: AlertDialog = AlertDialog.Builder(this@MainActivity).create()
            alert.setTitle("HATA")
            alert.setMessage("Bu aygıt Flash desteklemiyor")
            alert.setButton(
                "Tamam"
            ) { _, _ ->
                finish()
            }
            alert.show()
            return
        }
        cameraAc()
        toggleButtonImage()
    btnSwitch!!.setOnClickListener {
            if (flashAcik) {
                flashKapat()
            } else {
                flashAc()
            }
        }
    }
    private fun cameraAc() {
        if (camera == null) {
            camera = Camera.open()
            params = camera!!.parameters
        }
    }
    private fun flashAc() {
        if (!flashAcik) {
            if (camera == null || params == null) {
                return
            }
            params = camera!!.parameters
            params!!.flashMode = Parameters.FLASH_MODE_TORCH
            camera!!.parameters = params
            camera!!.startPreview()
            flashAcik = true
            toggleButtonImage()
        }
    }
    private fun flashKapat() {
        if (flashAcik) {
            if (camera == null || params == null) {
                return
            }
            params = camera!!.parameters
            params!!.flashMode = Parameters.FLASH_MODE_OFF
            camera!!.parameters = params
            camera!!.stopPreview()
            flashAcik = false
            toggleButtonImage()
        }
    }
    private fun toggleButtonImage() {
        if (flashAcik) {
            btnSwitch!!.setImageResource(R.drawable.btn_switch_on)
        } else {
            btnSwitch!!.setImageResource(R.drawable.btn_switch_off)
        }
    }
}