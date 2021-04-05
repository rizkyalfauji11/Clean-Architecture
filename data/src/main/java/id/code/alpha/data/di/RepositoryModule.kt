package id.code.alpha.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import id.code.alpha.data.repository.HospitalRepositoryImpl
import id.code.alpha.data.sources.hospital.local.HospitalLocalDataSource
import id.code.alpha.data.sources.hospital.remote.HospitalRemoteDataSource
import id.code.alpha.data.utils.AppExecutors
import id.code.alpha.domain.repository.HospitalRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(
        hospitalRepository: HospitalRepositoryImpl): HospitalRepository
}