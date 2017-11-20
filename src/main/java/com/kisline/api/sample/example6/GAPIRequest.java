package com.kisline.api.sample.example6;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GAPIRequest extends Base {

	private Map<String, String> paramMap = new HashMap();
	private Map<String, String> apiMap = new HashMap();

	public GAPIRequest(Map<String, String> paramMap, Map<String, String> apiMap) {
		this.paramMap = paramMap;
		this.apiMap = apiMap;
	}

	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();

	public static class APIUrl extends GenericUrl {

		public APIUrl(String encodedUrl) {
			super(encodedUrl);
		}

		@Key
		public String fields;
	}

	public HttpResponse apiCall() {
		HttpResponse response = null;

		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(JSON_FACTORY));
			}
		});

		try {
			String apiURL = "";

			String key = "";
			String value = "";
			
			apiURL = apiMap.get("apiURL");

			StringBuilder sb = new StringBuilder();

			for (Iterator<String> it = paramMap.keySet().iterator(); it.hasNext();) {
				key = String.valueOf(it.next());
				value = String.valueOf(paramMap.get(key));
				sb.append("&");
				sb.append(key);
				sb.append("=");
				sb.append(value);
			}

			APIUrl url = new APIUrl(APIBASEURL + apiURL + "?uid=" + UID + sb);

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(RESPONSETYPE);
			headers.put("x-ibm-client-id", CLIENTID);
			headers.put("x-ibm-client-secret", CLIENTSECRET);

			HttpRequest request = requestFactory.buildGetRequest(url).setHeaders(headers);

			response = request.execute();

		} catch (IOException ioe) {
			log.error("APIRequest", ioe);
		} catch (Exception e) {
			log.error("APIRequest", e);
		}
		return response;
	}

}
