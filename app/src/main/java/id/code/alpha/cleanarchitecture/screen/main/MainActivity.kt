package id.code.alpha.cleanarchitecture.screen.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.code.alpha.cleanarchitecture.R
import id.code.alpha.cleanarchitecture.databinding.ActivityMainBinding
import id.code.alpha.cleanarchitecture.screen.main.home.HomeFragment
import id.code.alpha.cleanarchitecture.screen.search.SearchActivity
import id.code.alpha.cleanarchitecture.screen.search.SearchActivity.Companion.SEARCH_TEXT
import id.code.alpha.cleanarchitecture.utils.replaceFragment

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SEARCH_REQUEST_CODE = 100
    }

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        replaceFragment(
            R.id.container,
            HomeFragment.newInstance(viewBinding.textSearch.text.toString())
        )

        viewBinding.toolbar.title = resources.getString(R.string.app_name)
        setSupportActionBar(viewBinding.toolbar)

        viewBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> inflateFragment()
                R.id.menu_saved -> inflateFragment(true)
            }
            return@setOnNavigationItemSelectedListener true
        }

        configureListener()
    }

    private fun configureListener() {
        viewBinding.textSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra(SEARCH_TEXT, viewBinding.textSearch.text.toString())
            startActivityForResult(intent, SEARCH_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            viewBinding.textSearch.text = data?.getStringExtra(SEARCH_TEXT)
            if (viewBinding.bottomNavigation.selectedItemId == R.id.menu_home) {
                inflateFragment()
            } else {
                inflateFragment(true)
            }
        }
    }

    private fun inflateFragment(isSaved: Boolean = false) {
        replaceFragment(
            R.id.container,
            HomeFragment.newInstance(viewBinding.textSearch.text.toString(), isSaved)
        )
    }
}