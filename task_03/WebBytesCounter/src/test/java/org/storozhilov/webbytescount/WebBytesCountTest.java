package org.storozhilov.webbytescount;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class WebBytesCountTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public WebBytesCountTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( WebBytesCountTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testWebBytesCounter()
    {
        assertTrue( true );
    }

    public void testLogLine()
    {
	String s = "ip1 - - [24/Apr/2011:04:06:01 -0400] \"GET /~strabal/grease/photo9/927-3.jpg HTTP/1.1\" 200 40028 \"-\" \"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\"";
	WebBytesCount.LogLine logLine = new WebBytesCount.LogLine(s);
	assertEquals("ip1", logLine.ip);
	assertEquals(40028, logLine.bytes);
	assertEquals("Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)", logLine.userAgent);
    }
}
