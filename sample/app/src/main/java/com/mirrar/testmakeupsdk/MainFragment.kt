package com.mirrar.testmakeupsdk

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mirrar.makeupsdk.MakeUpFragment
import com.mirrar.makeupsdk.ViewModels.CaptureImage
import com.mirrar.makeupsdk.helper.AppConstraints
import com.mirrar.makeupsdk.interface_class.IMakeupCallback
import com.mirrar.testmakeupsdk.databinding.FragmentMainBinding
import com.visagetechnologies.makeupsdk.Effect
import okhttp3.internal.notifyAll
import java.io.IOException

class MainFragment : Fragment(), IMakeupCallback {

    private lateinit var binding: FragmentMainBinding
    private lateinit var makeUpFragment: MakeUpFragment
    private val effectMap = mutableMapOf<String, Effect>()

    private val category = arrayListOf(
        "EyeshadowLid",
        "LipstickOneTone",
        "BlushCheekbone",
        "Highlighter",
        "BlushContour",
        "EyebrowsThin"
    )
    private val sku = arrayListOf(
        "Light5861",
        "D for Danger5864",
        "Pale Pink5870",
        "Peachlite5873",
        "02 Warm5877",
        "Medium Brown5879"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppConstraints.BRAND_ID = "4a65a544-0d45-4388-8076-001a6bec4c68"
        makeUpFragment = MakeUpFragment(this)

        childFragmentManager.beginTransaction().addToBackStack("makeUpFragment")
            .add(R.id.makeupContainer, makeUpFragment, makeUpFragment.tag).commit()

        binding.apply.setOnClickListener {
            for (i in 0 until category.size){
                val cat = category[i]
                val s = sku[i]
                makeUpFragment.applyMakeupEffect(cat, s)
            }
        }

        binding.camera.setOnClickListener {
            makeUpFragment.switchToLiveCamera()
        }

        binding.capture.setOnClickListener {
            makeUpFragment.captureImage()
        }

        var b = true
        binding.compare.setOnClickListener {
            makeUpFragment.isNeededCompareTool(b)
            b = !b
        }

        binding.photo.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        }

//        makeUpFragment.removeMakeupEffect(effectMap[category[0]]!!)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        data.data
                    )
                    makeUpFragment.switchToGallery(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } else if (resultCode === AppCompatActivity.RESULT_CANCELED) {
                Toast.makeText(requireActivity(), "Canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun OnClear() {

    }

    override fun OnEffectCreated(effect: Effect) {
        effectMap[effect.GetName()] = effect
    }

    override fun OnEffectDisplayed(effect: Effect) {

    }

    override fun OnEffectHidden(effect: Effect) {

    }

    override fun OnEffectRemoved(effect: Effect) {
        effectMap.remove(effect.GetName())
    }

    override fun OnEffectUpdated(effect: Effect) {

    }

    override fun OnStart() {

    }

    override fun captureBitmap(image: CaptureImage) {
        Log.v(image.originalUrl, image.filterUrl)
    }
}