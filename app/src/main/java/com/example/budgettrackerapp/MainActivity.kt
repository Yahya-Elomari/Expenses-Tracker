package com.example.budgettrackerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var transactions: ArrayList<Transaction>
    private lateinit var transactionsAdapter: TransactionsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerview: RecyclerView
    private lateinit var balance: TextView
    private lateinit var budget: TextView
    private lateinit var expense: TextView
    private lateinit var addBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        balance = findViewById(R.id.balance)
        budget = findViewById(R.id.budget)
        expense = findViewById(R.id.expense)
        recyclerview = findViewById(R.id.recyclerview)
        addBtn = findViewById(R.id.btn_add)

        transactions = arrayListOf(
            Transaction("Monthly Budget", 4000.00),
            Transaction("Food Budget", -2000.00),
            Transaction("Medical Budget", -300.00),
            Transaction("Subscriptions Budget", -85.00),
            Transaction("Internet Budget", -200.00),
            Transaction("Car Budget", 0.00),
        )
        transactionsAdapter = TransactionsAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview.adapter = transactionsAdapter
        recyclerview.layoutManager = linearLayoutManager

        updateDashboard()

        addBtn.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateDashboard() {
        val totalAmount = transactions.map { it.amount }.sum()
        val budgetAmount = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
        val expenseAmount = totalAmount - budgetAmount

        balance.text = "$%.2f".format(totalAmount)
        budget.text = "$%.2f".format(budgetAmount)
        expense.text = "$%.2f".format(expenseAmount)
    }
}
