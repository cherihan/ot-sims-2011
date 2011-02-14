package beans;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import dao.TraitementSQL;


public class ListofAnnonces {
	
	public ListofAnnonces() {
		// TODO Auto-generated constructor stub
		
		annonces =  new ArrayList<Annonce>();
		
	}
	
		public String output;		
		
		ArrayList<Annonce> annonces;
		

		
		@PostConstruct
		public void init() {			
		
//			annonces.add(new Annonce("La Doua Gaston Berger","Part Dieu","12/02/2011"));			
//			annonces.add(new Annonce("Charpenne","Massena","12/03/2011"));
//			annonces.add(new Annonce("Gare de Vaise","Perache","15/03/2011"));				
//			annonces.add(new Annonce("IUT Feyssin","Tete d'or","17/03/2011"));
			
			annonces = TraitementSQL.loadData();
								
		}
		
		
		public ArrayList<Annonce> getAnnonces() {
					
			return annonces;
		}
		


		
	}

