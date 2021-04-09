package ipvc.estg.smartcity.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.estg.smartcity.entities.Nota

@Dao
interface notaDAO {

    @Insert( onConflict = OnConflictStrategy.IGNORE)
   suspend fun addNota(nota: Nota)

    @Query("SELECT * FROM tabela_notas ORDER BY id ASC")
    fun lertudo(): LiveData<List<Nota>>
}