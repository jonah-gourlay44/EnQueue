package enqueue.files.master;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTP_Server {
		private static String code;
		
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
	        	HttpURLConnection con = (HttpURLConnection) new URL(Authorization_URI.authorizationCodeUri_Sync().toString())
	        			.openConnection();
	        	con.setRequestMethod("GET");
	        	Headers responseHeaders = t.getResponseHeaders();
	        	responseHeaders.set("Location", Authorization_URI.authorizationCodeUri_Sync().toString());
	        	t.sendResponseHeaders(302, 0);
	        	
	        }
	    }
	    
	    static class RedirectHandler implements HttpHandler {
	    	@Override
	    	public void handle(HttpExchange t) throws IOException {
	    		String response = "Hello World";
	    		t.sendResponseHeaders(200,  response.length());
	    		OutputStream os = t.getResponseBody();
	    		os.write(response.getBytes());
	    		os.close();
	    		code = t.getRequestURI().toString().split("=")[1].split("&")[0];
	    		System.out.println(code);
	    		Authorization_Code.authorizationCode_Sync();
	    	}
	    }
	}
