// ProductDetailActivity.kt
package ipca.app.modeltest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ipca.app.modeltest.databinding.ActivityProductDetailBinding
import java.util.Date

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private var productList = arrayListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productList = intent.getParcelableArrayListExtra("productList") ?: arrayListOf()

        binding.doneButton.setOnClickListener {
            val description = binding.descriptionEditText.text.toString()
            val valueText = binding.valueEditText.text.toString()

            if (description.isNotBlank() && valueText.isNotBlank()) {
                val value = valueText.toDouble()

                val product = Product(description, value, Date())

                productList.add(product)

                val resultIntent = Intent()
                resultIntent.putParcelableArrayListExtra("updatedProductList", productList)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
