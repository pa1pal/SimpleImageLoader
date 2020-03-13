package pa1pal.imageloadersample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pa1pal.simpleimageloader.models.ImageData
import pa1pal.simpleimageloader.repository.Repository
import pa1pal.simpleimageloader.utils.Resource

class MainViewModel : ViewModel() {
    fun getImageData(): LiveData<Resource<List<ImageData>>> =
        Repository.loadImageData()
}