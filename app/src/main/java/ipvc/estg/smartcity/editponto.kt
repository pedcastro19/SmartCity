package ipvc.estg.smartcity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ipvc.estg.smartcity.api.EndPoints
import ipvc.estg.smartcity.api.Pontos
import ipvc.estg.smartcity.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class editponto : AppCompatActivity() {

    private lateinit var pontoTitInsert: EditText
    private lateinit var pontoDesc: EditText
    private lateinit var pontoTipo: EditText
    private var IdMarker = 0
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_editponto)

        pontoTitInsert = findViewById(R.id.pontoTit)
        pontoDesc = findViewById(R.id.pontoDesc)
        pontoTipo = findViewById(R.id.pontoTipo)

        IdMarker = intent.getIntExtra("idDoMarkerEdit", 0)

        //Carregar valores do ponto nos EditText
        carregar_pontos(IdMarker)

        //Botão editar
        val button1 = findViewById<Button>(R.id.buttonSave)
        button1.setOnClickListener {
            editPonto(IdMarker)
        }
    }

    fun carregar_pontos(id: Int?) {

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getPontosID(id)

        call.enqueue(object : Callback<Pontos> {

            override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {

                if (response.isSuccessful){
                    val p: Pontos = response.body()!!

                    pontoTitInsert.setText(p.titulo)
                    pontoTipo.setText(p.tipo)
                    pontoDesc.setText(p.descricao)
                }
            }

            override fun onFailure(call: Call<Pontos>, t: Throwable) {
                Toast.makeText(this@editponto, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun editPonto(idPonto: Int ){

        //Gets the values inserted in the EditText Inputs
        val tit = pontoTitInsert.text.toString()
        val desc = pontoDesc.text.toString()
        val tipo = pontoTipo.text.toString()

        if(tit.isBlank() || desc.isBlank()) {
            Toast.makeText(this, getResources().getString(R.string.preenchercampos), Toast.LENGTH_SHORT).show()
        }
        else{
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.editPonto(idPonto,tit,desc,tipo)

            call.enqueue(object : Callback<Pontos> {

                override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {
                    Toast.makeText(this@editponto, getResources().getString(R.string.alterado), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@editponto, markerdescri::class.java)
                    intent.putExtra("idDoMarkerDoEdit", IdMarker)
                    startActivity(intent)
                    finish()
                }

                override fun onFailure(call: Call<Pontos>, t: Throwable) {
                    val intent = Intent(this@editponto, markerdescri::class.java)
                    intent.putExtra("idDoMarkerDoEdit", IdMarker)
                    startActivity(intent)
                    finish()
                }
            })
        }
    }




}