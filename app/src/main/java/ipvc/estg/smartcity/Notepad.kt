package ipvc.estg.smartcity

import android.R.id.toggle
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import ipvc.estg.smartcity.viewmodel.Notaviewmodel
import kotlinx.android.synthetic.main.linelayout.*
import kotlinx.android.synthetic.main.notepad.*


class Notepad : AppCompatActivity() {

    private lateinit var mNotaviewmodel: Notaviewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notepad)
        try {this.supportActionBar!!.hide()}
        catch (e: NullPointerException) {}

        bottomAppBar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete-> {
                    deleteAll()
                    true
                }
                else -> false
            }
        }

        val buttonfab = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab)
        buttonfab.setOnClickListener{

            val intent = Intent(this, NovaNota::class.java)
            startActivity(intent)
        }


        //RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = ListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //ViewModel
        mNotaviewmodel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Notaviewmodel::class.java)
        mNotaviewmodel.lertudo.observe(this, Observer { nota ->
            adapter.setData(nota)
        })
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton(getResources().getString(R.string.yes)) { _, _ ->
            mNotaviewmodel.deleteAllNotas()
            Toast.makeText(
                this,
                getResources().getString(R.string.done),
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(getResources().getString(R.string.no)) { _, _ -> }
        builder.setTitle(getResources().getString(R.string.deleteevery))
        builder.setMessage(getResources().getString(R.string.areyousure))
        builder.create().show()
    }


}