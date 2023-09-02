package com.example.petz.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.petz.R
import com.example.petz.dbo.DBConnect
import com.example.petz.model.Pet

class FormularioActivity : AppCompatActivity() {

    // Identificando as views
    lateinit var nomeView : EditText
    lateinit var racaView : EditText
    lateinit var localizacaoView : EditText
    lateinit var idadeView : EditText
    lateinit var tipoIdadeView: RadioGroup

    lateinit var btnAdicionar : Button
    lateinit var btnEditar : Button
    lateinit var btnRemover : Button

    // Conexão com o banco
    val dbConnect = DBConnect(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        // Identificadores das views
        nomeView = findViewById(R.id.editNome)
        racaView = findViewById(R.id.editRaca)
        localizacaoView = findViewById(R.id.editLocalizacao)
        idadeView = findViewById(R.id.editIdade)
        tipoIdadeView = findViewById(R.id.radioGroup)

        btnAdicionar = findViewById(R.id.btnCadastrar)
        btnEditar = findViewById(R.id.btnAlterar)
        btnRemover = findViewById(R.id.btnRemover)



        // Clique de adicionar
        btnAdicionar.setOnClickListener(){
            val valorNome = nomeView.text.toString()
            val valorRaca = racaView.text.toString()
            val valorLocalizacao = localizacaoView.text.toString()
            val valorIdade = idadeView.text.toString().toInt()
            val valorTipoIdade = VerificarTipoIdade(valorIdade, tipoIdadeView.checkedRadioButtonId)

            val novoPet = Pet(0, valorNome, valorRaca, valorLocalizacao, valorIdade, valorTipoIdade)
            dbConnect.AdicionarNovoPet(novoPet)
        }

        btnEditar.setOnClickListener(){
            val receboInformacao = intent.extras

            if(receboInformacao != null){
                val valorId = receboInformacao.getInt("idPet")
                val valorNome = nomeView.text.toString()
                val valorRaca = racaView.text.toString()
                val valorLocalizacao = localizacaoView.text.toString()
                val valorIdade = idadeView.text.toString().toInt()
                val valorTipoIdade = VerificarTipoIdade(valorIdade, tipoIdadeView.checkedRadioButtonId)

                val novoPet = Pet(valorId, valorNome, valorRaca, valorLocalizacao, valorIdade, valorTipoIdade)
                dbConnect.AtualizarPet(novoPet)
                finish()
            }


        }

        // Clique de remoção
        btnRemover.setOnClickListener(){
            val receboInformacao = intent.extras
            if(receboInformacao != null){
                dbConnect.RemoverPet(receboInformacao.getInt("idPet"))
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // Recebendo o id do pet
        val intentExtras = intent.extras



        if(intentExtras != null){
            btnAdicionar.visibility = View.INVISIBLE
            btnEditar.visibility = View.VISIBLE
            btnRemover.visibility = View.VISIBLE


            // Salvar os dados consultados do pet
            val petConsultado = dbConnect.BuscarPet(intentExtras.getInt("idPet"))

            if(petConsultado != null){
                Toast.makeText(this,"${petConsultado.nome}", Toast.LENGTH_SHORT).show()
                nomeView.setText(petConsultado.nome)
                racaView.setText(petConsultado.raca)
                localizacaoView.setText(petConsultado.localizacao)
                idadeView.setText(petConsultado.idade.toString())

                if(petConsultado.tipoIdade == "Anos" || petConsultado.tipoIdade == "Ano"){
                    findViewById<RadioButton>(R.id.radioAno).isChecked = true
                }else{
                    findViewById<RadioButton>(R.id.radioMes).isChecked = true
                }

            }

        }
    }

    // Função de verificar o tipo da idade
    fun VerificarTipoIdade(idade: Int, radioChecado: Int): String{

        if(radioChecado == R.id.radioMes){
            if(idade > 1){
                return "Meses"
            }else{
                return "Mês"
            }
        }else{
            if(idade > 1){
                return "Anos"
            }else{
                return "Ano"
            }
        }
    }
}
