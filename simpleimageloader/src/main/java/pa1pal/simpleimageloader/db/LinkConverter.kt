package pa1pal.simpleimageloader.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pa1pal.simpleimageloader.models.Links

class LinkConverter {

    @TypeConverter
    fun stringToLinks(data: String?): Links? {
        val type = object : TypeToken<Links>() {}.type
        return data?.let { Gson().fromJson(data, type) }
    }

    @TypeConverter
    fun linksToString(links: Links?): String? {
        return links?.let { Gson().toJson(links) }
    }
}
