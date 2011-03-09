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

	/**
	 * @param int Définit la distance minimale entre 2 points de la polyline
	 *        décrivant le parcours. AInsi, si dans une curbe, les points sont trop rapprochés, ils seront supprimés pur qu'ils soient au plus prooche, distant de X metres
	 */
	private static final int POLYLINE_INTERVAL_SIZE_MIN = 6000;
	
	
	/**
	 * @param int Définit la distance maximale entre 2 points de la polyline
	 *        décrivant le parcours
	 */
	private static final int POLYLINE_INTERVAL_SIZE_MAX = 8000;
	

	public static final double LATITUDE_CONVERS = 0.000009;
	public static final double LONGITUDE_CONVERS = 0.000014;

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
					URLEncoder.encode(coords.get(prefix + "latitude")
							.toString(), "UTF-8"));
			uri = uri.concat(",").concat(
					URLEncoder.encode(coords.get(prefix + "longitude")
							.toString(), "UTF-8"));
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

	public static ArrayList<Hashtable<String, Object>> getDirection(
			Hashtable<String, Double> origin,
			Hashtable<String, Double> destination, String mode,
			Hashtable<Integer, Hashtable<Integer, Double>> waypoints) {

		ArrayList<Hashtable<String, Object>> result = new ArrayList<Hashtable<String, Object>>();
		int resultCounter = 0;

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
			String responseText = GoogleGeoApi.execHttpRequest(uri);

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
			for (int i = 0; i < size_leg; i++) {
				JSONObject jobj = (JSONObject) legs.get(i);
				JSONArray steps = jobj.getJSONArray("steps");

				int size_step = steps.length();
				for (int i2 = 0; i2 < size_step; i2++) {
					JSONObject step = steps.getJSONObject(i2);

					JSONObject start = step.getJSONObject("start_location");
					JSONObject end = step.getJSONObject("end_location");

					Integer duration = step.getJSONObject("duration").getInt(
							"value");

					Hashtable<String, Double> step_start = new Hashtable<String, Double>();
					step_start.put("latitude", start.getDouble("lat"));
					step_start.put("longitude", start.getDouble("lng"));

					Hashtable<String, Double> step_end = new Hashtable<String, Double>();
					step_end.put("latitude", end.getDouble("lat"));
					step_end.put("longitude", end.getDouble("lng"));

					String polyString = step.getJSONObject("polyline")
							.getString("points");
					ArrayList<Hashtable<String, Double>> polyHash = GoogleGeoApi
							.decodePoly(polyString);

					ArrayList<Hashtable<String, Double>> polyHashNonPrecis = new ArrayList<Hashtable<String, Double>>();
					polyHashNonPrecis = GoogleGeoApi.polylineDelSubpoints(
							polyHash, GoogleGeoApi.POLYLINE_INTERVAL_SIZE_MIN);

					ArrayList<Hashtable<String, Double>> polyHashPrecis = new ArrayList<Hashtable<String, Double>>();
					polyHashPrecis = GoogleGeoApi.polylineAddSubpoints(
							polyHashNonPrecis, GoogleGeoApi.POLYLINE_INTERVAL_SIZE_MAX);
					
					
					ArrayList<Hashtable<String, Object>> segments = new ArrayList<Hashtable<String, Object>>();
					segments = GoogleGeoApi.constructSegments(polyHashPrecis, duration);

					Hashtable<String, Object> stepRes = new Hashtable<String, Object>();
					
					stepRes.put("begin", step_start);

					stepRes.put("end", step_end);

					stepRes.put("duration", Double.valueOf(duration));
/*
					stepRes.put("points", polyHash);
					stepRes.put("pointsPrecision", polyHashPrecis);
					*/
					stepRes.put("segments", segments);

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
		while ((inputLine = in.readLine()) != null) {
			output = output.concat(inputLine);
		}

		in.close();
		return output;
	}

	protected static ArrayList<Hashtable<String, Double>> decodePoly(
			String encoded) {

		ArrayList<Hashtable<String, Double>> poly = new ArrayList<Hashtable<String, Double>>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			Hashtable<String, Double> p = new Hashtable<String, Double>();
			p.put("latitude", (Double) (((double) lat / 1E5)));
			p.put("longitude", (Double) (((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

	private static ArrayList<Hashtable<String, Double>> polylineAddSubpoints(
			ArrayList<Hashtable<String, Double>> polyHash,
			int polylineIntervalSize) {
		ArrayList<Hashtable<String, Double>> retour = new ArrayList<Hashtable<String, Double>>();

		int polyHashSize = polyHash.size();
		int polyHashI = 0;
		if (polyHashSize >= 1) {
			retour.add(polyHash.get(0));
			do {
				Double ptStartLat = polyHash.get(polyHashI).get("latitude");
				Double ptStartLng = polyHash.get(polyHashI).get("longitude");

				Double ptEndLat = polyHash.get(polyHashI + 1).get("latitude");
				Double ptEndLng = polyHash.get(polyHashI + 1).get("longitude");

				Double x = ((ptEndLat - ptStartLat) / GoogleGeoApi.LATITUDE_CONVERS);
				Double y = ((ptEndLng - ptStartLng) / GoogleGeoApi.LONGITUDE_CONVERS);

				Double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

				if (distance > polylineIntervalSize) {

					Double deltaLat = ptEndLat - ptStartLat;
					Double deltaLng = ptEndLng - ptStartLng;

					int nbAdditionalPoints = (int) (Math.floor(distance
							/ polylineIntervalSize) - 1);
					for (int addI = 1; addI <= nbAdditionalPoints; addI++) {
						Double ptLat = ptStartLat
								+ ((deltaLat / (nbAdditionalPoints + 1)) * addI);
						Double ptLng = ptStartLng
								+ ((deltaLng / (nbAdditionalPoints + 1)) * addI);

						Hashtable<String, Double> newPoint = new Hashtable<String, Double>();
						newPoint.put("latitude", ptLat);
						newPoint.put("longitude", ptLng);
						retour.add(newPoint);
					}
				}

				retour.add(polyHash.get(polyHashI + 1));
				polyHashI++;
			} while (polyHashI < polyHashSize - 1);
		}

		return retour;
	}

	private static ArrayList<Hashtable<String, Double>> polylineDelSubpoints(
			ArrayList<Hashtable<String, Double>> polyHash,
			int polylineIntervalSizeMin) {
		//return polyHash;
		Boolean updated = false;
		
		ArrayList<Hashtable<String, Double>> retour = new ArrayList<Hashtable<String, Double>>();

		int polyHashSize = polyHash.size();
		int polyHashI = 0;
		if(polyHashSize >= 1) {
			//on ajoute le premier point dans tous les cas
			retour.add(polyHash.get(0));
		}
		if (polyHashSize >= 3) {
			do {
				Double ptStartLat = polyHash.get(polyHashI).get("latitude");
				Double ptStartLng = polyHash.get(polyHashI).get("longitude");

				Double ptMiddleLat = polyHash.get(polyHashI + 1).get("latitude");
				Double ptMiddleLng = polyHash.get(polyHashI + 1).get("longitude");
				
				Double ptEndLat = polyHash.get(polyHashI + 2).get("latitude");
				Double ptEndLng = polyHash.get(polyHashI + 2).get("longitude");

				Double seg1X = ((ptMiddleLat - ptStartLat) / GoogleGeoApi.LATITUDE_CONVERS);
				Double seg1Y = ((ptMiddleLng - ptStartLng) / GoogleGeoApi.LONGITUDE_CONVERS);

				Double seg2X = ((ptEndLat - ptMiddleLat) / GoogleGeoApi.LATITUDE_CONVERS);
				Double seg2Y = ((ptEndLng - ptMiddleLng) / GoogleGeoApi.LONGITUDE_CONVERS);

				Double seg1Distance = Math.sqrt(Math.pow(seg1X, 2) + Math.pow(seg1Y, 2));
				Double seg2Distance = Math.sqrt(Math.pow(seg2X, 2) + Math.pow(seg2Y, 2));

				if ((seg1Distance+seg2Distance) < polylineIntervalSizeMin) {
					retour.add(polyHash.get(polyHashI + 2));
					polyHashI++;
					updated=true;
				}else{
					retour.add(polyHash.get(polyHashI + 1));
				}
				polyHashI++;
			} while (polyHashI < polyHashSize - 2);
		}
		if(polyHashSize >= 2) {
			//on ajoute le dernier point dans tous les cas
			retour.add(polyHash.get(polyHashSize-1));
		}
		
		if(retour.size() != polyHashSize) {
			updated=true;
		}else{
			updated=false;
		}
		
		if(updated) {
			return GoogleGeoApi.polylineDelSubpoints(retour, polylineIntervalSizeMin);
		}else{
			return retour;
		}
		
	}
	
	private static ArrayList<Hashtable<String, Object>> constructSegments(
			ArrayList<Hashtable<String, Double>> points,
			Integer globalDuration) {
		ArrayList<Hashtable<String, Object>> retour = new ArrayList<Hashtable<String, Object>>();
		int hashSize=points.size();
		if( hashSize >= 2) {
			Double distance=(double) 0;
			for(int hashI=0 ; hashI < hashSize-1 ; hashI++) {
				Double pt1Lat = points.get(hashI).get("latitude");
				Double pt1Lng = points.get(hashI).get("longitude");
				
				Double pt2Lat = points.get(hashI+1).get("latitude");
				Double pt2Lng = points.get(hashI+1).get("longitude");
				
				Double d = Math.sqrt( Math.pow(pt2Lat - pt1Lat, 2) + Math.pow(pt2Lng - pt1Lng, 2));
				distance+=d;
			}
			
			for(int hashI=0 ; hashI < hashSize-1 ; hashI++) {
				Hashtable<String, Object> segment = new Hashtable<String, Object>();
				
				
				Double pt1Lat = points.get(hashI).get("latitude");
				Double pt1Lng = points.get(hashI).get("longitude");
				
				Double pt2Lat = points.get(hashI+1).get("latitude");
				Double pt2Lng = points.get(hashI+1).get("longitude");
				
				Double d = Math.sqrt( Math.pow(pt2Lat - pt1Lat, 2) + Math.pow(pt2Lng - pt1Lng, 2));
				
				Double proportion = d / distance;
				
				
				
				segment.put("begin", points.get(hashI));
				segment.put("end", points.get(hashI+1));
				segment.put("duration", Double.valueOf(Math.round(globalDuration * proportion)));
				
				retour.add(segment);
			}
		}
		return retour;
	}

}
