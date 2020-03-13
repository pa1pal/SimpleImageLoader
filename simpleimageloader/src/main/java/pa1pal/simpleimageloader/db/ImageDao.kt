package pa1pal.simpleimageloader.db

import androidx.lifecycle.LiveData
import androidx.room.*
import pa1pal.simpleimageloader.models.Image

@Dao
abstract class ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg image: Image)

    @Query("SELECT * FROM image WHERE imageUrl = :url")
    abstract fun getImage(url: String): LiveData<Image>
}