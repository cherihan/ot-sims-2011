package google_api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleGeoApiCached {
	
	public static Hashtable<String, Double> getCoordOfAddress(String address) {
		return GoogleGeoApi.getCoordOfAddress(address);
	}

	public static String getNearAddressFromCoord(Hashtable<String, Double> coords){
			return GoogleGeoApi.getNearAddressFromCoord(coords);
	}
}
