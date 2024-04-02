package com.example.flatrockproduct

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flatrockproduct.databinding.ActivityMainBinding
import com.example.flatrockproduct.domain.product.ProductDetail
import com.example.flatrockproduct.presentation.ui.product.ProductAdapter
import com.example.flatrockproduct.presentation.ui.product.ProductViewModel
import com.example.flatrockproduct.presentation.ui.productdetails.ProductDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        // Observe state changes
        viewModel.state.observe(this) { state ->
            // Update UI based on the current state
            if (state.isLoading) {
                // Show loading indicator
            } else {
                // Hide loading indicator
                state.error?.let { error ->
                    // Show error message
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                }
                state.productDetailsInfo?.let { productDetailsInfo ->
                    productAdapter.setProductList(productDetailsInfo)
                }
            }
        }

        // Fetch products
        viewModel.getAllProducts()

        // Set item click listener
        productAdapter.setOnItemClickListener { product ->
            openProductDetails(product)
        }
    }

    private fun prepareRecyclerView() {
        productAdapter = ProductAdapter()
        val recyclerView: RecyclerView = binding.rvProducts
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter
    }

    private fun openProductDetails(product: ProductDetail) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("productId", product.id)
        startActivity(intent)
    }
}
