package alberto.examples.streams.rsstext;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import org.apache.commons.lang3.time.StopWatch;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import java.net.URL;

/**
 * NewsTextContent
 *
 * @author <a href="mailto:nobuji.saito@gmail.com">nobuji.saito</a>
 * @version $Id$
 * @since Nov 2, 2015
 *
 */
public class NewsTextContent {

    /**
     * Represents main
     *
     * @param args
     * @throws IllegalArgumentException
     * @throws FeedException
     * @throws IOException
     * @since Nov 2, 2015
     *
     */
    public static void main(final String args[]) throws IllegalArgumentException, FeedException, IOException {
	final StopWatch stopwatch = new StopWatch();
	stopwatch.start();
	final URL feedSource = new URL("http://www.chicagotribune.com/news/local/breaking/rss2.0.xml");
	final SyndFeedInput input = new SyndFeedInput();
	// Create a SyndFeed object using an XMLReader object with the RSS feed
	// source URL
	final SyndFeed feed = input.build(new XmlReader(feedSource));
	feed.getEntries().stream()
	.map(entry -> {
	    try {
		return getNewsTextContent(entry);
	    }
	    catch(final IOException e) {
		return "Unable to obtain text";
	    }
	})
	.forEach(System.out::println);
	stopwatch.stop();
	System.out.printf("Elapsed time %f seconds", stopwatch.getNanoTime()/1000000000f);
    }

    /**
     * Represents getNewsTextContent
     *
     * @param entry
     * @return
     * @throws IOException
     * @since Nov 2, 2015
     *
     */
    private static String getNewsTextContent(final SyndEntry entry) throws IOException {
	final Document doc = Jsoup.connect(entry.getUri()).get();
	return doc.text();
    }
}
