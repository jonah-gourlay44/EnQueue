package enqueue.files.master;

import java.io.IOException;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTP_Server {
		private static String code;
    	private static URI uri = Authorization_URI.authorizationCodeUri_Sync();
   
    	
		public static String getCode() {
			return code;
		}

	    public static void main(String[] args) throws Exception {
	        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
	        server.createContext("/test", new TestHandler());
	        server.createContext("/callback", new RedirectHandler()); 
	        server.setExecutor(null); // creates a default executor
	        server.start();
	    }

	    static class TestHandler implements HttpHandler {
	        @Override
	        public void handle(HttpExchange t) throws IOException {
	        	
	        	HttpURLConnection con = (HttpURLConnection) new URL(uri.toString())
	        			.openConnection();
	        	con.setRequestMethod("GET");
	        	Headers responseHeaders = t.getResponseHeaders();
	        	responseHeaders.set("Location", uri.toString());
	        	t.sendResponseHeaders(302, 0);
	        	
	        }
	    }
	    
	    static class RedirectHandler implements HttpHandler {
	    	@Override
	    	public void handle(HttpExchange t) throws IOException {
	    		if(new myUri(t.getRequestURI()).getState().equals(new myUri(uri).getState())) {
	    			String response = "Hello World";
	    			t.sendResponseHeaders(200,  response.length());
	    			OutputStream os = t.getResponseBody();
	    			os.write(response.getBytes());
	    			os.close();
	    			code = new myUri(t.getRequestURI()).getCode();
	    			Authorization_Code.authorizationCode_Sync();
	    		}
	    	}
	    }
	}
