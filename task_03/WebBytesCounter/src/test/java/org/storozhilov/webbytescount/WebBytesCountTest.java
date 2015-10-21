package org.storozhilov.webbytescount;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void testRegExp()
    {
	String s = "ip1 - - [24/Apr/2011:04:06:01 -0400] \"GET /~strabal/grease/photo9/927-3.jpg HTTP/1.1\" 200 40028 \"-\" \"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\"";
	Matcher m = Pattern.compile(WebBytesCount.WebBytesCountMapper.regexp).matcher(s);
	assertTrue(m.find());
	assertEquals("ip1", m.group(1));
	assertTrue(m.find());
	assertEquals("-", m.group(1));
	assertTrue(m.find());
	assertEquals("-", m.group(1));
	assertTrue(m.find());
	assertEquals("[24/Apr/2011:04:06:01 -0400]", m.group(1));
	assertTrue(m.find());
	assertEquals("\"GET /~strabal/grease/photo9/927-3.jpg HTTP/1.1\"", m.group(1));
	assertTrue(m.find());
	assertEquals("200", m.group(1));
	assertTrue(m.find());
	assertEquals("40028", m.group(1));
	assertTrue(m.find());
	assertEquals("\"-\"", m.group(1));
	assertTrue(m.find());
	assertEquals("\"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\"", m.group(1));
    }
}
