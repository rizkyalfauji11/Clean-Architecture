package id.code.alpha.cleanarchitecture.di

import dagger.Component
import id.code.alpha.cleanarchitecture.screen.detail.fragment.DetailHospitalFragment
import id.code.alpha.cleanarchitecture.screen.dialog.FilterDialogFragment
import id.code.alpha.cleanarchitecture.screen.main.home.HomeFragment
import id.code.alpha.cleanarchitecture.screen.map.MapsActivity
import id.code.alpha.cleanarchitecture.screen.splash.SplashScreenActivity
import id.code.alpha.data.di.CoreComponent

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(activity: SplashScreenActivity)
    fun inject(activity: MapsActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: DetailHospitalFragment)
    fun inject(fragment: FilterDialogFragment)
}