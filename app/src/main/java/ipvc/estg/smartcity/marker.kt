package ipvc.estg.smartcity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class marker : AppCompatActivity() {

    lateinit var preferences: SharedPreferences

    private lateinit var Titulo: TextView
    private lateinit var Tipo: TextView
    private lateinit var Descricao: TextView
    //private lateinit var imagem: ImageView

    private var iddoUserMarker = 0
    private var IdMarker = 0
    private lateinit var TituloValor: String
    private lateinit var DescValor: String
    private lateinit var TipoValor: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try { this.supportActionBar!!.hide()} catch (e: NullPointerException) {}
        setContentView(R.layout.activity_marker)

        Titulo = findViewById<TextView>(R.id.outputTituloMarker)
        Tipo = findViewById<TextView>(R.id.outputTipoMarker)
        Descricao = findViewById<TextView>(R.id.OutputMarkerDescricao)

    }
}