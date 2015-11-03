package alberto.examples.streams.parallel;

import org.apache.commons.lang3.time.StopWatch;

import java.util.stream.Stream;

/**
 * ParallelStream
 *
 * @author <a href="mailto:nobuj_000@gmail.com">nobuj_000</a>
 * @version $Id$
 * @since Nov 3, 2015
 *
 */
public class ParallelStream {

    /**
     * Represents main
     *
     * @param args
     * @since Nov 3, 2015
     *
     */
    public static void main(final String args[]) {
	final StopWatch stopwatch = new StopWatch();
	stopwatch.start();
	final int stringsLength = Stream.of("This is the first", "then the second", "third?", "another one", "5", "last one :)")
		.parallel()
		.map((string) -> {
		    try {
			return sleepAndCountChars(string);
		    }
		    catch (final InterruptedException e) {
			e.printStackTrace();
			return 0;
		    }
		})
		.reduce(0, (a,b)-> a+b);
	System.out.printf("Total number of characters: %d\n", stringsLength);
	stopwatch.stop();
	System.out.printf("Elapsed time %f seconds", stopwatch.getNanoTime()/1000000000f);

    }

    /**
     * Represents sleepAndCountChars
     *
     * @param string
     * @throws InterruptedException
     * @since Nov 3, 2015
     *
     * @todo complete description
     */
    private static int sleepAndCountChars(final String string) throws InterruptedException {
	Thread.sleep(5000);
	return string.length();
    }


}
