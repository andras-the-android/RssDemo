package rssdemo.andras.hu.rssdemo.network


import com.einmalfel.earl.EarlParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.net.URL
import java.util.zip.DataFormatException

class FeedApi {

    @Throws(IOException::class, DataFormatException::class, XmlPullParserException::class)
    fun loadFeed(feedUrl: String): com.einmalfel.earl.Feed {
        val inputStream = URL(feedUrl).openConnection().getInputStream()
        return EarlParser.parseOrThrow(inputStream, 0)
    }
}
