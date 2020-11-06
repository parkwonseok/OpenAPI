package com.sprpa.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Geocoder {
	String epsgX = null;
	String epsgY = null;
	
	public Geocoder(String address) throws IOException, ParseException {
		String json;
		address = address.replace(" ", "%20");
		String urlStr = "http://apis.vworld.kr/jibun2coord.do?q=" + address + "&output=json&epsg=epsg:4326&apiKey=28614AE3-676F-3459-8A94-03A785A660D2";
		BufferedReader br;
		URL url;
		HttpURLConnection conn;
		String protocol = "GET";

		url = new URL(urlStr);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(protocol);
		br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		json = br.readLine();
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(json);
		JSONObject jsonObj = (JSONObject) obj;
		epsgX = (String) jsonObj.get("EPSG_4326_X");
		epsgY = (String) jsonObj.get("EPSG_4326_Y");
	}
}