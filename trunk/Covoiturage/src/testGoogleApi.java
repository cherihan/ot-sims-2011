import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import dao.DaoPosition;
import dao.DaoRoute;

import model.Position;
import model.Route;
import model.Route_type;

import utilities.DateUtils;
import utilities.ValidatorOfData;

import google_api.GoogleGeoApi;
import google_api.GoogleGeoApiCached;


public class testGoogleApi {

	/**
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static void main22(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String typeTest="";
		
		//typeTest="googleDirection";
		typeTest="createRoute";
		//typeTest="googleDirection";
		
		if(typeTest.equals("googleDirection")) {
			Route r1;
			String adr1;
			String adr2;
			String adr3;
			Position pos1;
			Position pos2;
			Position pos3;
			
			adr1 = "Lyon";
			adr2 = "Paris";
			adr3 = "Bron";
			Hashtable<String, Double> gresult;
			
			gresult = GoogleGeoApiCached.getCoordOfAddress(adr1);
			pos1 = DaoPosition.createPosition(adr1, gresult.get("latitude"), gresult.get("longitude"));
			
			gresult = GoogleGeoApiCached.getCoordOfAddress(adr2);
			pos2 = DaoPosition.createPosition(adr2, gresult.get("latitude"), gresult.get("longitude"));
			
			gresult = GoogleGeoApiCached.getCoordOfAddress(adr3);
			pos3 = DaoPosition.createPosition(adr3, gresult.get("latitude"), gresult.get("longitude"));
			
			System.out.println("Debut a "+pos1.getAddress());
			ArrayList<Hashtable<String, Object>> result = GoogleGeoApi.getDirection(pos1.getCoords(), pos2.getCoords(), "", new Hashtable<Integer, Hashtable<Integer,Double>>());
			
			/*
		    Enumeration<Hashtable<String, Double>> en = result.elements();
			while(en.hasMoreElements()) {
				*/
			//	while(itKey.hasNext()) {
			for(int i=0;i < result.size();i++) {
					//Character value = (Character)itValue.next();
					Integer key = i;
	
				Hashtable<String, Object> step = (Hashtable<String, Object>) result.get(i);
				
				String addressStart = GoogleGeoApiCached.getNearAddressFromCoord((Hashtable<String, Double>) step.get("begin"));
				String addressEnd = GoogleGeoApiCached.getNearAddressFromCoord((Hashtable<String, Double>) step.get("end"));
				Double duration = (Double) step.get("duration");
				
				ArrayList<Hashtable<String, Object>> segments= (ArrayList<Hashtable<String, Object>>) step.get("segments");
				
				System.out.println("Step : ");
				System.out.println("	start :  "+addressStart);
				System.out.println("	end :  "+addressEnd);
				System.out.println("	duree :  "+duration);
				System.out.println("	etape:  "+key);
				System.out.println("	SUB Step : "+segments.size());
				for(int i2=0;i2 < segments.size();i2++) {
					String subaddressStart = GoogleGeoApiCached.getNearAddressFromCoord((Hashtable<String, Double>) segments.get(i2).get("begin"));
					String subaddressEnd = GoogleGeoApiCached.getNearAddressFromCoord((Hashtable<String, Double>) segments.get(i2).get("end"));
					Double subduration = (Double) segments.get(i2).get("duration");
					System.out.println("	SUB Step : ");
					System.out.println("		start :  "+subaddressStart+" - "+((Hashtable<String, Double>) segments.get(i2).get("begin")).get("latitude")+" , "+((Hashtable<String, Double>) segments.get(i2).get("begin")).get("longitude"));
					System.out.println("		end :  "+subaddressEnd);
					System.out.println("		duree :  "+subduration);
				}
				
			}
			
			System.out.println("Fin");
		}
		
		if(typeTest.equals("createRoute")) {
			String adr1;
			String adr2;
			String adr3;
			String adr4;
			Route r1;
			
			Position pos1;
			Position pos2;
			Position pos3;
			Position pos4;
			
			adr1="Lyon";
			adr2="Paris";
			adr3="Bron";
			adr4="Dijon";
			
			pos1 = DaoPosition.getPositionByAddress(adr1);
			pos2 = DaoPosition.getPositionByAddress(adr2);
			pos3 = DaoPosition.getPositionByAddress(adr3);
			pos4 = DaoPosition.getPositionByAddress(adr4);
			
			System.out.println(DateUtils.getDateAsInteger(new Date(199999)));
			System.out.println(DateUtils.getDateAsInteger(new Date()));
			
			r1 = DaoRoute.createRoute(Route_type.PROVIDE_CAR, adr1,
					adr2, new Date(), null, "comment",
					1, 3, (Integer) 0);
			System.out.println(r1);
			
			Hashtable<Integer, Route> result = DaoRoute.route_search(pos1, pos2, new Date(0), new Date(), 100, 0);
			testGoogleApi.displayHash(result);
			
			result = DaoRoute.route_search(pos3, pos2, new Date(0), new Date(), 10000, 0);
			result = DaoRoute.route_search(pos4, pos2, new Date(0), new Date(), 20000, 0);
			
			//result = DaoRoute.route_search_of_owner(1, new Date(), new Date(0), 0);
			
			testGoogleApi.displayHash(result);
			
			System.out.println("fin");
			if(true) {
				return ;
			}
			
			/*
			Hashtable<String, Double> resultCoord;
			String addressQuery = "Lyon";
			String resultAddress;
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
			*/
		}
		
	}
	
	public static void displayHash(Hashtable<Integer, Route> hash) {
		System.out.println("Affichage des resultats");
		Enumeration<Route> en = hash.elements();
		while(en.hasMoreElements()) {
			Route rte = en.nextElement();
			System.out.println("Route : ");
			System.out.println("	Owner : "+rte.getOwner());
			System.out.println("	Depart : "+rte.getPosition_beginObj().getAddress());
			System.out.println("	Arrive : "+rte.getPosition_endObj().getAddress());
		}
	}

}
