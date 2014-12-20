/*
 * Copyright 2014 EPFL. All rights reserved.
 */

package ch.epfl.sweng.quizapp.test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import org.mockito.Mockito;

import ch.epfl.sweng.quizapp.DefaultNetworkProvider;
import ch.epfl.sweng.testing.MockTestCase;

/** Tests the DefaultNetworkProvider */
public class DefaultNetworkProviderTest extends MockTestCase {
    private DefaultNetworkProvider dnp;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dnp = new DefaultNetworkProvider();
    }

    /**
     * Test that getConnection() calls url.openConnection() and does not tamper
     * with it.
     */
    public void testOpenConnectionCalled() throws IOException {
        final HttpURLConnection expected = Mockito.mock(HttpURLConnection.class);
        URL url = new URL("http", "example.com", -1, "/", new URLStreamHandler() {

            @Override
            protected URLConnection openConnection(URL u) throws IOException {
                return expected;
            }
        });

        HttpURLConnection result = dnp.getConnection(url);
        assertSame("Wrong URL method called", expected, result);
        Mockito.verifyZeroInteractions(expected);
    }
}
