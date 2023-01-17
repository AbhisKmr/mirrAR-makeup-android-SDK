package com.mirrar.testmakeupsdk

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.mirrar.makeupsdk.MakeUpFragment
import com.mirrar.makeupsdk.helper.AppConstraints.Companion.BRAND_ID
import com.mirrar.makeupsdk.interface_class.IMakeupCallback
import com.visagetechnologies.makeupsdk.Effect

class MainActivity : AppCompatActivity() {

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
            findViewById<Button>(R.id.openMakeupSDK).setOnClickListener {
                supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment())
                    .commit()
            }
        }
    }
}