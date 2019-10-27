package com.grok.akm.sphtest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.grok.akm.sphtest.model.MobileDataResponse

@Database(
    entities = [MobileDataResponse::class],
    version = 1,
    exportSchema = false
)
abstract class DataDB : RoomDatabase() {

    abstract fun dataDao(): DataDao

    companion object {

        @Volatile
        private var INSTANCE: DataDB? = null

        fun getDatabase(context: Context): DataDB? {
            if (INSTANCE == null) {
                synchronized(DataDB::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DataDB::class.java, "data_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}