package ipca.app.modeltest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.Date

class MainActivity : AppCompatActivity() {

    val budgetItems = arrayListOf<BudgetItem>(
        BudgetItem("Item1", 1.4, Date()),
        BudgetItem("Item2", 3.1, Date()),
        BudgetItem("Item3", 5.4, Date()),
        BudgetItem("Item4", 6.4, Date()),
        BudgetItem("Item5", 0.4, Date()),
        BudgetItem("Item6", 2.3, Date()),
        BudgetItem("Item7", 8.5, Date())
    )

    lateinit var listViewBudgetItem: ListView
    val adapter = BudgetAdapter()

    val resultLauncher =
        registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK){
                it.data?.let {intent ->
                    val name = intent.extras?.getString(BudgetItemDetailActivity.DATA_NAME)?:""
                    val value = intent.extras?.getDouble(BudgetItemDetailActivity.DATA_VALUE)?:0.0
                    val date = Date()
                    val budgetItem = BudgetItem(name!!, value!!,date)
                    budgetItems.add(budgetItem)
                    adapter.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listViewBudgetItem = findViewById<ListView>(R.id.listViewBudgetItems)
        listViewBudgetItem.adapter = adapter



        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            val intent = Intent(this,BudgetItemDetailActivity::class.java )
            resultLauncher.launch(intent)
        }

        findViewById<Button>(R.id.buttonSort).setOnClickListener {
            budgetItems.sortBy {it.value}
            adapter.notifyDataSetChanged()
        }

        findViewById<Button>(R.id.buttonTotal).setOnClickListener {

            val totalBudgetCost = budgetItems.sumOf { it.value }
            Toast.makeText(this, "Total:${totalBudgetCost}", Toast.LENGTH_LONG).show()
        }


    }

    inner class BudgetAdapter: BaseAdapter(){
        override fun getCount(): Int {
            return budgetItems.size
        }

        override fun getItem(position: Int): Any {
            return budgetItems[position]
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_budget_item,parent, false)
            val textViewProductDescription = rootView.findViewById<TextView>(R.id.textViewDescription)
            val textViewProductValue = rootView.findViewById<TextView>(R.id.textViewValue)
            val textViewProductDate = rootView.findViewById<TextView>(R.id.textViewDate)

            textViewProductDescription.text = budgetItems[position].description
            textViewProductValue.text = budgetItems[position].value.toString()
            textViewProductDate.text = budgetItems[position].date.toString()

            return rootView
        }

    }
}