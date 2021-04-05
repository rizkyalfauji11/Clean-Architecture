package id.code.alpha.cleanarchitecture.screen.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import id.code.alpha.cleanarchitecture.R
import id.code.alpha.cleanarchitecture.base.BaseActivity
import id.code.alpha.cleanarchitecture.databinding.ActivityDetailBinding
import id.code.alpha.cleanarchitecture.screen.detail.fragment.DetailHospitalFragment
import id.code.alpha.cleanarchitecture.utils.replaceFragment
import id.code.alpha.domain.model.Hospital

class DetailActivity : BaseActivity() {
    private lateinit var viewBinding: ActivityDetailBinding

    companion object {
        private const val HOSPITAL = "hospital"
        fun startDetail(hospital: Hospital?, context: Context?) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(HOSPITAL, hospital)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        replaceFragment(
            R.id.container, DetailHospitalFragment.newInstance(
                intent.getParcelableExtra(
                    HOSPITAL
                )
            )
        )
        viewBinding.toolbar.title = resources.getString(R.string.detail_activity)
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}