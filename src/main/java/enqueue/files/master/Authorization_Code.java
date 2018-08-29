package enqueue.files.master;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Authorization_Code {
	private static final String clientId = "00d2b20c278241bd8b8858f58a0be19f";
	private static final String clientSecret = "55a47182289b49ce96689c4470397a56";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8000/callback");
	private static final String code = HTTP_Server.getCode();
	
	private static final SpotifyApi spotifyApi =new SpotifyApi.Builder()
		.setClientId(clientId)
		.setClientSecret(clientSecret)
		.setRedirectUri(redirectUri)
		.build();
	
	private static final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
		.build();
	
	public static void authorizationCode_Sync() {
		System.out.println(code);
		try {
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
			
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
			spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
			
			System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
		} catch (IOException | SpotifyWebApiException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
