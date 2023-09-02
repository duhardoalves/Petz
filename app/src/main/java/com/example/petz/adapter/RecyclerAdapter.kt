package com.example.petz.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petz.R
import com.example.petz.activitys.FormularioActivity
import com.example.petz.model.Pet

class RecyclerAdapter(private val context: Context, val listaPetz: List<Pet>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun VincularItensView(pet: Pet){
            val viewNome = itemView.findViewById<TextView>(R.id.nome_pet)
            val viewLocalizacao = itemView.findViewById<TextView>(R.id.local_pet)
            val viewDesc = itemView.findViewById<TextView>(R.id.idade_pet)

            viewNome.text = pet.nome
            viewLocalizacao.text = pet.localizacao
            viewDesc.text = "${pet.raca}, ${pet.idade} ${pet.tipoIdade}"

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_pet_activity, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val pet = listaPetz[position]
        holder.VincularItensView(pet)

        // Clique do card
        holder.itemView.setOnClickListener(){
            val intent = Intent(context, FormularioActivity::class.java)
            intent.putExtra("idPet", listaPetz[position].id)

            (holder.itemView.context as Activity).startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listaPetz.size
    }
}