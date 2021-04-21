package ipvc.estg.smartcity.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ipvc.estg.smartcity.dao.notaDAO
import ipvc.estg.smartcity.entities.Nota
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Nota::class], version = 1, exportSchema = false)

abstract class NotasDatabase: RoomDatabase(){

    abstract fun notaDAO(): notaDAO

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var notaDAO = database.notaDAO()
                    var nota = Nota(1, "Viana do Castelo", "Portugal")
                    notaDAO.addNota(nota)

                }
            }
        }
    }



    companion object{
        @Volatile
        private var INSTANCE: NotasDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NotasDatabase{
            val tempInstance =
                INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotasDatabase::class.java,
                    "Notas_Database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }




}