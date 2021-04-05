package id.code.alpha.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import id.code.alpha.data.database.AppDatabase
import javax.inject.Named
import javax.inject.Singleton

@Module
class DatabaseModule {
    companion object {
        const val HOSPITAL_DAO = "HOSPITAL_DAO"
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "db_hospital.db"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Named(HOSPITAL_DAO)
    fun provideHospitalDao(database: AppDatabase) = database.hospitalDao()
}