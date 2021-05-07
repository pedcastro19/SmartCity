package ipvc.estg.smartcity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ipvc.estg.smartcity.api.EndPoints
import ipvc.estg.smartcity.api.Pontos
import ipvc.estg.smartcity.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class markerdescri : AppCompatActivity() {

    lateinit var preferences: SharedPreferences

    private lateinit var Titulo: TextView
    private lateinit var Tipo: TextView
    private lateinit var Descricao: TextView

    private var iddoUserMarker = 0
    private var IdMarker = 0
    private lateinit var TituloValor: String
    private lateinit var DescValor: String
    private lateinit var TipoValor: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_markerdescri)

        Titulo = findViewById<TextView>(R.id.outputTituloMarker)
        Tipo = findViewById<TextView>(R.id.outputTipoMarker)
        Descricao = findViewById<TextView>(R.id.OutputMarkerDescricao)

        IdMarker = intent.getIntExtra("idDoMarker", 0)
        Log.d("idmarker", IdMarker.toString())

        if (IdMarker == 0) {
            IdMarker = intent.getIntExtra("idDoMarkerDoEdit", 0)
        }

        carregar_pontos(IdMarker)

        val button = findViewById<Button>(R.id.buttonEditar)
        button.setOnClickListener {
            preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE);
            val idPref = preferences.getInt("ID", 0)

            if (idPref.equals(iddoUserMarker)) {
                val intent = Intent(this@markerdescri, editponto::class.java)
                intent.putExtra("idDoMarkerEdit", IdMarker)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@markerdescri, R.string.noedit, Toast.LENGTH_SHORT).show()
            }
        }

        val button1 = findViewById<Button>(R.id.buttonVoltar)
        button1.setOnClickListener {
            val intent = Intent(this@markerdescri, Mapa::class.java)
            startActivity(intent)
            finish()
        }

        val buttondelete = findViewById<Button>(R.id.buttonapagar)
        buttondelete.setOnClickListener {
            preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE);
            val idPref = preferences.getInt("ID", 0)

            if(idPref.equals(iddoUserMarker)){

                val builder = AlertDialog.Builder(this)
                builder.setPositiveButton(getResources().getString(R.string.yes)) { _, _ ->
                    deletePonto(IdMarker)
                }
                builder.setNegativeButton(getResources().getString(R.string.no)) { _, _ -> }
                builder.setTitle(getResources().getString(R.string.deleteconfirma))
                builder.create().show()

            }
            else{
                Toast.makeText(this@markerdescri, R.string.nodelete, Toast.LENGTH_SHORT).show()
            }
        }
        }


    fun carregar_pontos(id: Int?) {

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getPontosID(id)


        call.enqueue(object : Callback<Pontos> {

            override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {

                if (response.isSuccessful) {
                    val p: Pontos = response.body()!!
                    Log.d("sucesso", "sucesso")
                    Titulo.setText(p.titulo)
                    Tipo.setText(p.tipo)
                    Descricao.setText(p.descricao)

                    TituloValor = p.titulo
                    DescValor = p.tipo
                    TipoValor = p.descricao

                    iddoUserMarker = p.user_id
                }
            }

            override fun onFailure(call: Call<Pontos>, t: Throwable) {
                Toast.makeText(this@markerdescri, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun deletePonto(id: Int?) {

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.deletePonto(id)

        call.enqueue(object : Callback<Pontos> {

            override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {

                Toast.makeText(this@markerdescri, R.string.deletemarker, Toast.LENGTH_SHORT).show()

                val intent = Intent(this@markerdescri, Mapa::class.java)
                startActivity(intent)
                finish()

            }

            override fun onFailure(call: Call<Pontos>, t: Throwable) {
                Toast.makeText(this@markerdescri, R.string.deletemarker, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@markerdescri, Mapa::class.java)
                startActivity(intent)
                finish()
                //Toast.makeText(this@marker_desc, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onBackPressed() {
        val intent = Intent(this@markerdescri, Mapa::class.java)
        startActivity(intent)
        finish()
    }
}
