package id.code.alpha.cleanarchitecture.di

import dagger.Binds
import dagger.Module
import id.code.alpha.domain.usecase.HospitalUseCase
import id.code.alpha.domain.usecase.HospitalUseCaseImpl

@Module
abstract class AppModule {
    @Binds
    abstract fun provideHospitalUseCase(hospitalUseCaseImpl: HospitalUseCaseImpl): HospitalUseCase
}