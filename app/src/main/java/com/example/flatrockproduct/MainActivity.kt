package com.example.flatrockproduct

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flatrockproduct.databinding.ActivityMainBinding
import com.example.flatrockproduct.domain.product.ProductDetail
import com.example.flatrockproduct.presentation.ui.product.ProductAdapter
import com.example.flatrockproduct.presentation.ui.product.ProductViewModel
import com.example.flatrockproduct.presentation.ui.productdetails.ProductDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.SearchView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView

    // Variables to handle double back press
    private var backPressedOnce = false
    private val doubleBackPressHandler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up drawer layout and toggle
        drawerLayout = binding.navigationDrawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up NavigationView
        navigationView = binding.navigationView
        setupDrawerContent(navigationView)
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Home"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Smartphones"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Furniture"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Beauty"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Groceries"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Fragrances"))

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SmartphonesFragment())
            .commit()
        // Prepare RecyclerView
        prepareRecyclerView()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment = when (tab.position) {
                    0 -> SmartphonesFragment()
                    1 -> FurnitureFragment()
                    2 -> BeautyFragment()
                    3 -> HomeFragment()
                    4 -> GroceriesFragment()
                    5 -> FragrancesFragment()
                    else -> SmartphonesFragment()
                }
                supportFragmentManager.beginTransaction()
               //     .replace(R.id.fragment_container, fragment)
              //      .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Observe state changes
        viewModel.state.observe(this) { state ->
            if (state.isLoading) {
                // Show loading indicator
            } else {
                state.error?.let { error ->
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
        val recyclerView = binding.rvProducts
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter
    }

    private fun openProductDetails(product: ProductDetail) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("productId", product.id)
        startActivity(intent)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    productAdapter.filter.filter(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productAdapter.filter.filter(newText ?: "")
                return true
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed()
            return
        }
        this.backPressedOnce = true
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
        doubleBackPressHandler.postDelayed({
            backPressedOnce = false
        }, 2000) // 2 seconds delay
    }
}
