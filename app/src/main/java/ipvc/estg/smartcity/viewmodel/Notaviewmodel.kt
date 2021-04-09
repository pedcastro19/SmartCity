package ipvc.estg.smartcity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ipvc.estg.smartcity.database.NotasDatabase
import ipvc.estg.smartcity.entities.Nota
import ipvc.estg.smartcity.repositorio.Notarepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Notaviewmodel(application: Application): AndroidViewModel(application) {

    private val lertudo: LiveData<List<Nota>>
    private val repositorio: Notarepositorio

    init{
        val notaDao = NotasDatabase.getDatabase(application).notaDAO()
        repositorio = Notarepositorio(notaDao)
        lertudo = repositorio.lertudo
    }

    fun addNota(nota: Nota){
        viewModelScope.launch (Dispatchers.IO){
            repositorio.addNota(nota)
        }
    }

}