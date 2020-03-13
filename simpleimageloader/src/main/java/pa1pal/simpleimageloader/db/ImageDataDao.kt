package pa1pal.simpleimageloader.db

import androidx.lifecycle.LiveData
import androidx.room.*
import pa1pal.simpleimageloader.models.ImageData

@Dao
abstract class ImageDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg image: ImageData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertImages(images: List<ImageData>)

    @Transaction
    @Query("SELECT * FROM imagedata")
    abstract fun getImagesAndUser(): LiveData<List<ImageData>>
}
