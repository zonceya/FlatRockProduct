package com.example.flatrockproduct.presentation.ui.product

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.flatrockproduct.domain.product.ProductDetail
import com.example.flatrockproduct.databinding.ProductLayoutBinding
import com.example.flatrockproduct.domain.product.ProductDetailsInfo

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var productList = listOf<ProductDetail>()
    private var onItemClick: ((ProductDetail) -> Unit)? = null

    fun setProductList(productDetailsInfo: ProductDetailsInfo) {
        this.productList = productDetailsInfo.productList?.firstOrNull()?.products?.toList() as List<ProductDetail>
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                 val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(productList[position])
                }
            }
        }

        fun bind(product: ProductDetail) {
            with(binding) {
                productName.text = product.title
                Glide.with(productImage.context)
                    .load(product.thumbnail)
                    .into(productImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    fun setOnItemClickListener(listener: (ProductDetail) -> Unit) {
        onItemClick = listener
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
