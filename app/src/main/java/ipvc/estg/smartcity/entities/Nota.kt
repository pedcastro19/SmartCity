package ipvc.estg.smartcity.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_notas")

data class Nota (
    @PrimaryKey(autoGenerate = true)
        val id: Int,
        val titulo: String,
        val texto: String,
        val horaData: String

)