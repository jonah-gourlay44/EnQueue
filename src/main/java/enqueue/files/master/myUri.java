package enqueue.files.master;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class myUri {
	private final URI uri;
	private final Map<String, String> params; 
	
	public myUri(URI uri) {
		params = new HashMap<String, String>();
		this.uri = uri;
		String[] paramArr = uri.toASCIIString().split("\\?")[1].split("&");
		for(String s : paramArr) {
			String[] kV = s.split("=");
			params.put(kV[0], kV[1]);
		}
	}
	
	public URI getUri() { 
		return uri;
	}
	
	public String getState() {
		return params.entrySet()
					 .stream()
					 .filter(e -> e.getKey().equals("state"))
					 .collect(Collectors.toList())
					 .get(0).getValue();
	}
	
	public String getCode() {
		return params.entrySet()
				     .stream()
				     .filter(e -> e.getKey().equals("code"))
				     .collect(Collectors.toList())
				     .get(0).getValue();
	}
	
	public static void main(String[] args) {
		myUri uri = new myUri(Authorization_URI.authorizationCodeUri_Sync());
		System.out.println(uri.getState());
	}

}
