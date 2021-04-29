package ipvc.estg.smartcity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import ipvc.estg.smartcity.entities.Nota
import ipvc.estg.smartcity.viewmodel.Notaviewmodel
import kotlinx.android.synthetic.main.activity_nova_nota.*
import kotlinx.android.synthetic.main.activity_nova_nota.appbar
import kotlinx.android.synthetic.main.editarnota.*
import kotlinx.android.synthetic.main.notepad.*

class editarnota : AppCompatActivity() {

    private lateinit var mNotaviewmodel: Notaviewmodel

    var IdItem = 0
    var descvalor = ""
    var titvalor = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarnota)
        try {this.supportActionBar!!.hide()}
        catch (e: NullPointerException) {}

        appbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete-> {
                    deleteitem(IdItem)
                    true
                }
                else -> false
            }
        }



        appbar.setNavigationOnClickListener {
            val intent = Intent(this, Notepad::class.java)
            startActivity(intent)
        }


        var notaTit = findViewById<TextInputEditText>(R.id.edittitulo)
        var notaDesc = findViewById<TextInputEditText>(R.id.editnota)

        IdItem = intent.getIntExtra("iddoItem", 0)

        descvalor = intent.getStringExtra("descvalor").toString()
        titvalor = intent.getStringExtra("titvalor").toString()

        notaDesc.setText(descvalor)
        notaTit.setText(titvalor)

        mNotaviewmodel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Notaviewmodel::class.java)

        val button = findViewById<FloatingActionButton>(R.id.updatebutton)
                    button.setOnClickListener{
                        update(IdItem)
                    }
    }


    private fun update(IdItem: Int){

        var notaTit = findViewById<TextInputEditText>(R.id.edittitulo)
        var notaDesc = findViewById<TextInputEditText>(R.id.editnota)

        Log.d("teste" , IdItem.toString())

        val titulo = notaTit.text.toString()
        val desc = notaDesc.text.toString()

            if(titulo.isBlank() || desc.isBlank() ){
                Toast.makeText(this, "Existem campos em branco!", Toast.LENGTH_SHORT).show()
            }
            else{
                val updatedNota = Nota(IdItem, titulo, desc)
                mNotaviewmodel.updateNotas(updatedNota)
                finish()
            }
    }

    private fun deleteitem(IdItem: Int){
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton(getResources().getString(R.string.yes)) { _, _ ->
            mNotaviewmodel.deleteporid(IdItem)
            finish()
            Toast.makeText(
                this,
                getResources().getString(R.string.doneone),
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(getResources().getString(R.string.no)) { _, _ -> }
        builder.setTitle(getResources().getString(R.string.deleteone))
        builder.create().show()
    }
    }