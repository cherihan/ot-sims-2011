package google_api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleGeoApi {
	public static String api_key = "ABQIAAAAnrqyN8PQj6Xnh3pejC_7JRT2yXp_ZAY8_ufC3CFXhHIE1NvwkxTvtmOkjl2kxp54yXOXd3Wg2pLyDg";

	public static Hashtable<String, Double> getCoordOfAddress(String address) {
		try {
			String uri = new String(
					"http://maps.googleapis.com/maps/api/geocode/json?sensor=false");
			uri = uri.concat("&address=").concat(
					URLEncoder.encode(address, "UTF-8"));

			uri = uri.concat("&language=fr&region=fr");

			String responseText = GoogleGeoApi.execHttpRequest(uri);

			JSONObject responseJson = new JSONObject(responseText);

			if (!responseJson.getString("status").equals("OK")) {
				throw new Exception();
			}

			double latitude;
			double longitude;

			JSONArray results = responseJson.getJSONArray("results");

			if (results.length() == 0) {
				throw new Exception();
			}

			JSONObject location = ((JSONObject) results.get(0)).getJSONObject(
					"geometry").getJSONObject("location");
			latitude = location.getDouble("lat");
			longitude = location.getDouble("lng");

			Hashtable<String, Double> result;
			result = new Hashtable<String, Double>();
			result.put("longitude", longitude);
			result.put("latitude", latitude);
			return result;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		return null;
	}

	public static String getNearAddressFromCoord(Hashtable<String, Double> coords) {
		try {
			String uri = new String(
					"http://maps.googleapis.com/maps/api/geocode/json?sensor=false");
			uri = uri.concat("&latlng=").concat(
					URLEncoder.encode(coords.get("latitude").toString(),
							"UTF-8"));
			uri = uri.concat(",").concat(
					URLEncoder.encode(coords.get("longitude").toString(),
							"UTF-8"));
			uri = uri.concat("&language=fr&region=fr");

			String responseText = GoogleGeoApi.execHttpRequest(uri);

			JSONObject responseJson = new JSONObject(responseText);

			if (!responseJson.getString("status").equals("OK")) {
				throw new Exception();
			}

			String addressText;

			JSONArray results = responseJson.getJSONArray("results");
			
			if (results.length() == 0) {
				throw new Exception();
			}
			
			JSONObject addressJson = ((JSONObject) results.get(0));
			addressText = addressJson.getString("formatted_address");

			return addressText;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		return null;
	}

	protected static String execHttpRequest(String url) throws Exception {
		URL yahoo = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yahoo.openStream()));

		String inputLine;
		String output = new String();

		while ((inputLine = in.readLine()) != null) {
			output = output.concat(inputLine);
		}
		// System.out.println(inputLine);

		in.close();
		return output;
	}

}
