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

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * RxJavaExample
 *
 * @author <a href="mailto:nobuji.saito@gmail.com">nobuji.saito</a>
 * @version $Id$
 * @since Nov 8, 2015
 *
 */
public class NewsTextContentRxJava {

    /**
     * Represents main
     *
     * @param args
     * @throws IllegalArgumentException
     * @throws FeedException
     * @throws IOException
     * @since Nov 8, 2015
     *
     */
    public static void main(final String[] args) throws IllegalArgumentException, FeedException, IOException {

	final URL feedSource = new URL("http://www.chicagotribune.com/news/local/breaking/rss2.0.xml");
	final SyndFeedInput input = new SyndFeedInput();
	// Create a SyndFeed object using an XMLReader object with the RSS feed
	// source URL
	final SyndFeed feed = input.build(new XmlReader(feedSource));

	final StopWatch stopwatch = new StopWatch();
	stopwatch.start();

	// Create an Observable Object from the feed entries
	final Observable<SyndEntry> obs = Observable.from(feed.getEntries());
	// Apply flatMap operation to the observable
	obs.flatMap(entry -> {
	        // defer the execution of obtaining the URL text with JSoup
		return Observable.defer(() -> {
		    try {
			return Observable.just(getNewsContentText(entry));
		    }
		    catch (final Exception e) {
			return null;
		    }
		}).subscribeOn(Schedulers.newThread());
	}).toBlocking().forEach((text) -> {
	   System.out.printf("%s : %s\n", Thread.currentThread().getName(), text);
	});

	stopwatch.stop();
	System.out.printf("Elapsed time %f seconds\n", stopwatch.getNanoTime() / 1000000000f);
    }

    private static String getNewsContentText(final SyndEntry entry) throws IOException {
	final Document doc = Jsoup.connect(entry.getUri()).get();
	return doc.text();
    }

}
