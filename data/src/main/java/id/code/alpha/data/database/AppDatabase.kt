package id.code.alpha.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.code.alpha.data.database.dao.HospitalDao
import id.code.alpha.data.model.local.HospitalEntity

@Database(entities = [HospitalEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun hospitalDao(): HospitalDao
}