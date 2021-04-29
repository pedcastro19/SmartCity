package ipvc.estg.smartcity.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ipvc.estg.smartcity.entities.Nota

@Dao
interface notaDAO {

    @Insert( onConflict = OnConflictStrategy.IGNORE)
   suspend fun addNota(nota: Nota)

    @Query("SELECT * FROM tabela_notas ORDER BY id ASC")
    fun lertudo(): LiveData<List<Nota>>

    @Update
    suspend fun updateNotas(nota: Nota)

    @Query("DELETE FROM tabela_notas WHERE id == :id")
    suspend fun deleteporid(id: Int)

    @Query("DELETE FROM tabela_notas")
    suspend fun deleteAllNotas()
}