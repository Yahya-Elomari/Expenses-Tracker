package com.example.budgettrackerapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.room.Room.databaseBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddTransactionActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_transaction)
        val nameLayout = findViewById<TextInputLayout>(R.id.nameLayout)
        val amountLayout = findViewById<TextInputLayout>(R.id.amountLayout)
        val nameInput = findViewById<TextInputEditText>(R.id.nameInput)
        val amountInput = findViewById<TextInputEditText>(R.id.amountInput)
        val descriptionInput = findViewById<TextInputEditText>(R.id.descriptionInput)
        val addTransactionBtn = findViewById<Button>(R.id.addTransactionBtn)
        val closeBtn = findViewById<ImageButton>(R.id.closeBtn)

        nameInput.addTextChangedListener {
                    if(it!!.count() > 0)
                        nameLayout.error = null
                }

                amountInput.addTextChangedListener {
                    if(it!!.count() > 0)
                        amountLayout.error = null
                }

                addTransactionBtn.setOnClickListener {
                    val name = nameInput.text.toString()
                    val description = descriptionInput.text.toString()
                    val amount = amountInput.text.toString().toDoubleOrNull()

                    if(name.isEmpty())
                        nameLayout.error = "Please enter a valid name"

                    else if(amount == null)
                        amountLayout.error = "Please enter a valid amount"
                    else {
                        val transaction  =Transaction(0, name, amount, description)
                        insert(transaction)
                    }
                }

                closeBtn.setOnClickListener {
                    finish()
                }
            }



            private fun insert(transaction: Transaction){
                val db = databaseBuilder(this,
                    TransactionsDataBase::class.java,
                    "transactions").build()

                GlobalScope.launch {
                    db.transactionDao().insertAll(transaction)
                    finish()
                }
            }

        }