package com.grok.akm.sphtest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grok.akm.sphtest.model.MobileDataResponse
import io.reactivex.Maybe

@Dao
abstract class DataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun setData(data: MobileDataResponse)

    @Query("SELECT * from MobileDataResponse ORDER BY id ASC")
    abstract fun getData() : Maybe<MobileDataResponse>


    @Query("DELETE FROM MobileDataResponse")
    abstract fun deleteAll()

}