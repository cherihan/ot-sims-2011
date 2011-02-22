import java.util.Hashtable;

import utilities.ValidatorOfData;

import google_api.GoogleGeoApi;


public class testGoogleApi {

	/**
	 * @param args
	 */
	public static void main22(String[] args) {
		// TODO Auto-generated method stub
		String phone = "0630874476";
		System.out.println("Validate phone "+phone+""+ValidatorOfData.validatePhone(phone));
		
		String mail = "pkh-sdf_sdf+LKJo@pJGH.sdf_dso.fr";
		System.out.println("Validate mail "+mail+""+ValidatorOfData.validateEMail(mail));
		
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
