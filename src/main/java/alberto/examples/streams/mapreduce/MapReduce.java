package alberto.examples.streams.mapreduce;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;

import java.net.URL;

/**
 * MapReduce
 *
 * @author <a href="mailto:nobuj_000@gmail.com">nobuj_000</a>
 * @version $Id$
 * @since Nov 2, 2015
 *
 */
public class MapReduce {

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
	final URL feedSource = new URL("http://www.chicagotribune.com/news/local/breaking/rss2.0.xml");
	final SyndFeedInput input = new SyndFeedInput();
	// Create a SyndFeed object using an XMLReader object with the RSS feed
	// source URL
	final SyndFeed feed = input.build(new XmlReader(feedSource));
	final String string = "chicago";

	final int ocurrences = feed.getEntries().stream()
		.map(entry -> checkIfStringPresentInTitle(entry, string))
		.reduce(0, (a, b) -> a + b); // adds all values

	System.out.printf("Total news with %s in the title is %d", string, ocurrences);
    }

    private static int checkIfStringPresentInTitle(final SyndEntry entry, final String string) {
	final String title = entry.getTitle().toLowerCase();
	return title.indexOf(string.toLowerCase()) > 0 ? 1 : 0;
    }

}
