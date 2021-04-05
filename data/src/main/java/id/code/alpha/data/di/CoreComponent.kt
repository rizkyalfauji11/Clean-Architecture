package id.code.alpha.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.code.alpha.domain.repository.HospitalRepository
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideRepository(): HospitalRepository
}