package com.example.flatrockproduct.presentation.ui.productdetails


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flatrockproduct.R
import com.example.flatrockproduct.databinding.ProductDetailsLayoutBinding
import com.example.flatrockproduct.domain.product.ProductDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ProductDetailsLayoutBinding
    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductDetailsLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressBar = findViewById(R.id.productDetailsProgressBar)

       
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Handle the back button click

        // Initialize ViewModel
        ViewModelProvider(this)[ProductDetailViewModel::class.java].also { viewModel = it }
        // Set up progress bar
        progressBar.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // Extract productId from intent
        val productId = intent.getIntExtra("productId", -1)

        // Observe LiveData from ViewModel
        viewModel.state.observe(this, Observer { state ->
            progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            state.productDetailsInfo?.let { productDetailsInfo ->
                val productDetails = productDetailsInfo.productDetailsPerProduct.values
                productDetails.forEach { productDetail ->
                    if (productDetail != null) {
                        bindProductDetails(productDetail)
                    }
                }
            }
        })

        // Fetch product details
        if (productId != -1) {
            viewModel.getProductDetail(productId)
        } else {
            // Handle invalid productId
            return
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun bindProductDetails(productDetail: ProductDetail) {
        with(binding) {
            // Load image using Glide
            Glide.with(this@ProductDetailsActivity)
                .load(productDetail.thumbnail)
                .transform(RoundedCorners(20))
                .into(productThumbnail)

            // Bind other product details to UI elements
            productTitle.text = "Product: ${productDetail.title}"
            productDescription.text = "Description: ${productDetail.description}"
            productBrand.text = "Brand: ${productDetail.brand}"
            productCategory.text = "Category: ${productDetail.category}"
           // productPrice.text = "Price: ${productDetail.price}"
            productStock.text = "Stock: ${productDetail.stock}"
            productRating.text = "Rating: ${productDetail.rating}"
        }
    }
}
