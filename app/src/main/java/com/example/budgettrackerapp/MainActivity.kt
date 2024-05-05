package com.example.budgettrackerapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var transactions : ArrayList<Transaction>
    private lateinit var transactionsAdapter : TransactionsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerview:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transactions = arrayListOf(
            Transaction("Monthly Budget",4000.00),
            Transaction("Food Budget",-2000.00),
            Transaction("Medical Budget",-300.00),
            Transaction("Subscriptions Budget",-85.00),
            Transaction("Internet Budget",-200.00),
            Transaction("Car Budget",0.00),
        )
        transactionsAdapter = TransactionsAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview = findViewById(R.id.recyclerview)
        recyclerview.adapter = transactionsAdapter
        recyclerview.layoutManager = linearLayoutManager

    }
}