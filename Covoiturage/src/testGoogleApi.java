import java.util.Hashtable;

import google_api.GoogleGeoApi;


public class testGoogleApi {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String addressQuery = "Ocean atlantique";
		
		Hashtable<String, Double> resultCoord;
		String resultAddress;
		
		System.out.println("Recherche de l'addresse ".concat(addressQuery));
		
		resultCoord = GoogleGeoApi.getCoordOfAddress(addressQuery);
		if(resultCoord == null) {
			System.out.println("Pas de coordonnées");
		}else{
			System.out.println(new String("Coords : ").concat(resultCoord.toString()));
			
			resultAddress = GoogleGeoApi.getNearAddressFromCoord(resultCoord);
			if(resultAddress == null) {
				System.out.println("Pas d'addresse correspondante");
			}else{			
				System.out.println(new String("Address : ").concat(resultAddress));
			}
		}
		
	}

}
