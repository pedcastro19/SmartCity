package ipvc.estg.smartcity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import ipvc.estg.smartcity.viewmodel.Notaviewmodel
import kotlinx.android.synthetic.main.activity_nova_nota.*
import kotlinx.android.synthetic.main.activity_nova_nota.appbar
import kotlinx.android.synthetic.main.editarnota.*

class editarnota : AppCompatActivity() {

    private lateinit var mNotaviewmodel: Notaviewmodel

    var descvalor = ""
    var titvalor = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarnota)
        try {this.supportActionBar!!.hide()}
        catch (e: NullPointerException) {}
        appbar.setNavigationOnClickListener {
            val intent = Intent(this, Notepad::class.java)
            startActivity(intent)
        }

        var notaTit = findViewById<TextInputEditText>(R.id.edittitulo)
        var notaDesc = findViewById<TextInputEditText>(R.id.editnota)

        descvalor = intent.getStringExtra("descvalor").toString()
        titvalor = intent.getStringExtra("titvalor").toString()

        notaDesc.setText(descvalor)
        notaTit.setText(titvalor)

        mNotaviewmodel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Notaviewmodel::class.java)

        val button = findViewById<FloatingActionButton>(R.id.updatebutton)
                    button.setOnClickListener{
                        update(idItem)
                    }

    }



}