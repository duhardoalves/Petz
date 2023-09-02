package com.example.petz.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.petz.R
import com.example.petz.adapter.RecyclerAdapter
import com.example.petz.dbo.DBConnect
import com.example.petz.model.Pet

class MainActivity : AppCompatActivity() {

    // Criando a conexão com o banco
    private val dbconnect = DBConnect(this)

    // Identificando as views
    lateinit var recycler: RecyclerView
    lateinit var buttonAdd: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAdd = findViewById(R.id.btn_adicionar)
        recycler = findViewById(R.id.recyclerView)



        buttonAdd.setOnClickListener(){
            val intent = Intent(this, FormularioActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        recycler.adapter = RecyclerAdapter(this, dbconnect.ListarPet())
    }
}