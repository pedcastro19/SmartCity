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

     val lertudo: LiveData<List<Nota>>
    private val repositorio: Notarepositorio

    init{
        val notaDao = NotasDatabase.getDatabase(application, viewModelScope).notaDAO()
        repositorio = Notarepositorio(notaDao)
        lertudo = repositorio.lertudo
    }

    fun addNota(nota: Nota){
        viewModelScope.launch (Dispatchers.IO){
            repositorio.addNota(nota)
        }
    }

    fun updateNotas(nota: Nota) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.updateNotas(nota)
        }
    }

   fun deleteporid(id: Int){
       viewModelScope.launch(Dispatchers.IO) {
       repositorio.deleteporid(id)
        }
   }

    fun deleteAllNotas(){
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.deleteAllNotas()
        }
    }


}