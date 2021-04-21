package ipvc.estg.smartcity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.smartcity.viewmodel.Notaviewmodel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.notepad.*

class Notepad : AppCompatActivity() {

    private lateinit var mNotaviewmodel: Notaviewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notepad)
        try {this.supportActionBar!!.hide()}
        catch (e: NullPointerException) {}

        bottomAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }
        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    // Handle search icon press
                    true
                }
                R.id.more -> {
                    // Handle more item (inside overflow menu) press
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

        //val view = inflater.inflate(R.layout.notepad, container, false)

        //RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = ListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //ViewModel
        mNotaviewmodel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Notaviewmodel::class.java)
        //mNotaviewmodel = ViewModelProvider(this).get(Notaviewmodel::class.java)
        mNotaviewmodel.lertudo.observe(this, Observer { nota ->
            adapter.setData(nota)
        })




    }

}