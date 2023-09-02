package com.example.petz.dbo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.petz.model.Pet

class DBConnect (context: Context): SQLiteOpenHelper(context, "database.db", null, 1){

    val sql = arrayOf(
        "CREATE TABLE Pet(id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, raca TEXT, localizacao TEXT, idade INTEGER, tipoIdade TEXT)",
        "INSERT INTO Pet(nome, raca, localizacao, idade, tipoIdade) VALUES('Pipoca', 'Cachorro', 'Venice Beach', 2, 'Anos')"
    )

    override fun onCreate(banco: SQLiteDatabase) {
        sql.forEach { banco.execSQL(it) }
    }

    override fun onUpgrade(banco: SQLiteDatabase, p1: Int, p2: Int) {
        banco.execSQL("DROP TABLE Pet")
    }

    // Função de inserir pet
    fun AdicionarNovoPet(pet: Pet){

        val banco = this.writableDatabase

        // Capturar informações
        val valoresInsercao = ContentValues()
        valoresInsercao.put("nome", pet.nome)
        valoresInsercao.put("raca", pet.raca)
        valoresInsercao.put("localizacao", pet.localizacao)
        valoresInsercao.put("idade", pet.idade)
        valoresInsercao.put("tipoIdade", pet.tipoIdade)

        banco.insert("Pet", null, valoresInsercao)
        banco.close()
    }

    fun ListarPet(): List<Pet>{
        val banco = this.readableDatabase
        val comando = banco.rawQuery("SELECT * FROM Pet", null)

        // Criando uma lista temporária de retorno
        val listaPetz = ArrayList<Pet>()

        if(comando.count > 0){
            comando.moveToFirst()  // Ir direto no primeiro item
            while(comando.moveToNext()){
                val indexId = comando.getColumnIndex("id")
                val indexNome = comando.getColumnIndex("nome")
                val indexRaca = comando.getColumnIndex("raca")
                val indexLocalizacao = comando.getColumnIndex("localizacao")
                val indexIdade = comando.getColumnIndex("idade")
                val indexTipoIdade = comando.getColumnIndex("tipoIdade")

                val valorId = comando.getInt(indexId)
                val valorNome = comando.getString(indexNome)
                val valorRaca = comando.getString(indexRaca)
                val valorLocalizacao = comando.getString(indexLocalizacao)
                val valorIdade = comando.getInt(indexIdade)
                val valorTipoIdade = comando.getString(indexTipoIdade)

                val representaPet = Pet(valorId, valorNome, valorRaca, valorLocalizacao, valorIdade, valorTipoIdade)
                listaPetz.add(representaPet)
            }
        }

        banco.close()
        return listaPetz
    }

    // Função de remover pet
    fun RemoverPet(idPet: Int){
        val banco = this.writableDatabase
        banco.delete("Pet", "id=?", arrayOf(idPet.toString()))
        banco.close()
    }

    fun BuscarPet(idPet: Int): Pet? {
        val banco = this.readableDatabase
        val comando = banco.rawQuery("SELECT * FROM Pet WHERE id=?", arrayOf(idPet.toString()))


        if(comando.moveToFirst()){
                val indexId = comando.getColumnIndex("id")
                val indexNome = comando.getColumnIndex("nome")
                val indexRaca = comando.getColumnIndex("raca")
                val indexLocalizacao = comando.getColumnIndex("localizacao")
                val indexIdade = comando.getColumnIndex("idade")
                val indexTipoIdade = comando.getColumnIndex("tipoIdade")

                val valorId = comando.getInt(indexId)
                val valorNome = comando.getString(indexNome)
                val valorRaca = comando.getString(indexRaca)
                val valorLocalizacao = comando.getString(indexLocalizacao)
                val valorIdade = comando.getInt(indexIdade)
                val valorTipoIdade = comando.getString(indexTipoIdade)

                val representaPet = Pet(valorId, valorNome, valorRaca, valorLocalizacao, valorIdade, valorTipoIdade)

                return representaPet
        }

        banco.close()
        return null
    }

    fun AtualizarPet(pet: Pet){

        val banco = this.writableDatabase

        // Capturar informações
        val valoresInsercao = ContentValues()
        valoresInsercao.put("nome", pet.nome)
        valoresInsercao.put("raca", pet.raca)
        valoresInsercao.put("localizacao", pet.localizacao)
        valoresInsercao.put("idade", pet.idade)
        valoresInsercao.put("tipoIdade", pet.tipoIdade)

        banco.update("Pet", valoresInsercao,"id=?", arrayOf(pet.id.toString()))
        banco.close()
    }
}