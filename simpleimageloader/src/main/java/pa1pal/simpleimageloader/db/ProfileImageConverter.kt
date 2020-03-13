package pa1pal.simpleimageloader.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pa1pal.simpleimageloader.models.ProfileImage

object ProfileImageConverter {

    @TypeConverter
    @JvmStatic
    fun stringToProfileImage(data: String?): ProfileImage? {
        val type = object : TypeToken<ProfileImage>() {}.type
        return data?.let { Gson().fromJson(data, type) }
    }

    @TypeConverter
    @JvmStatic
    fun profileImageToString(profileImage: ProfileImage?): String? {
        return profileImage?.let { Gson().toJson(profileImage) }
    }
}