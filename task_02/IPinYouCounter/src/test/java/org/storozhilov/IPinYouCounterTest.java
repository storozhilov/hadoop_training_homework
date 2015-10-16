package org.storozhilov;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class IPinYouCounterTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public IPinYouCounterTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( IPinYouCounterTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testIPinYouCounter()
    {
        assertTrue( true );
    }
}
