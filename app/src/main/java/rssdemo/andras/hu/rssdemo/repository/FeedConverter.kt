package rssdemo.andras.hu.rssdemo.repository


import com.einmalfel.earl.Item
import org.xmlpull.v1.XmlPullParserException
import rssdemo.andras.hu.rssdemo.data.Feed
import rssdemo.andras.hu.rssdemo.data.FeedItem
import rssdemo.andras.hu.rssdemo.network.FeedApi
import java.io.IOException
import java.text.DateFormat
import java.util.zip.DataFormatException

class FeedConverter(private val api: FeedApi, private val dateFormat: DateFormat) {

    @Throws(IOException::class, DataFormatException::class, XmlPullParserException::class)
    internal fun convert(feedUrl: String): Feed {
        val rawFeed = api.loadFeed(feedUrl)
        val items = mutableListOf<FeedItem>()
        val feed = Feed(
                description = rawFeed.description ?: "",
                items = items
        )
        rawFeed.items.mapTo(items) { buildFeedItem(it) }
        return feed
    }

    private fun buildFeedItem(rawFeedItem: Item): FeedItem {
        return FeedItem(
                title = rawFeedItem.title ?: "",
                description = rawFeedItem.description ?: "",
                link = rawFeedItem.link ?: "",
                date = try {dateFormat.format(rawFeedItem.publicationDate)} catch (t: Throwable) {""}
        )
    }
}
