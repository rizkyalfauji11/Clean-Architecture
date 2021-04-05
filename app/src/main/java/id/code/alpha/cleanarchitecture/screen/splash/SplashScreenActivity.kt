package id.code.alpha.cleanarchitecture.screen.splash

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import id.code.alpha.cleanarchitecture.MyApplication
import id.code.alpha.cleanarchitecture.base.BaseActivity
import id.code.alpha.cleanarchitecture.databinding.ActivitySplashScreenBinding
import id.code.alpha.cleanarchitecture.screen.main.MainActivity
import id.code.alpha.cleanarchitecture.utils.ViewModelFactory
import id.code.alpha.cleanarchitecture.utils.startActivity
import id.code.alpha.cleanarchitecture.utils.visible
import id.code.alpha.domain.Resource
import javax.inject.Inject

class SplashScreenActivity : BaseActivity() {
    private lateinit var viewBinding: ActivitySplashScreenBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: SplashScreenViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent.inject(this)
        viewBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel.hospitals.observe(this) { data ->
            if (data != null) {
                when (data) {
                    is Resource.Success<*> -> {
                        startActivity(MainActivity::class.java)
                        finish()
                    }

                    is Resource.Loading<*> -> {
                        viewBinding.progressBar.visible()
                        viewBinding.textPleaseWait.visible()
                    }
                    is Resource.Error<*> -> {
                        Log.e("Splash", data.message.toString())
                    }
                }
            }
        }
    }

}