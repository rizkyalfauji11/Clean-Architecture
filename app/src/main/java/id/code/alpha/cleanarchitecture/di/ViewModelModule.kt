package id.code.alpha.cleanarchitecture.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.code.alpha.cleanarchitecture.screen.detail.fragment.DetailHospitalViewModel
import id.code.alpha.cleanarchitecture.screen.dialog.FilterViewModel
import id.code.alpha.cleanarchitecture.screen.main.home.HomeViewModel
import id.code.alpha.cleanarchitecture.screen.map.MapsViewModel
import id.code.alpha.cleanarchitecture.screen.splash.SplashScreenViewModel
import id.code.alpha.cleanarchitecture.utils.ViewModelFactory

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapsViewModel::class)
    abstract fun bindMapsViewModel(viewModel: MapsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilterViewModel::class)
    abstract fun bindFilterViewModel(viewModel: FilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailHospitalViewModel::class)
    abstract fun bindDetailViewModel(viewModel: DetailHospitalViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}