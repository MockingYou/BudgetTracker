package com.example.budgettracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_add_transaction.*
import kotlinx.android.synthetic.main.activity_add_transaction.amountInput
import kotlinx.android.synthetic.main.activity_add_transaction.amountLayout
import kotlinx.android.synthetic.main.activity_add_transaction.closeBtn
import kotlinx.android.synthetic.main.activity_add_transaction.descriptionInput
import kotlinx.android.synthetic.main.activity_add_transaction.labelInput
import kotlinx.android.synthetic.main.activity_add_transaction.labelLayout
import kotlinx.android.synthetic.main.activity_detailed.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        labelInput.addTextChangedListener {
            if(it!!.isNotEmpty()) {
                labelLayout.error = null
            }
        }
        amountInput.addTextChangedListener {
            if(it!!.isNotEmpty()) {
                amountLayout.error = null
            }
        }

        update_button.setOnClickListener{
            val label = labelInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()
            val description = descriptionInput.text.toString()

            if(label.isEmpty()) {
                labelLayout.error = "Please enter a valid label"
            }
            if(amount == null) {
                amountLayout.error = "Please enter a valid amount"
            } else {
                val transaction = Transaction(0, label, amount, description)
                update(transaction)
            }
        }
        closeBtn.setOnClickListener {
            finish()
        }
    }
    private fun update(transaction: Transaction) {
       val db = Room.databaseBuilder(this, AppDatabase::class.java, "transactions").build()
        GlobalScope.launch {
            db.transactionDao().update(transaction)
            finish()
        }
    }
}