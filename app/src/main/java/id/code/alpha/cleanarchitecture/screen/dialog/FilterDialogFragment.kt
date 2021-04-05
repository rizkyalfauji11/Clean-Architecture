package id.code.alpha.cleanarchitecture.screen.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.code.alpha.cleanarchitecture.MyApplication
import id.code.alpha.cleanarchitecture.R
import id.code.alpha.cleanarchitecture.databinding.FragmentFilterDialogBinding
import id.code.alpha.cleanarchitecture.screen.dialog.adapter.FilterAdapter
import id.code.alpha.cleanarchitecture.screen.dialog.adapter.FilterItemDecoration
import id.code.alpha.cleanarchitecture.utils.*
import javax.inject.Inject

class FilterDialogFragment : BottomSheetDialogFragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var adapter: FilterAdapter

    companion object {
        @JvmStatic
        fun newInstance() =
            FilterDialogFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private val viewModel: FilterViewModel by viewModels {
        factory
    }

    private val viewBinding by viewLifecycleLazy {
        view?.let {
            FragmentFilterDialogBinding.bind(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FilterAdapter {
        }
        val layoutManager = verticalLayoutManager()
        viewBinding?.rvReference?.adapter = adapter
        viewBinding?.rvReference?.addItemDecoration(
            FilterItemDecoration(16.dp)
        )
        viewBinding?.rvReference?.layoutManager = horizontalLayoutManager()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getReferenceHospitalType()
            .observe(viewLifecycleOwner, {
                adapter.submitList(it)
            })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as? MyApplication)?.appComponent?.inject(this)
    }
}