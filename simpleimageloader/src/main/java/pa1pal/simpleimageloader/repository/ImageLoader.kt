package pa1pal.simpleimageloader.repository

import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import pa1pal.simpleimageloader.App
import pa1pal.simpleimageloader.R
import java.util.*

class ImageLoader constructor(var lifecycleOwner: LifecycleOwner) {
    private val imageViewMap: MutableMap<ImageView, String> =
        Collections.synchronizedMap(WeakHashMap<ImageView, String>())

    fun loadImage(
        imageView: ImageView,
        imageUrl: String,
        height: Int = 128,
        width: Int = 128,
        compressPercent: Int = 50
    ) {
        imageView.setImageResource(0)
        if (imageViewMap.containsValue(imageUrl)) {
            Repository.loadDbImage(imageUrl).observe(lifecycleOwner, Observer {
                if (it?.data != null) {
                    imageView.setImageBitmap(
                        BitmapFactory.decodeByteArray(
                            it.data, 0, it.data.size
                        )
                    )
                }
            })
        } else {
            imageViewMap[imageView] = imageUrl
            Repository.loadImage(imageUrl, height, width, compressPercent)
                .observe(lifecycleOwner, Observer { networkImage ->
                    if (networkImage.data != null) {
                        imageView.setImageBitmap(
                            BitmapFactory.decodeByteArray(
                                networkImage.data.data, 0, networkImage.data.data!!.size
                            )
                        )
                    } else {
                        imageView.setImageDrawable(
                            ContextCompat.getDrawable(
                                App.applicationContext(),
                                R.drawable.ic_launcher_background
                            )
                        )
                    }
                })
        }
    }

    fun clean() {
        imageViewMap.apply { this.clear() }
    }
}
