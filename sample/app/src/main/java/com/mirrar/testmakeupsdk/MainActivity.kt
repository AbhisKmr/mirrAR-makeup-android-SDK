package com.mirrar.testmakeupsdk

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.mirrar.makeupsdk.MakeUpFragment
import com.mirrar.makeupsdk.interface_class.IMakeupCallback

class MainActivity : AppCompatActivity(), IMakeupCallback {

    private lateinit var makeUpFragment: MakeUpFragment
    private val PERMS = arrayOf(
        Manifest.permission.CAMERA
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, PERMS, 0)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var allowed = true
        for (i in permissions.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                allowed = false
                break
            }
        }

        if (allowed) {
            makeUpFragment = MakeUpFragment(this)
            supportFragmentManager.beginTransaction().replace(R.id.container, makeUpFragment)
                .commit()
        }
    }

    override fun OnClear() {
        TODO("Not yet implemented")
    }

    override fun OnEffectCreated() {
        TODO("Not yet implemented")
    }

    override fun OnEffectDisplayed() {
        TODO("Not yet implemented")
    }

    override fun OnEffectHidden() {
        TODO("Not yet implemented")
    }

    override fun OnEffectRemoved() {
        TODO("Not yet implemented")
    }

    override fun OnEffectUpdated() {
        TODO("Not yet implemented")
    }

    override fun OnStart() {
        TODO("Not yet implemented")
    }
}