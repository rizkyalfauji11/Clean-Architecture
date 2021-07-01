package id.code.alpha.cleanarchitecture.screen.map

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import id.code.alpha.cleanarchitecture.MyApplication
import id.code.alpha.cleanarchitecture.R
import id.code.alpha.cleanarchitecture.databinding.ActivityMapsBinding
import id.code.alpha.cleanarchitecture.screen.dialog.FilterDialogFragment
import id.code.alpha.cleanarchitecture.utils.ViewModelFactory
import id.code.alpha.cleanarchitecture.utils.getCameraOption
import id.code.alpha.cleanarchitecture.utils.getMarkerOption
import javax.inject.Inject

class MapsActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMapsBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: MapsViewModel by viewModels {
        factory
    }

    private var maps: SupportMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent.inject(this)
        viewBinding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        maps = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        configureViewModel()

        viewBinding.cardFilter.setOnClickListener {
            val dialog = FilterDialogFragment.newInstance()
            dialog.setOnItemClickListener(object : FilterDialogFragment.OnItemClickListener {
                override fun onItemClick(type: Int, name: String?) {
                    if (type == FilterDialogFragment.RUJUKAN)
                        viewModel.setType(name)
                    else
                        viewModel.setRegion(name)

                    configureViewModel()
                }

            })
            dialog.show(supportFragmentManager, FilterDialogFragment::class.java.simpleName)
        }
    }

    private fun configureViewModel() {
        viewModel.getHospital(false, null).observe(this) {
            maps?.getMapAsync { map ->
                map.clear()
                map.animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                        getCameraOption()
                    )
                )

                it.forEach { item ->
                    if (item?.latitude != null && item.longitude != null) {
                        item.latitude?.let { lat ->
                            item.longitude?.let { long ->
                                map.addMarker(
                                    LatLng(
                                        lat,
                                        long
                                    ).getMarkerOption(this)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}