package google_api;

import java.util.Hashtable;

import dao.DaoGoogleGeo;

import model.Google_cache;

public class GoogleGeoApiCached {

	public static Hashtable<String, Double> getCoordOfAddress(String address) {

		Hashtable<String, Double> result = null;

		Google_cache gch = DaoGoogleGeo.getByAddress(address);
		if (gch != null) {
			return gch.getCoordsAsHash();
		}

		result = GoogleGeoApi.getCoordOfAddress(address);
		if (result != null) {
			DaoGoogleGeo.createOrUpdateGoogleGeo(address, result);
		}
		return result;
	}

	public static String getNearAddressFromCoord(
			Hashtable<String, Double> coords) {

		String result = null;

		Google_cache gch = DaoGoogleGeo.getByCoords(coords);
		if (gch != null) {
			return gch.getAddress();
		}

		result = GoogleGeoApi.getNearAddressFromCoord(coords);
		if (result != null) {
			DaoGoogleGeo.createOrUpdateGoogleGeo(result, coords);
		}
		return result;
	}
}
