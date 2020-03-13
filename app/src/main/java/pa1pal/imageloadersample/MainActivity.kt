package pa1pal.imageloadersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import pa1pal.simpleimageloader.repository.ImageLoader
import pa1pal.simpleimageloader.utils.Status

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var imageLoader: ImageLoader
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        imageLoader = ImageLoader(this)
//        Testing direct link
        imageLoader.loadImage(imageView, "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_1280.jpg",
            32,32,50)
        adapter = MainAdapter(imageLoader)
        recycler_view.adapter = adapter
        fetchRecentImages()
        refresh_layout.setOnRefreshListener { showProgressBar(); fetchRecentImages() }
    }

    private fun fetchRecentImages() {
        mainViewModel.getImageData().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    refresh_layout.isRefreshing = false
                    if (it.data != null && it.data!!.isNotEmpty()) {
                        recycler_view.visibility = View.VISIBLE
                        adapter.submitList(it.data!!)
                    }
                }
                Status.ERROR -> {
                    refresh_layout.isRefreshing = false
                }
                Status.LOADING -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun showProgressBar() {
        refresh_layout.isRefreshing = true
        recycler_view.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        imageLoader.clean()
    }
}
