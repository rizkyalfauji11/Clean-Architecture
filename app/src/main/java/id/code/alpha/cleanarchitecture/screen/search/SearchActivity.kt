package id.code.alpha.cleanarchitecture.screen.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.code.alpha.cleanarchitecture.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    companion object {
        const val SEARCH_TEXT = "searchText"
    }

    private lateinit var viewBinding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setSearchText()
        configureListener()
    }

    private fun configureListener() {
        viewBinding.imageSearch.setOnClickListener {
            val intent = Intent()
            intent.putExtra(SEARCH_TEXT, viewBinding.etSearch.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun setSearchText() {
        val searchText = intent?.getStringExtra(SEARCH_TEXT)
        viewBinding.etSearch.setText(searchText)
    }
}