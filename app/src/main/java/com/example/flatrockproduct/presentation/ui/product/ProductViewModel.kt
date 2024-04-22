package com.example.flatrockproduct.presentation.ui.product
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
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _state = MutableLiveData<ProductState>()
    val state: LiveData<ProductState>
        get() = _state

    fun getAllProducts() {
        viewModelScope.launch {
            _state.value = ProductState(isLoading = true, error = null)

            val result = repository.getAllProduct()
            when (result)  {
                is Resource.Success -> {
                    val productDetailsInfo = ProductDetailsInfo(
                        productDetailsPerProduct = emptyMap(), // Provide appropriate value for your use case
                        productList = listOf(result.data) // Convert Product to a list
                    )
                    _state.value = ProductState(productDetailsInfo = productDetailsInfo, isLoading = false, error = null)
                }
                else -> {
                    _state.value = ProductState(productDetailsInfo = null, isLoading = false, error = result.message ?: "Unknown error occurred")
                }
            }
        }
    }
}
