package ch.epfl.sweng.quizapp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * A default implementation of the {@link NetworkProvider} interface that uses
 * the mechanism available in the {@link URL} object to create
 * {@link HttpURLConnection} objects.
 * @author lcg31439
 *
 */
public class DefaultNetworkProvider implements NetworkProvider {

    /**
     * The default constructor.
     */
    public DefaultNetworkProvider() {
    }

	@Override
	public HttpURLConnection getConnection(URL url) throws IOException {
		
		String protocol = url.getProtocol();
		HttpURLConnection connection = null;
		
		if (protocol.equals("http") || protocol.equals("https")) {
			try {
				connection = (HttpURLConnection) url.openConnection();
			} catch (MalformedURLException e) {
				throw new IOException(e);
			} catch (ProtocolException e) {
				throw new IOException(e);
			} catch (IOException e) {
				throw new IOException(e);
			}
		} else {
			throw new IOException("URL is not HTTP/HTTPS");
		}
		
		return connection;
	}

}