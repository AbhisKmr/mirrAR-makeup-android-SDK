package com.mirrar.testmakeupsdk.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirrar.testmakeupsdk.InterfaceListener.ISkuListener
import com.mirrar.testmakeupsdk.databinding.SkuItemLayoutBinding
import com.mirrar.testmakeupsdk.model.SkuModel
import java.util.LinkedList

//, private val addMore: () -> Unit
class SkuAdapter(val skuList: MutableList<SkuModel>, val iSkuListener: ISkuListener) :
    RecyclerView.Adapter<SkuAdapter.ViewHolder>() {

    class ViewHolder(val binding: SkuItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            SkuItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.binding.sku.text = skuList[holder.adapterPosition].sku
        holder.binding.skuCategory.text = skuList[holder.adapterPosition].category

        holder.binding.delete.setOnClickListener {
            skuList.removeAt(holder.adapterPosition)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = skuList.size
}