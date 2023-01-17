package com.mirrar.testmakeupsdk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mirrar.makeupsdk.MakeUpFragment
import com.mirrar.makeupsdk.helper.AppConstraints
import com.mirrar.makeupsdk.interface_class.IMakeupCallback
import com.mirrar.testmakeupsdk.databinding.FragmentMainBinding
import com.visagetechnologies.makeupsdk.Effect
import okhttp3.internal.notifyAll

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

//        makeUpFragment.removeMakeupEffect(effectMap[category[0]]!!)
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
}