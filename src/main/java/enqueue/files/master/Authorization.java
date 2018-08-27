package enqueue.files.master;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Authorization {
	private String URL = "https://accounts.spotify.com/authorize";
	private String client_id = "00d2b20c278241bd8b8858f58a0be19f";
	private String response_type = "code";
	private String redirect_uri = "enqueue-login://relaunch";
	private String scope = "playlist-modify-public app-remote-control user-library-modify user-follow-modify";
	private String state = generateState();
	
	public Authorization() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("client_id", client_id);
		params.put("response_type", response_type);
		params.put("redirect_uri", redirect_uri);
		params.put("scope", scope);
		params.put("state", state);
		
		try {
			URL url = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(ParamStringBuilder.getParamsString(params));
			out.flush();
			out.close();
		} catch(MalformedURLException e) {
			System.out.println("ERR: PROBLEM WITH URL");
		} catch(IOException e) {
			System.out.println("ERR: PROBLEM OPENING CONNECTION");
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
		Authorization auth = new Authorization();
	
	}
}
