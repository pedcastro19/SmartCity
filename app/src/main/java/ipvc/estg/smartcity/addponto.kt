package ipvc.estg.smartcity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import ipvc.estg.smartcity.api.EndPoints
import ipvc.estg.smartcity.api.Pontos
import ipvc.estg.smartcity.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class addponto : AppCompatActivity() {
    private lateinit var pontoTitInsert: EditText
    private lateinit var pontoDesc: EditText
    private lateinit var pontoTipo: EditText

    //Imagem
    lateinit var imageView: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addponto)

        //Shared Preferences
        lateinit var preferences: SharedPreferences

        //Buscar o ID
        preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE);
        val idPref = preferences.getInt("ID", 0)

        (this as AppCompatActivity).supportActionBar?.title = ""

        pontoTitInsert = findViewById(R.id.pontoTit)
        pontoDesc = findViewById(R.id.pontoDesc)
        pontoTipo = findViewById(R.id.pontoTipo)

        var LocLat : Double
        var LocLon : Double

        LocLat = intent.getDoubleExtra("LocLat",0.0)
        LocLon = intent.getDoubleExtra("LocLon",0.0)
        Log.d("latitude", LocLat.toString())

        val button = findViewById<Button>(R.id.buttonSave)
        button.setOnClickListener {
            inserePonto(LocLat,LocLon,idPref)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode !== Activity.RESULT_CANCELED) {
            if (resultCode == RESULT_OK && requestCode == pickImage) {
                imageView.setImageURI(data?.data)
            }
        }
    }

    private fun inserePonto(LocLat:Double, LocLon:Double,idPref: Int ){

        //Gets the values inserted in the EditText Inputs
        val tit = pontoTitInsert.text.toString()
        val desc = pontoDesc.text.toString()
        val tipo = pontoTipo.text.toString()

        if(tit.isBlank() || desc.isBlank()) {
            Toast.makeText(this, getResources().getString(R.string.preenchercampos), Toast.LENGTH_SHORT).show()
        }
        else{
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.inserirPonto(tit,desc,LocLat.toString(),LocLon.toString(),tipo,idPref)

            call.enqueue(object : Callback<Pontos> {

                override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@addponto, getResources().getString(R.string.sucesso), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@addponto, Mapa::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this@addponto, getResources().getString(R.string.erro), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Pontos>, t: Throwable) {
                    Toast.makeText(this@addponto, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}