package enqueue.files.master;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.json.JsonObject;

import org.apache.http.Header;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.SpotifyHttpManager.Builder;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

public class Authorization_URI {
	private static final String client_id = "00d2b20c278241bd8b8858f58a0be19f";
	private static final String client_secret = "55a47182289b49ce96689c4470397a56";
	private static final URI redirect_uri = SpotifyHttpManager.makeUri("http://localhost:8000/callback");
	private static final String scope = "playlist-modify-public,app-remote-control,user-library-modify,user-follow-modify,streaming";
	private static final String state = generateState();
	
	private static final SpotifyApi enqueue = new SpotifyApi.Builder()
			.setClientId(client_id)
			.setClientSecret(client_secret)
			.setRedirectUri(redirect_uri)
			.build();
	
	private static final AuthorizationCodeUriRequest enqueueAuth = enqueue.authorizationCodeUri()
			.state(state)
			.scope(scope)
			.show_dialog(true)
			.build();
	
	public static URI authorizationCodeUri_Sync() {
	    final URI uri = enqueueAuth.execute();
	    
	    System.out.println("URI: " + uri.toString());
	    return uri;
	  }
	
	public static void authorizationCodeUri_Async() {
	    try {
	      final Future<URI> uriFuture = enqueueAuth.executeAsync();

	      // ...

	      final URI uri = uriFuture.get();

	      System.out.println("URI: " + uri.toString());
	    } catch (InterruptedException | ExecutionException e) {
	      System.out.println("Error: " + e.getCause().getMessage());
	    }
	 } 
	
	private static String generateState() {
		String code = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
		StringBuilder bldr = new StringBuilder();
		
		while(bldr.length() < 10 ) {
			Random rnd = new Random();
			int index = (int) (rnd.nextFloat() * code.length());
			bldr.append(code.charAt(index));
		}
		
		return bldr.toString();
	}
	
	public static void main(String[] args) {
	
	}
	
}
