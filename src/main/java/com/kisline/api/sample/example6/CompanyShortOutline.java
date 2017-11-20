package com.kisline.api.sample.example6;

import java.util.HashMap;
import java.util.Map;

import com.google.api.client.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompanyShortOutline extends Base {
	private static final String APIURL = "/companyOutlineIfo/companyShortOutline";

	public static void main(String[] args) {

		try {
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();

			Map<String, String> paramMap = new HashMap();
			Map<String, String> apiMap = new HashMap();

			paramMap.put("kiscode", "380725");

			apiMap.put("apiURL", APIURL);

			GAPIRequest apir = new GAPIRequest(paramMap, apiMap);

			HttpResponse response = apir.apiCall();

			String json = new String(response.parseAsString().getBytes("8859_1"), "utf-8");

			log.info("Response Body INFO");
			log.info(json);

			JsonElement element = parser.parse(json).getAsJsonObject().get("items").getAsJsonObject().get("item");

			ResultObject[] example = gson.fromJson(element, ResultObject[].class);

			for (ResultObject temp : example) {
				log.info("kiscode = " + temp.getKiscode() + ", amnisuyn : " + temp.getAmnisuyn());
			}
		} catch (Exception ioe) {
			log.error("CompanyShortOutline", ioe);
		}
	}

}
