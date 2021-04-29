package ipvc.estg.smartcity.repositorio

import androidx.lifecycle.LiveData
import ipvc.estg.smartcity.dao.notaDAO
import ipvc.estg.smartcity.entities.Nota

class Notarepositorio(private val notaDAO: notaDAO) {

    val lertudo: LiveData<List<Nota>> = notaDAO.lertudo()

    suspend fun addNota(nota: Nota){
        notaDAO.addNota(nota)
    }

    suspend fun updateNotas(nota: Nota){
        notaDAO.updateNotas(nota)
    }

    suspend fun deleteporid(id: Int){
        notaDAO.deleteporid(id)
    }

    suspend fun deleteAllNotas(){
        notaDAO.deleteAllNotas()
    }


}