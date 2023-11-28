// MainActivity.kt
package ipca.app.modeltest

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ipca.app.modeltest.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var productList = arrayListOf(
        Product("Robalo", 5.1, Date()),
        Product("Frango", 4.1, Date()),
        Product("Ervilhas", 3.2, Date()),
        Product("Salsa", 1.2, Date()),
        Product("Arroz", 2.1, Date())
    )

    private lateinit var adapter: ArrayAdapter<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, productList)
        binding.budgetListView.adapter = adapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putParcelableArrayListExtra("productList", ArrayList(productList))
            startActivityForResult(intent, PRODUCT_DETAIL_REQUEST_CODE)
        }

        binding.sortButton.setOnClickListener {
            productList.sortBy { it.value }
            adapter.notifyDataSetChanged()
        }

        binding.totalButton.setOnClickListener {
            val totalCost = productList.sumOf { it.value }
            val formattedTotal = "%.2f".format(totalCost)
            Toast.makeText(this, "Total: $formattedTotal", Toast.LENGTH_LONG).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PRODUCT_DETAIL_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.getParcelableArrayListExtra<Product>("updatedProductList")?.let {
                productList.clear()
                productList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        const val PRODUCT_DETAIL_REQUEST_CODE = 1
    }
}
