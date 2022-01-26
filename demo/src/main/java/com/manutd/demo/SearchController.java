package com.manutd.demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.manutd.demo.ShopByPlayerController.Dis;

//import com.manutd.demo.PlayersController.Dis;

@Controller
public class SearchController {
public class Dis{
	    /**
		 * 
		 */
		
		String src_arr;  
	    String alt_arr;
	    Integer pr_arr;  
	    Integer pr_coin_arr;
	public Dis(String src_arr, String alt_arr, Integer pr_arr, Integer pr_coin_arr) {
			super();
			this.src_arr = src_arr;
			this.alt_arr = alt_arr;
			this.pr_arr = pr_arr;
			this.pr_coin_arr = pr_coin_arr;
		}
		public String getSrc_arr() {
			return src_arr;
		}
		public void setSrc_arr(String src_arr) {
			this.src_arr = src_arr;
		}
		public String getAlt_arr() {
			return alt_arr;
		}
		public void setAlt_arr(String alt_arr) {
			this.alt_arr = alt_arr;
		}
		public Integer getPr_arr() {
			return pr_arr;
		}
		public void setPr_arr(Integer pr_arr) {
			this.pr_arr = pr_arr;
		}
		public Integer getPr_coin_arr() {
			return pr_coin_arr;
		}
		public void setPr_coin_arr(Integer pr_coin_arr) {
			this.pr_coin_arr = pr_coin_arr;
		}
	    
};



public void func(Model model,String tab_name, String tab_string, String req,List<Dis> list) {

	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
    String User="user";
    String Pass="user";
    try {
		Connection connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement pre=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet re=pre.executeQuery();
		while(re.next()) {
			System.out.println("/////////");
		 	String src_third=re.getString(tab_string+"_img_link");
			String alt_third=re.getString(tab_string+"_descrp");
			Integer pr_third=re.getInt("price");
			Integer pr_coin_third=re.getInt("price_coin");
		 	
			if(re.isAfterLast()) {
				break;
			}
			
			if(alt_third.toUpperCase().contains(req.toUpperCase())) {	
				System.out.println("/////////");
				System.out.println(alt_third);
				System.out.println();
				list.add(new Dis(src_third, alt_third, pr_third, pr_coin_third));
			}
			
		 }
		 
		
		 
		pre.close();
		re.close();

	
		
    }catch(SQLException w) {
		w.printStackTrace();
	}
}



public void func(Model model,String tab_name,String tab_key, String tab_string, String req,List<Dis> list) {

	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
    String User="user";
    String Pass="user";
    try {
		Connection connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement pre=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet re=pre.executeQuery();
		
		while(re.next()) {
			System.out.println("/////////");
		 	String src_third=re.getString(tab_key+"_img_link");
			String alt_third=re.getString(tab_string+"_descrp");
			Integer pr_third=re.getInt("price");
			Integer pr_coin_third=re.getInt("price_coin");
		 	
			if(re.isAfterLast()) {
				break;
			}
			
			if(alt_third.toUpperCase().contains(req.toUpperCase())) {	
				list.add(new Dis(src_third, alt_third, pr_third, pr_coin_third));
			}
		 }
		

		
		 
		pre.close();
		re.close();

		
		
    }catch(SQLException w) {
		w.printStackTrace();
	}
}




public void func_size_filters(Model model,HttpServletRequest request,List<Dis> list) throws UnsupportedEncodingException {
	String page=request.getHeader("Referer");
	String heq=URLDecoder.decode(page, "UTF-8");
	System.out.println(page);
	heq.replaceAll("http://localhost:8082/", "");
	System.out.println(heq.replaceAll("http://localhost:8082/", ""));
	System.out.println("Here WE are");
	System.out.println(heq.replaceAll("http://localhost:8082/", "").indexOf("_", 0));
	
	Integer decide_num=0;
	
	if(heq.contains("12")) {
		decide_num=12;
	}else if(heq.contains("24")) {
		decide_num=24;
	}else if(heq.contains("36")) {
		decide_num=36;
	}
	
	
	Integer defis=heq.replaceAll("http://localhost:8082/", "").indexOf("_", 0);
	
	 func(model,"keeper_kits","keeper","keeper_kit",heq.replaceAll("http://localhost:8082/", "").substring(0, defis),list);
	 func(model,"home_kits","home","home_kit",heq.replaceAll("http://localhost:8082/", "").substring(0, defis),list);
	 func(model,"away_kits","away","away_kit",heq.replaceAll("http://localhost:8082/", "").substring(0, defis),list);
	 func(model,"third_kits","third","third_kit",heq.replaceAll("http://localhost:8082/", "").substring(0, defis),list);
	 func(model,"man_headwear","man_headwear",heq.replaceAll("http://localhost:8082/", "").substring(0, defis),list);
	 func(model,"man_jackets","man_jackets",heq.replaceAll("http://localhost:8082/", "").substring(0, defis),list);
	 func(model,"women_tops","women_top",heq.replaceAll("http://localhost:8082/", "").substring(0, defis),list);
	 func(model,"women_headwear","women_headwear",heq.replaceAll("http://localhost:8082/", "").substring(0, defis),list);
	 func(model,"women_scarves","women_scarve",heq.replaceAll("http://localhost:8082/", "").substring(0, defis),list);
	 func(model,"kids_football_kits","kids_football_kit",heq.replaceAll("http://localhost:8082/", "").substring(0, defis),list);
	 func(model,"kids_jackets","kids_jacket",heq.replaceAll("http://localhost:8082/", "").substring(0, defis),list);
	 func(model,"kids_tracksuits","kids_tracksuit",heq.replaceAll("http://localhost:8082/", "").substring(0, defis),list);
	 
	
	
	if(heq.contains("_lp")) {
		 
		 int adad=0;
			
			if(list.isEmpty()) {
				System.out.println("Do nothing is empty!");
				
				System.out.println(list.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","Search_lp");
		     	model.addAttribute("high_price","Search_hp");
		     	model.addAttribute("new_item","Search_ni");
		     	model.addAttribute("size_one","Search_12");
		     	model.addAttribute("size_two","Search_24");
		     	model.addAttribute("size_three","Search_36");
		     	model.addAttribute("mass",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
		     	model.addAttribute("item",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
		     	model.addAttribute("url","search");
				
			}else if(list.size()<3){
				System.out.println(list.get(0).alt_arr);
				System.out.println("Do nothing!");
				
				System.out.println(list.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", list.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","Search_lp");
		     	model.addAttribute("high_price","Search_hp");
		     	model.addAttribute("new_item","Search_ni");
		     	model.addAttribute("size_one","Search_12");
		     	model.addAttribute("size_two","Search_24");
		     	model.addAttribute("size_three","Search_36");
		     	model.addAttribute("mass",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
		     	model.addAttribute("item",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
		     	model.addAttribute("url","search");
				
			}else {
				System.out.println(list.get(0).alt_arr);
				if(list.size()%3!=0) {
//					System.out.println("Has");
					adad=list.size()%3;
					System.out.println(adad);
//					System.out.println("an");
						for(int k=0;k<adad;k++) {
							System.out.println("!");
							list.remove(k);
							
						}
				}
				
				
				System.out.println(list.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", list.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//		     	model.addAttribute("width",pile.size());
		     	model.addAttribute("mult",list.size()-1);
		    	model.addAttribute("low_price","Search_lp");
		     	model.addAttribute("high_price","Search_hp");
		     	model.addAttribute("new_item","Search_ni");
		     	model.addAttribute("size_one","Search_12");
		     	model.addAttribute("size_two","Search_24");
		     	model.addAttribute("size_three","Search_36");
		     	model.addAttribute("mass",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
		     	model.addAttribute("item",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
		     	model.addAttribute("url","search");
		     	
			}
		 
		 
	}else if(heq.contains("_hp")) {
				
		int adad=0;
		
		if(list.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(list.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price","Search_lp");
	     	model.addAttribute("high_price","Search_hp");
	     	model.addAttribute("new_item","Search_ni");
	     	model.addAttribute("size_one","Search_12");
	     	model.addAttribute("size_two","Search_24");
	     	model.addAttribute("size_three","Search_36");
	     	model.addAttribute("mass",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("item",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("url","search");
			
		}else if(list.size()<3){
			System.out.println(list.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(list.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", list.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price","Search_lp");
	     	model.addAttribute("high_price","Search_hp");
	     	model.addAttribute("new_item","Search_ni");
	     	model.addAttribute("size_one","Search_12");
	     	model.addAttribute("size_two","Search_24");
	     	model.addAttribute("size_three","Search_36");
	     	model.addAttribute("mass",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("item",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("url","search");
			
		}else {
			System.out.println(list.get(0).alt_arr);
			if(list.size()%3!=0) {
//				System.out.println("Has");
				adad=list.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int k=0;k<adad;k++) {
						System.out.println("!");
						list.remove(k);
						
					}
			}
			
			
			System.out.println(list.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", list.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",list.size()-1);
	    	model.addAttribute("low_price","Search_lp");
	     	model.addAttribute("high_price","Search_hp");
	     	model.addAttribute("new_item","Search_ni");
	     	model.addAttribute("size_one","Search_12");
	     	model.addAttribute("size_two","Search_24");
	     	model.addAttribute("size_three","Search_36");
	     	model.addAttribute("mass",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("item",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("url","search");
	     	
		}
	}else if(heq.contains("_ni")) {
			int adad=0;
		
		if(list.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(list.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price","Search_lp");
	     	model.addAttribute("high_price","Search_hp");
	     	model.addAttribute("new_item","Search_ni");
	     	model.addAttribute("size_one","Search_12");
	     	model.addAttribute("size_two","Search_24");
	     	model.addAttribute("size_three","Search_36");
	     	model.addAttribute("mass",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("item",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("url","search");
			
		}else if(list.size()<3){
			System.out.println(list.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(list.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", list);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price","Search_lp");
	     	model.addAttribute("high_price","Search_hp");
	     	model.addAttribute("new_item","Search_ni");
	     	model.addAttribute("size_one","Search_12");
	     	model.addAttribute("size_two","Search_24");
	     	model.addAttribute("size_three","Search_36");
	     	model.addAttribute("mass",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("item",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("url","search");
			
		}else {
			System.out.println(list.get(0).alt_arr);
			if(list.size()%3!=0) {
//				System.out.println("Has");
				adad=list.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int k=0;k<adad;k++) {
						System.out.println("!");
						list.remove(k);
						
					}
			}
			
			
			System.out.println(list.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", list);
//	     	model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",list.size()-1);
	    	model.addAttribute("low_price","Search_lp");
	     	model.addAttribute("high_price","Search_hp");
	     	model.addAttribute("new_item","Search_ni");
	     	model.addAttribute("size_one","Search_12");
	     	model.addAttribute("size_two","Search_24");
	     	model.addAttribute("size_three","Search_36");
	     	model.addAttribute("mass",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("item",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("url","search");
	     	
		}
		
	}else if(heq.contains("_12") || heq.contains("_24") || heq.contains("_36")) {
		
int adad=0;
		
		if(list.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(list.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price","Search_lp");
	     	model.addAttribute("high_price","Search_hp");
	     	model.addAttribute("new_item","Search_ni");
	     	model.addAttribute("size_one","Search_12");
	     	model.addAttribute("size_two","Search_24");
	     	model.addAttribute("size_three","Search_36");
	     	model.addAttribute("mass",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("item",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("url","search");
			
		}else if(list.size()<3){
			System.out.println(list.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(list.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", list);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price","Search_lp");
	     	model.addAttribute("high_price","Search_hp");
	     	model.addAttribute("new_item","Search_ni");
	     	model.addAttribute("size_one","Search_12");
	     	model.addAttribute("size_two","Search_24");
	     	model.addAttribute("size_three","Search_36");
	     	model.addAttribute("mass",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("item",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("url","search");
			
		}else {
			System.out.println(list.get(0).alt_arr);
			if(list.size()%3!=0) {
//				System.out.println("Has");
				adad=list.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int k=0;k<adad;k++) {
						System.out.println("!");
						list.remove(k);
						
					}
			}
			
			
			System.out.println(list.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			if(list.size()>=decide_num) {
				System.out.println("list>decide_num");
				model.addAttribute("img_source", list.subList(0, decide_num));
				model.addAttribute("mult",list.subList(0, decide_num).size()-1);
			}else {
				System.out.println("list<decide_num");
				model.addAttribute("img_source", list);
				model.addAttribute("mult",list.size()-1);
			}
////	  	model.addAttribute("width",pile.size());
//	     	model.addAttribute("mult",list.size()-1);
	    	model.addAttribute("low_price","Search_lp");
	     	model.addAttribute("high_price","Search_hp");
	     	model.addAttribute("new_item","Search_ni");
	     	model.addAttribute("size_one","Search_12");
	     	model.addAttribute("size_two","Search_24");
	     	model.addAttribute("size_three","Search_36");
	     	model.addAttribute("mass",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("item",heq.replaceAll("http://localhost:8082/", "").substring(0, defis));
	     	model.addAttribute("url","search");
	     	
		}
		
	}
	
}

	
	
	
@GetMapping("/search")
public String search(Model model,HttpServletRequest request,HttpServletResponse res) throws IOException, SQLException {
		String req =request.getParameter("searchStr");
		System.out.println(req);
		List<Dis> list = new ArrayList<Dis>(); 
		model.addAttribute("title", "Manchester United Search");
		model.addAttribute("direct_name", "Manchester United");
	    String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	    String User="user";
	    String Pass="user";
	    
	    
	    ArrayList<String> pl_name =new ArrayList<String>();	
	  
	    
		System.out.println("Testing connection to Postgresql JDBC");
			
			try {
				Class.forName("org.postgresql.Driver");
			}catch(ClassNotFoundException e) {
				System.out.println("Postgresql not found");
				e.printStackTrace();
//				return "players";
			}
			
			boolean is_empty=false;
			System.out.println("PostgreSQL JDBC Driver successfully connected");
			Connection connection = null;
//			If the database is empty?
			try {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement slt=connection.prepareStatement("SELECT * FROM away_kits",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rst=slt.executeQuery();
				
					if (!rst.next()){
						is_empty=true;
//					Database is empty
						}
				connection.close();		
				slt.close();
				rst.close();
			}catch(SQLException w) {
				w.printStackTrace();
			}
			
			
		System.out.println(is_empty);
		List<String> tab_names =new ArrayList <String>(); 
		List<String> tab_string =new ArrayList <String>(); 
		
		
		if(is_empty!=true) {
			 connection = DriverManager.getConnection(DB_URL,User,Pass);
			 Statement prepare=connection.createStatement();
			 ResultSet result=prepare.executeQuery("SELECT table_name FROM information_schema.tables\r\n"
			 		+ "WHERE table_schema NOT IN ('information_schema', 'pg_catalog')\r\n"
			 		+ "AND table_schema IN('public', 'manchester');");
			
			 while(result.next()) {
				  System.out.print(result.getString(1));
				  System.out.println();
				  
				  if(result.getString(1).equals("category") || result.getString(1).equals("players")) {
					  System.out.println("Not necessary!");
				  }else {
					  tab_names.add(result.getString(1));
					  tab_string.add(result.getString(1).replaceAll("[s]\\b", ""));  
				  }
				  
					if(result.isAfterLast()) {
						break;
					}
				
					
				
				}
			 System.out.println();
			 System.out.println();
			 for(int i=0;i<tab_names.size();i++) {
				 System.out.println(tab_names.get(i));
			 }
			 ///////
			 System.out.println();
			 System.out.println();
		
			 for(int i=0;i<tab_string.size();i++) {
				 System.out.println(tab_string.get(i));
			 }
			
			 
			 PreparedStatement pre=connection.prepareStatement("SELECT * FROM players",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			 ResultSet re=pre.executeQuery();
			 while(re.next()) {
				 	String name_pl=re.getString("player_name");
				 	
				 	if(name_pl.toLowerCase().contains(req.toLowerCase())) {
				 		pl_name.add(name_pl);
				 	}
				 	
				 	
					if(re.isAfterLast()) {
						break;
					}
					
					
				 }
			 
			pre.close();
			re.close();
			
			 
			 
				 func(model,"keeper_kits","keeper","keeper_kit",req,list);
				 func(model,"home_kits","home","home_kit",req,list);
				 func(model,"away_kits","away","away_kit",req,list);
				 func(model,"third_kits","third","third_kit",req,list);
				 func(model,"man_headwear","man_headwear",req,list);
				 func(model,"man_jackets","man_jackets",req,list);
				 func(model,"women_tops","women_top",req,list);
				 func(model,"women_headwear","women_headwear",req,list);
				 func(model,"women_scarves","women_scarve",req,list);
				 func(model,"kids_football_kits","kids_football_kit",req,list);
				 func(model,"kids_jackets","kids_jacket",req,list);
				 func(model,"kids_tracksuits","kids_tracksuit",req,list);
				
//			 prepare.close();
//			 result.close();
//		model.addAttribute("img_source", list);


			connection.close();
		}else {
			System.out.println("The database is empty!");
		}
		
		
		int adad=0;
		
		if(list.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(list.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price","Search_lp");
	     	model.addAttribute("high_price","Search_hp");
	     	model.addAttribute("new_item","Search_ni");
	     	model.addAttribute("size_one","Search_12");
	     	model.addAttribute("size_two","Search_24");
	     	model.addAttribute("size_three","Search_36");
	     	model.addAttribute("mass",req.toUpperCase());
	     	model.addAttribute("item",req.toUpperCase());
	    	model.addAttribute("url","away_"+pl_name.get(0).toString());
			
		}else if(list.size()<3){
			System.out.println(list.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(list.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", list);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price","Search_lp");
	     	model.addAttribute("high_price","Search_hp");
	     	model.addAttribute("new_item","Search_ni");
	     	model.addAttribute("size_one","Search_12");
	     	model.addAttribute("size_two","Search_24");
	     	model.addAttribute("size_three","Search_36");
	     	model.addAttribute("mass",req.toUpperCase());
	     	model.addAttribute("item",req.toUpperCase());
	    	model.addAttribute("url","away_"+pl_name.get(0).toString());
			
		}else {
			System.out.println(list.get(0).alt_arr);
			if(list.size()%3!=0) {
//				System.out.println("Has");
				adad=list.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int k=0;k<adad;k++) {
						System.out.println("!");
						list.remove(k);
						
					}
			}
			
			
			System.out.println(list.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", list);
//	     	model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",list.size()-1);
	    	model.addAttribute("low_price","Search_lp");
	     	model.addAttribute("high_price","Search_hp");
	     	model.addAttribute("new_item","Search_ni");
	     	model.addAttribute("size_one","Search_12");
	     	model.addAttribute("size_two","Search_24");
	     	model.addAttribute("size_three","Search_36");
	     	model.addAttribute("mass",req.toUpperCase());
	     	model.addAttribute("item",req.toUpperCase());
	     	model.addAttribute("url","away_"+pl_name.get(0).toString());
	     	
		}
		
		
		
		
			
		if(list.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(list.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

		
  }

@GetMapping("/Search_lp")
public String Search_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> list = new ArrayList<Dis>(); 
	model.addAttribute("title", "Manchester United Search");
	model.addAttribute("direct_name", "Manchester United");
	func_size_filters(model,request,list);
	
	
	if(list.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(list.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "away";
	}

}


@GetMapping("/Search_hp")
public String Search_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> list = new ArrayList<Dis>(); 
	model.addAttribute("title", "Manchester United Search");
	model.addAttribute("direct_name", "Manchester United");
	func_size_filters(model,request,list);
	
	
	if(list.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(list.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "away";
	}

}



@GetMapping("/Search_ni")
public String Search_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> list = new ArrayList<Dis>(); 
	model.addAttribute("title", "Manchester United Search");
	model.addAttribute("direct_name", "Manchester United");
	func_size_filters(model,request,list);
	
	
	if(list.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(list.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "away";
	}

}
	

@GetMapping("/Search_12")
public String Search_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> list = new ArrayList<Dis>(); 
	model.addAttribute("title", "Manchester United Search");
	model.addAttribute("direct_name", "Manchester United");
	func_size_filters(model,request,list);
	
	
	if(list.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(list.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "away";
	}

}


@GetMapping("/Search_24")
public String Search_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> list = new ArrayList<Dis>(); 
	model.addAttribute("title", "Manchester United Search");
	model.addAttribute("direct_name", "Manchester United");
	func_size_filters(model,request,list);
	
	
	if(list.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(list.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "away";
	}

}

	
@GetMapping("/Search_36")
public String Search_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> list = new ArrayList<Dis>(); 
	model.addAttribute("title", "Manchester United Search");
	model.addAttribute("direct_name", "Manchester United");
	func_size_filters(model,request,list);
	
	
	if(list.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(list.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "away";
	}

}
	
	
}
