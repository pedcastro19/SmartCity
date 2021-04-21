package ipvc.estg.smartcity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.google.android.material.textfield.TextInputEditText
import ipvc.estg.smartcity.entities.Nota
import ipvc.estg.smartcity.viewmodel.Notaviewmodel
import kotlinx.android.synthetic.main.activity_nova_nota.*
import kotlinx.android.synthetic.main.linelayout.*

class NovaNota : AppCompatActivity() {

    private lateinit var mNotaviewmodel: Notaviewmodel
    private lateinit var titulo: TextInputEditText
    private lateinit var descricao: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_nota)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_nova_nota)
        appbar.setNavigationOnClickListener {
            val intent = Intent(this, Notepad::class.java)
            startActivity(intent)
        }

        titulo = findViewById(R.id.titletext)
        descricao = findViewById((R.id.notatext))

        mNotaviewmodel = ViewModelProvider(this).get(Notaviewmodel::class.java)
        // mNotaviewmodel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Notaviewmodel::class.java)

        addbutton.setOnClickListener {
            insertDataToDatabase()
        }

    }


    private fun insertDataToDatabase() {
        val titulo = titletext.text.toString()
        val descricao = notatext.text.toString()
        //val horadata = addHoradata.text.toString()

        if (inputCheck(titulo, descricao)) {
            //Criar nota
            val nota = Nota(0, titulo, descricao)
            //Adicionar nota
            mNotaviewmodel.addNota(nota)
            Toast.makeText(this, "Adicionado com Sucesso!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Notepad::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Preenche todos os campos.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(titulo: String, descricao: String): Boolean {
        return !(TextUtils.isEmpty(titulo) && TextUtils.isEmpty(descricao))
    }

}