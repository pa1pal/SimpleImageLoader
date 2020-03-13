package pa1pal.simpleimageloader.repository

import androidx.lifecycle.LiveData
import okhttp3.ResponseBody
import pa1pal.simpleimageloader.App
import pa1pal.simpleimageloader.api.ApiService
import pa1pal.simpleimageloader.db.ImageDao
import pa1pal.simpleimageloader.db.ImageDataDao
import pa1pal.simpleimageloader.db.ImageDb
import pa1pal.simpleimageloader.models.Image
import pa1pal.simpleimageloader.models.ImageData
import pa1pal.simpleimageloader.utils.*
import java.util.concurrent.TimeUnit

class Repository {
    companion object {
        val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)
        val apiService: ApiService = ApiService.instance
        val imageDataDao: ImageDataDao = ImageDb.getInstance(App.applicationContext()).imageDataDao()
        val imageDao: ImageDao = ImageDb.getInstance(App.applicationContext()).imageDao()

        fun loadImageData(): LiveData<Resource<List<ImageData>>> {
            return object : NetworkBoundResource<List<ImageData>, List<ImageData>>(AppExecutors) {
                override fun saveCallResult(item: List<ImageData>) {
                    imageDataDao.insertImages(item)
                }

                override fun shouldFetch(data: List<ImageData>?): Boolean {
                    return data == null || data.isEmpty()
                }

                override fun loadFromDb() = imageDataDao.getImagesAndUser()

                override fun createCall() = apiService.getImageData()

                override fun onFetchFailed() {
                    repoListRateLimit.reset("Images")
                }
            }.asLiveData()
        }

        fun loadDbImage(imageUrl: String) = imageDao.getImage(imageUrl)

        fun loadImage(imageUrl: String,
                      height: Int,
                      width: Int,
                      compressPercent: Int): LiveData<Resource<Image>> {
            return object : NetworkBoundResource<Image, ResponseBody>(AppExecutors) {
                override fun saveCallResult(item: ResponseBody) {
                    imageDao.insert(
                        Image(
                            imageUrl,
                            ImageUtils.compressBitmap(
                                item.source().readByteArray(),
                                compressPercent
                            )
                        )
                    )
                }

                override fun shouldFetch(data: Image?): Boolean {
                    return data == null || data.data != null
                }

                override fun loadFromDb() = imageDao.getImage(imageUrl)

                override fun createCall() = apiService.getImage(imageUrl, height, width)

                override fun onFetchFailed() {
                    repoListRateLimit.reset("Image")
                }
            }.asLiveData()
        }
    }
}
