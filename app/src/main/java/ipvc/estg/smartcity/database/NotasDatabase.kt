package ipvc.estg.smartcity.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ipvc.estg.smartcity.dao.notaDAO
import ipvc.estg.smartcity.entities.Nota

@Database(entities = [Nota::class], version = 1, exportSchema = false)

abstract class NotasDatabase: RoomDatabase(){

    abstract fun notaDAO(): notaDAO

    companion object{
        @Volatile
        private var INSTANCE: NotasDatabase? = null

        fun getDatabase(context: Context): NotasDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotasDatabase::class.java,
                    "notas_database"
                ).build()
                INSTANCE = instance
                return instance
            }


        }
    }

}