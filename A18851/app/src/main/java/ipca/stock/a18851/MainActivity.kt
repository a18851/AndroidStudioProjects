package ipca.stock.a18851

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val products = arrayListOf<Product>(
        Product("molas", 10, 2),
        Product("pregos", 9, 3),
        Product("fios", 8, 2),
        Product("tomadas", 7, 3),
        Product("mesas", 6, 7),
        Product("cadeiras", 5, 6),
    )

    var onlyLow: Boolean = false
    lateinit var listViewProducts: ListView
    val adapter = ProductAdapter()

    val resultLauncher =
        registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.let { intent ->
                    val name = intent.extras?.getString(ProductDetailActivity.DATA_NAME) ?: ""
                    val quantity = intent.extras?.getInt(ProductDetailActivity.DATA_QUANTITY) ?: 0
                    val minimum = intent.extras?.getInt(ProductDetailActivity.DATA_MINIMUM) ?: 0
                    val product = Product(name!!, quantity!!, minimum!!)
                    products.add(product)
                    adapter.notifyDataSetChanged()
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewProducts = findViewById<ListView>(R.id.listViewProducts)
        listViewProducts.adapter = adapter


        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            val intent = Intent(this, ProductDetailActivity::class.java)
            resultLauncher.launch(intent)
        }

        findViewById<Button>(R.id.buttonLow).setOnClickListener {
            onlyLow = true
            adapter.notifyDataSetChanged()
        }
    }


    inner class ProductAdapter : BaseAdapter() {

        private var displayedProducts = ArrayList<Product>()

        init {
            displayedProducts = ArrayList(products)
        }

        fun showProducts(products: List<Product>) {
            displayedProducts.clear()
            displayedProducts.addAll(products)
            notifyDataSetChanged()
        }

        override fun getCount(): Int {
            return products.size
        }

        override fun getItem(position: Int): Any {
            return products[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


            val rootView = layoutInflater.inflate(R.layout.row_product_list, parent, false)
            val textViewDescription = rootView.findViewById<TextView>(R.id.textViewDescription)
            val textViewQuantity = rootView.findViewById<TextView>(R.id.textViewQt)
            val textViewMinimum = rootView.findViewById<TextView>(R.id.textViewMinQt)

            val product = displayedProducts[position]

            textViewDescription.text = product.description
            textViewQuantity.text = product.quantity.toString()
            textViewMinimum.text = product.minimumQt.toString()

            if(product.quantity < product.minimumQt){
                textViewDescription.setTextColor(Color.RED)
                textViewQuantity.setTextColor(Color.RED)
                textViewMinimum.setTextColor(Color.RED)
            }

            if (onlyLow && product.quantity >= product.minimumQt) {
                rootView.visibility = View.GONE
            }
            return rootView
        }



    }
}


