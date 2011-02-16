package google_api;

import java.util.Hashtable;

public class GoogleGeoApiCached {
	
	public static Hashtable<String, Double> getCoordOfAddress(String address) {
		return GoogleGeoApi.getCoordOfAddress(address);
	}

	public static String getNearAddressFromCoord(Hashtable<String, Double> coords){
			return GoogleGeoApi.getNearAddressFromCoord(coords);
	}
}
