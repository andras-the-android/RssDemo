package rssdemo.andras.hu.rssdemo.network;


import com.einmalfel.earl.EarlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.DataFormatException;

public class FeedApi {

    public com.einmalfel.earl.Feed loadFeed(String feedUrl) throws IOException, DataFormatException, XmlPullParserException {
        InputStream inputStream = new URL(feedUrl).openConnection().getInputStream();
        return EarlParser.parseOrThrow(inputStream, 0);
    }
}
