package com.example.budgettrackerapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var transactions: List<Transaction>
    private lateinit var transactionsAdapter: TransactionsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerview: RecyclerView
    private lateinit var balance: TextView
    private lateinit var budget: TextView
    private lateinit var expense: TextView
    private lateinit var addBtn: FloatingActionButton
    private lateinit var db:TransactionsDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        balance = findViewById(R.id.balance)
        budget = findViewById(R.id.budget)
        expense = findViewById(R.id.expense)
        recyclerview = findViewById(R.id.recyclerview)
        addBtn = findViewById(R.id.btn_add)

        transactions = arrayListOf()
        transactionsAdapter = TransactionsAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview.adapter = transactionsAdapter
        recyclerview.layoutManager = linearLayoutManager

        db = Room.databaseBuilder(this,TransactionsDataBase::class.java,"transactions").build()

        fetchAll()

        addBtn.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchAll(){
        GlobalScope.launch {
//            db.transactionDao().insertAll(Transaction(0,"ice cream",-3.0,"yummy"))
            transactions = db.transactionDao().getAll()
            runOnUiThread {
                updateDashboard()
                transactionsAdapter.setData(transactions)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateDashboard() {
        val totalAmount = transactions.sumOf { it.amount }
        val budgetAmount = transactions.filter { it.amount > 0 }.sumOf { it.amount }
        val expenseAmount = totalAmount - budgetAmount

        balance.text = "$%.2f".format(totalAmount)
        budget.text = "$%.2f".format(budgetAmount)
        expense.text = "$%.2f".format(expenseAmount)
    }
}
