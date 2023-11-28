package ipca.stock.a18851

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextQuantity = findViewById<EditText>(R.id.editTextQuantity)
        val editTextMinimum = findViewById<EditText>(R.id.editTextMinimum)

        findViewById<Button>(R.id.buttonDone).setOnClickListener {
            val intent = Intent()
            intent.putExtra(DATA_NAME,editTextName.text.toString())
            intent.putExtra(DATA_QUANTITY,editTextQuantity.text.toString().toInt())
            intent.putExtra(DATA_MINIMUM,editTextMinimum.text.toString().toInt())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }


    companion object {
        const val DATA_NAME = "data_name"
        const val DATA_QUANTITY = "data_quantity"
        const val DATA_MINIMUM = "data_minimum"
    }
}