package id.code.alpha.cleanarchitecture.screen.main.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import id.code.alpha.cleanarchitecture.MyApplication
import id.code.alpha.cleanarchitecture.R
import id.code.alpha.cleanarchitecture.databinding.FragmentHomeBinding
import id.code.alpha.cleanarchitecture.screen.detail.DetailActivity
import id.code.alpha.cleanarchitecture.screen.dialog.FilterDialogFragment
import id.code.alpha.cleanarchitecture.screen.main.adapter.HospitalAdapter
import id.code.alpha.cleanarchitecture.screen.search.SearchActivity.Companion.SEARCH_TEXT
import id.code.alpha.cleanarchitecture.utils.ViewModelFactory
import id.code.alpha.cleanarchitecture.utils.verticalLayoutManager
import id.code.alpha.cleanarchitecture.utils.viewLifecycleLazy
import id.code.alpha.domain.model.Hospital
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var adapter: HospitalAdapter

    private val viewBinding by viewLifecycleLazy {
        view?.let {
            FragmentHomeBinding.bind(it)
        }
    }

    private val homeViewModel: HomeViewModel by viewModels {
        factory
    }

    companion object {
        private const val IS_SAVED = "is_saved"
        fun newInstance(searchText: String?, isSaved: Boolean = false) = HomeFragment().apply {
            arguments = Bundle().apply {
                putString(SEARCH_TEXT, searchText)
                putBoolean(IS_SAVED, isSaved)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HospitalAdapter {
            DetailActivity.startDetail(it, activity)
        }
        val layoutManager = verticalLayoutManager()
        viewBinding?.rvHospital?.adapter = adapter
        viewBinding?.rvHospital?.addItemDecoration(
            DividerItemDecoration(
                view.context,
                layoutManager.orientation
            )
        )
        viewBinding?.rvHospital?.layoutManager = verticalLayoutManager()
        viewBinding?.cardFilter?.setOnClickListener {
            val dialog = FilterDialogFragment()
            dialog.show(childFragmentManager, FilterDialogFragment::class.java.simpleName)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val isSaved = arguments?.getBoolean(IS_SAVED, false)
        val textSearch = arguments?.getString(SEARCH_TEXT)

        homeViewModel.getHospital(isSaved ?: false, textSearch)
            .observe(viewLifecycleOwner, observer)
    }

    private val observer = Observer<PagedList<Hospital>> {
        if (it != null) {
            adapter.submitList(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as? MyApplication)?.appComponent?.inject(this)
    }
}