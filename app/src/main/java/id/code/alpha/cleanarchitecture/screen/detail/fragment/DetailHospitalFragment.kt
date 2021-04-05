package id.code.alpha.cleanarchitecture.screen.detail.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.View.INVISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.code.alpha.cleanarchitecture.MyApplication
import id.code.alpha.cleanarchitecture.R
import id.code.alpha.cleanarchitecture.databinding.FragmentDetailHospitalBinding
import id.code.alpha.cleanarchitecture.utils.ViewModelFactory
import id.code.alpha.cleanarchitecture.utils.viewLifecycleLazy
import id.code.alpha.domain.model.Hospital
import javax.inject.Inject

class DetailHospitalFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: DetailHospitalViewModel by viewModels {
        factory
    }
    private val binding by viewLifecycleLazy {
        view?.let {
            FragmentDetailHospitalBinding.bind(it)
        }
    }
    private var hospital: Hospital? = null
    private var saveMenuItem: MenuItem? = null

    companion object {
        private const val HOSPITAL = "hospital"
        fun newInstance(hospital: Hospital?) = DetailHospitalFragment().apply {
            arguments = Bundle().apply {
                putParcelable(HOSPITAL, hospital)
            }
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val latLong = LatLng(hospital?.latitude ?: 0.0, hospital?.longitude ?: 0.0)
        googleMap.addMarker(MarkerOptions().position(latLong).title(hospital?.name))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLong))
        val cameraPosition = CameraPosition.Builder()
            .target(latLong)
            .zoom(17f)
            .bearing(90f)
            .tilt(30f)
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_hospital, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        with(binding) {
            val data = arguments?.getParcelable<Hospital?>(HOSPITAL)
            this?.name?.text = data?.name
            this?.textAddress?.text = data?.address
            this?.textBedCount?.text = data?.bedCount.toString()
            this?.textType?.text = data?.type?.replace("_".toRegex(), " ")
            this?.textPhoneNumber?.text = data?.phoneNumber
            if (data?.phoneNumber.isNullOrEmpty())
                this?.textPhoneNumber?.visibility = INVISIBLE
        }

        setHasOptionsMenu(true)

        viewModel.data.observe(viewLifecycleOwner, {
            hospital = it
            checkMenu()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_saved -> {
                viewModel.changeSavedHospitalState(hospital)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
        saveMenuItem = menu.findItem(R.id.menu_saved)
        checkMenu()
    }

    private fun checkMenu() {
        if (hospital?.isFavorite == true)
            saveMenuItem?.setIcon(R.drawable.ic_saved)
        else
            saveMenuItem?.setIcon(R.drawable.ic_unsaved)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as? MyApplication)?.appComponent?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hospital = arguments?.getParcelable<Hospital?>(HOSPITAL)
    }
}