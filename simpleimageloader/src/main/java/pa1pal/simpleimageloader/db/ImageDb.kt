package pa1pal.simpleimageloader.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pa1pal.simpleimageloader.models.Image
import pa1pal.simpleimageloader.models.ImageData
import pa1pal.simpleimageloader.utils.SingletonHolder

@Database(entities = [ImageData::class, Image::class], version = 1, exportSchema = false)
@TypeConverters(LinkConverter::class, UrlConverter::class, ProfileImageConverter::class)
abstract class ImageDb : RoomDatabase() {

    abstract fun imageDataDao(): ImageDataDao

    abstract fun imageDao(): ImageDao

    companion object : SingletonHolder<ImageDb, Context>({
        Room.inMemoryDatabaseBuilder(it.applicationContext, ImageDb::class.java)
            .fallbackToDestructiveMigration()
            .build()
    })
}