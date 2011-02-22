package google_api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
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
			// e.printStackTrace();
		}

		return null;
	}
	
	public static String getNearAddressFromCoord(
			Hashtable<String, Double> coords) {
		return GoogleGeoApi.getNearAddressFromCoord(coords, "");
	
	}
	
	public static String getNearAddressFromCoord(
			Hashtable<String, Double> coords, String prefix) {
		try {
			String uri = new String(
					"http://maps.googleapis.com/maps/api/geocode/json?sensor=false");
			uri = uri.concat("&latlng=").concat(
					URLEncoder.encode(coords.get(prefix+"latitude").toString(),
							"UTF-8"));
			uri = uri.concat(",").concat(
					URLEncoder.encode(coords.get(prefix+"longitude").toString(),
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
			// e.printStackTrace();
		}

		return null;
	}

	public static ArrayList<Hashtable<String, Double>> getDirection(Hashtable<String, Double> origin,
			Hashtable<String, Double> destination, String mode,
			Hashtable<Integer, Hashtable<Integer, Double>> waypoints) {
		
		ArrayList<Hashtable<String, Double>> result = new ArrayList<Hashtable<String, Double>>();
		int resultCounter=0;
		
		if ((mode == null || (!(mode.equals("driving")
				|| mode.equals("walking") || mode.equals("bicycling"))))) {
			mode = "driving";
		}
		if (waypoints == null) {
			waypoints = new Hashtable<Integer, Hashtable<Integer, Double>>();
		}
		try {
			String uri = new String(
					"http://maps.googleapis.com/maps/api/directions/json?sensor=false");
			uri = uri.concat("&origin=").concat(
					URLEncoder.encode(origin.get("latitude").toString(),
							"UTF-8"));
			uri = uri.concat(",").concat(
					URLEncoder.encode(origin.get("longitude").toString(),
							"UTF-8"));
			uri = uri.concat("&destination=").concat(
					URLEncoder.encode(destination.get("latitude").toString(),
							"UTF-8"));
			uri = uri.concat(",").concat(
					URLEncoder.encode(destination.get("longitude").toString(),
							"UTF-8"));
			uri = uri.concat("&mode=").concat(mode);
			uri = uri.concat("&units=metric").concat(mode);

			uri = uri.concat("&language=fr&region=fr");
			if (waypoints.size() >= 1) {
				Boolean isFirst = true;
				uri = uri.concat("&waypoints=");
				Enumeration<Hashtable<Integer, Double>> en = waypoints
						.elements();
				while (en.hasMoreElements()) {
					Hashtable<Integer, Double> pos = en.nextElement();
					if (!isFirst) {
						uri = uri.concat("|");
					} else {
						isFirst = false;
					}
					uri = uri.concat(pos.get("latitude").toString())
							.concat(",")
							.concat(pos.get("longitude").toString());
				}
			}
			System.out.println(uri);
			System.out.println("Resultat : ");
			String responseText = GoogleGeoApi.execHttpRequest(uri);
			System.out.println("Resultat : ");
			System.out.println(responseText);

			JSONObject responseJson = new JSONObject(responseText);

			if (!responseJson.getString("status").equals("OK")) {
				throw new Exception();
			}

			JSONArray results = responseJson.getJSONArray("routes");

			if (results.length() == 0) {
				throw new Exception();
			}
			JSONObject route = (JSONObject) results.get(0);
			JSONArray legs = route.getJSONArray("legs");
			int size_leg = legs.length();
			for(int i=0;i< size_leg;i++) {
				JSONObject jobj = (JSONObject) legs.get(i);
				JSONArray steps = jobj.getJSONArray("steps");
				
				int size_step = steps.length();
				for(int i2=0;i2< size_step;i2++) {
					JSONObject step = steps.getJSONObject(i2);
					
					JSONObject start = step.getJSONObject("start_location");
					JSONObject end = step.getJSONObject("end_location");
					
					Integer duration = step.getJSONObject("duration").getInt("value");
					
					Hashtable<String, Double> step_start = new Hashtable<String, Double>();
					step_start.put("latitude", start.getDouble("lat"));
					step_start.put("longitude", start.getDouble("lng"));
					
					Hashtable<String, Double> step_end = new Hashtable<String, Double>();
					step_end.put("latitude", end.getDouble("lat"));
					step_end.put("longitude", end.getDouble("lng"));
					
					Hashtable<String, Double> stepRes = new Hashtable<String, Double>();
					
					stepRes.put("start_latitude", step_start.get("latitude"));
					stepRes.put("start_longitude", step_start.get("longitude"));
					
					stepRes.put("end_latitude", step_end.get("latitude"));
					stepRes.put("end_longitude", step_end.get("longitude"));
					
					stepRes.put("duration", Double.valueOf(duration));
					//System.out.println("passage par "+step_end.get("longitude"));
					result.add(stepRes);
					resultCounter++;
				}
			}
			
			return result;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	protected static String execHttpRequest(String url) throws Exception {

		URL yahoo = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yahoo.openStream()));

		String inputLine;
		String output = new String();
		System.out.println("teest");
		while ((inputLine = in.readLine()) != null) {
			output = output.concat(inputLine);
		}
		// System.out.println(inputLine);

		in.close();
		return output;
	}

}
