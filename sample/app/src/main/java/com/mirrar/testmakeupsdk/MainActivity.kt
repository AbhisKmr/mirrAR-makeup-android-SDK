package com.mirrar.testmakeupsdk

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.mirrar.makeupsdk.helper.AppConstraints
import com.mirrar.testmakeupsdk.InterfaceListener.ISkuListener
import com.mirrar.testmakeupsdk.adapter.SkuAdapter
import com.mirrar.testmakeupsdk.databinding.ActivityMainBinding
import com.mirrar.testmakeupsdk.model.SkuModel
import java.util.LinkedList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = mutableListOf<SkuModel>()

    private val categoryTag: Array<String> = arrayOf(
        "Lipstick",
        "EyeshadowLid",
        "LipstickOneTone",
        "BlushCheekbone",
        "Highlighter",
        "BlushContour",
        "EyebrowsThin"
    )

    private val PERMS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppConstraints.BRAND_ID = "4a65a544-0d45-4388-8076-001a6bec4c68"

        getSpinnerAdapter(binding.skuCategory)

        binding.defaultBrand.text = "Default Brand ID : ${AppConstraints.BRAND_ID}"

        binding.skuRecycler.adapter = SkuAdapter(list, object : ISkuListener {
            override fun onDelete(position: Int) {
                if (list.size > 1) {
                    list.removeAt(position)
                    binding.skuRecycler.adapter?.notifyDataSetChanged()
                }
            }
        })

        binding.addSku.setOnClickListener {
            if (binding.skuCode.text.toString().isNotBlank()) {
                list.add(0, SkuModel().apply {
                    sku = binding.skuCode.text.toString()
                    category = binding.skuCategory.selectedItem.toString()
                })
                binding.skuRecycler.adapter?.notifyDataSetChanged()
                binding.skuCode.text.clear()
            }
        }

        binding.pastDemo.setOnClickListener {
            runDefault()
        }

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
                if (list.size > 1 && binding.brandId.text.isNotEmpty()){
                    AppConstraints.BRAND_ID = binding.brandId.text.toString()
                    supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment(list))
                        .commit()
                } else{
                    Toast.makeText(this, "Empty data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getSpinnerAdapter(convert_from_spinner: Spinner) {
        convert_from_spinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryTag)
    }

    private fun runDefault() {
        val category = arrayListOf(
            "EyeshadowLid",
            "LipstickOneTone",
            "BlushCheekbone",
            "Highlighter",
            "BlushContour",
            "EyebrowsThin"
        )
        val s = arrayListOf(
            "Light5861",
            "D for Danger5864",
            "Pale Pink5870",
            "Peachlite5873",
            "02 Warm5877",
            "Medium Brown5879"
        )
        list.clear()
        for (i in 0 until category.size) {
            val sku = SkuModel()
            sku.sku = s[i]
            sku.category = category[i]
            list.add(sku)
        }
        binding.brandId.setText(AppConstraints.BRAND_ID)
        binding.skuCode.text.clear()
        binding.skuRecycler.adapter?.notifyDataSetChanged()
    }
}