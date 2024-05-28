package com.example.flatrockproduct.presentation.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flatrockproduct.databinding.ProductLayoutBinding
import com.example.flatrockproduct.domain.product.ProductDetail
import com.example.flatrockproduct.domain.product.ProductDetailsInfo
import java.util.Locale

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>(), Filterable {

    private var productList = listOf<ProductDetail>()
    private var originalProductList = listOf<ProductDetail>()
    private var onItemClick: ((ProductDetail) -> Unit)? = null

    fun setProductList(productDetailsInfo: ProductDetailsInfo) {
        this.originalProductList =
            (productDetailsInfo.productList?.firstOrNull()?.products?.toList() ?: emptyList()) as List<ProductDetail>
        this.productList = originalProductList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ProductLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private var product: ProductDetail? = null
        private var secondProduct: ProductDetail? = null

        init {
            binding.productImage.setOnClickListener {
                product?.let { product ->
                    onItemClick?.invoke(product)
                }
            }
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
        val binding = ProductLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint?.toString()?.toLowerCase(Locale.getDefault())
                val filterResults = FilterResults()
                filterResults.values = if (queryString.isNullOrEmpty()) {
                    originalProductList
                } else {
                    originalProductList.filter { product ->
                        product.brand?.lowercase(Locale.getDefault())?.contains(queryString) ?: false ||
                                product.title?.toLowerCase(Locale.getDefault())?.contains(queryString) ?: false
                    }
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                productList = results?.values as List<ProductDetail>
                notifyDataSetChanged()
            }
        }
    }
}
