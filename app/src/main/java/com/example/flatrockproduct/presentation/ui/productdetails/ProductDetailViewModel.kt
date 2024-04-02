package com.example.flatrockproduct.presentation.ui.productdetails
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flatrockproduct.domain.product.ProductDetailsInfo
import com.example.flatrockproduct.domain.repository.ProductRepository
import com.example.flatrockproduct.presentation.util.ProductState
import com.example.flatrockproduct.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _state = MutableLiveData<ProductState>()
    val state: LiveData<ProductState>
        get() = _state

    fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            _state.value = ProductState(isLoading = true, error = null)

            val result = repository.getProduct(productId)

            _state.value = when (result) {
                is Resource.Success -> {
                    val productDetail = result.data
                    val productDetailsInfo = mapOf(0 to productDetail)?.let {
                        ProductDetailsInfo(
                            productDetailsPerProduct = it, // Assuming the key is 0
                            productList = null // Set to null or provide a product list if available
                        )
                    }
                    ProductState(productDetailsInfo = productDetailsInfo, isLoading = false, error = null)
                     }
                else -> {
                    ProductState(productDetailsInfo = null, isLoading = false, error = result.message ?: "Unknown error occurred")
                }
            }
        }
    }
}
