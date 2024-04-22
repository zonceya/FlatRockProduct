package com.example.flatrockproduct.presentation.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flatrockproduct.domain.product.ProductDetail
import com.example.flatrockproduct.databinding.ProductLayoutBinding
import com.example.flatrockproduct.domain.product.ProductDetailsInfo

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var productList = listOf<ProductDetail>()
    private var onItemClick: ((ProductDetail) -> Unit)? = null

    fun setProductList(productDetailsInfo: ProductDetailsInfo) {
        this.productList =
            (productDetailsInfo.productList?.firstOrNull()?.products?.toList() ?: emptyList()) as List<ProductDetail>
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var product: ProductDetail? = null
        private var secondProduct: ProductDetail? = null
        init {
            // Set click listener for the root view of the ViewHolder
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedProduct = if (binding.productImage2 == it) secondProduct else product

                    clickedProduct?.let { product ->
                        onItemClick?.invoke(product)
                    }
                }
            }

            // Set click listener for productImage
            binding.productImage.setOnClickListener {
                product?.let { product ->
                    onItemClick?.invoke(product)
                }
            }

            // Set click listener for productImage2
            binding.productImage2.setOnClickListener {
                secondProduct?.let { product ->
                    onItemClick?.invoke(product)
                }
            }
        }
        fun bind(product: ProductDetail, secondProduct: ProductDetail?) {
            this.product = product
            this.secondProduct = secondProduct
            with(binding) {
                productName.text = product.title
                Glide.with(productImage.context)
                    .load(product.thumbnail)
                    .transform(RoundedCorners(20))
                    .into(productImage)
                price.text = "R${product.price}"
                secondProduct?.let {
                    description2.text = it.title
                    Glide.with(productImage2.context)
                        .load(it.thumbnail)
                        .transform(RoundedCorners(20))
                        .into(productImage2)
                    price2.text = "R${it.price}"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ProductLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setOnItemClickListener(listener: (ProductDetail) -> Unit) {
        onItemClick = listener
    }

    override fun getItemCount(): Int {
        return (productList.size + 1) / 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product1Position = position * 2
        val product2Position = position * 2 + 1
        val product1 = productList.getOrNull(product1Position)
        val product2 = productList.getOrNull(product2Position)
        holder.bind(product1!!, product2)
    }
}
