package enqueue.files.master;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ParamStringBuilder {
	public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder paramStr = new StringBuilder();
		paramStr.append("/?");
		
		for(Map.Entry<String, String> entry : params.entrySet()) {
			paramStr.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			paramStr.append("=");
			paramStr.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			paramStr.append("&");
		}
		
		String query = paramStr.toString();
		return query.length() > 0 ? query.substring(0, query.length() - 1) : query;
	}
}
