package com.manutd.demo;


import org.springframework.boot.autoconfigure.cassandra.CassandraProperties.Request;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.manutd.demo.KorzinaController.Dis;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
//import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Controller


public class WomenController {



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
	
	public void Parser(String DB_URL,String User,String Pass,Elements price,Elements price_coin,Elements a,Elements img,Integer count) throws IOException {
		   
	       //Matching price with the image sizes
		   if(price.size()>img.size()) {
			  int def =price.size()-img.size();
			  for(int i=0;i<def;i++) {
			  price.remove(price.size()-1-i);
			  }
	       }
		   //If prices too many and they don't match with image sizes we're removing them
		   //The same procedure here! But we works with price_coin
		   if(price_coin.size()>img.size()) {
				  int def =price_coin.size()-img.size();
				  for(int i=0;i<def;i++) {
				  price_coin.remove(price_coin.size()-1-i);
				  }
		       }
//	       We need to count the definite kit
	    
	       // Here we're counting the Maguire's kits   
	       for(int i=0; i<img.size(); i++) {
	    	   if(img.get(i).attr("alt").contains("Maguire")==true) {
	    	   System.out.println(img.get(i).attr("alt"));
	    	   count++;
	    	   }
	       }
	       
	}
	
	
	public boolean db_empty(Connection connection,boolean is_empty,String DB_URL,String User,String Pass,Elements price,Elements price_coin,Elements a,Elements img,String tab_name) {
		
		try {
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement slt=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rst=slt.executeQuery();
			
				if (!rst.next()){
					is_empty=true;
//				Database is empty
					}
					
			slt.close();
			rst.close();
		}catch(SQLException w) {
			w.printStackTrace();
		}
		
		return is_empty;
	}
	
	public void fill_db(Connection connection,boolean is_empty,String DB_URL,String User,String Pass,Elements price,Elements price_coin,Elements a,Elements img,String tab_name,String tab_sub_name) {
		
		System.out.println(price.size());
		
		System.out.println(db_empty(connection, is_empty, DB_URL, User, Pass,price,price_coin,a,img,tab_name));
			if(db_empty(connection, is_empty, DB_URL, User, Pass,price,price_coin,a,img,tab_name)==true) {
				try {
					connection = DriverManager.getConnection(DB_URL,User,Pass);
					PreparedStatement statement = connection.prepareStatement("INSERT INTO "+tab_name+"("+tab_sub_name+"_img_link,price,price_coin,"+tab_sub_name+"_descrp,"+tab_sub_name+"_id) VALUES((?),(?),(?),(?),(?))");
	
		//			PreparedStatement st=connection.prepareStatement("INSERT INTO away_kits(player_id) VALUES((?))");
					
					
					for(int i=0 ; i<img.size();i++) {
						statement.setString(1, img.get(i).attr("src"));
						statement.setInt(2, Integer.parseInt(price.get(i).text()));
						statement.setInt(3, Integer.parseInt(price_coin.get(i).text()));
						statement.setString(4, img.get(i).attr("alt"));
						statement.setInt(5, i+1);
					
					
						
						statement.executeUpdate();
						
					}
					statement.close();
				
		//			st.close();
					connection.close();
					System.out.println("connection closed!");
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}else {
				System.out.println("Database filled in!");
			}
		
		
	}
	
	
	
	public void info_out(Connection connection,String DB_URL,String User,String Pass,List<Dis> pile,Model model,String tab_name, String tab_sub_name) throws SQLException {
		
		 connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result=prepare.executeQuery();
			
			while(result.next()) {
				String src=result.getString(tab_sub_name+"_img_link");
				String alt=result.getString(tab_sub_name+"_descrp");
				Integer pr=result.getInt("price");
				Integer pr_coin=result.getInt("price_coin");
				
				
				pile.add(new Dis(src, alt, pr, pr_coin));
				
		
			}
			
			System.out.println(pile.get(0).alt_arr);
			
			System.out.println(pile.size());

	     	int adad=0;
			
			if(pile.isEmpty()) {
				System.out.println("Do nothing is empty!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
				
			}else if(pile.size()<3){
				System.out.println(pile.get(0).alt_arr);
				System.out.println("Do nothing!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
				
			}else {
				System.out.println(pile.get(0).alt_arr);
				if(pile.size()%3!=0) {
//					System.out.println("Has");
					adad=pile.size()%3;
					System.out.println(adad);
//					System.out.println("an");
						for(int i=0;i<adad;i++) {
							System.out.println("!");
							pile.remove(i);
							
						}
				}
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				
				model.addAttribute("img_source", pile);
//			     		model.addAttribute("width",pile.size());
		     	model.addAttribute("mult",pile.size()-1);
		     	model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
			}
			
			connection.close();
			prepare.close();
			result.close();
			
			
			
	}
	
	
	public void info_out_filter_size(Connection connection,String DB_URL,String User,String Pass,List<Dis> pile,Model model,String tab_name, String tab_sub_name,String key,HttpServletRequest request, Integer num) throws SQLException {
		String page=request.getHeader("Referer");
		System.out.println(request.getHeader("Referer"));
//		
		if(page.contains("_lp")) {
			
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE "+key+" <"+num,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result=prepare.executeQuery();
			
			while(result.next()) {
				String src=result.getString(tab_sub_name+"_img_link");
				String alt=result.getString(tab_sub_name+"_descrp");
				Integer pr=result.getInt("price");
				Integer pr_coin=result.getInt("price_coin");
				
				
				pile.add(new Dis(src, alt, pr, pr_coin));
				
		
			}
			
			System.out.println(pile.get(0).alt_arr);
			
			System.out.println(pile.size());

	     	int adad=0;
			
			if(pile.isEmpty()) {
				System.out.println("Do nothing is empty!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
				
			}else if(pile.size()<3){
				System.out.println(pile.get(0).alt_arr);
				System.out.println("Do nothing!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
				
			}else {
				System.out.println(pile.get(0).alt_arr);
				if(pile.size()%3!=0) {
//					System.out.println("Has");
					adad=pile.size()%3;
					System.out.println(adad);
//					System.out.println("an");
						for(int i=0;i<adad;i++) {
							System.out.println("!");
							pile.remove(i);
							
						}
				}
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				
				model.addAttribute("img_source", pile);
//			     		model.addAttribute("width",pile.size());
		     	model.addAttribute("mult",pile.size()-1);
		     	model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
			}
			
			connection.close();
			prepare.close();
			result.close();
	
			
		}else if(page.contains("_hp")) {
			
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE "+key+" <"+num,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result=prepare.executeQuery();
			
			while(result.next()) {
				String src=result.getString(tab_sub_name+"_img_link");
				String alt=result.getString(tab_sub_name+"_descrp");
				Integer pr=result.getInt("price");
				Integer pr_coin=result.getInt("price_coin");
				
				
				pile.add(new Dis(src, alt, pr, pr_coin));
				
		
			}
			
			System.out.println(pile.get(0).alt_arr);
			
			System.out.println(pile.size());

	     	int adad=0;
			
			if(pile.isEmpty()) {
				System.out.println("Do nothing is empty!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
				
			}else if(pile.size()<3){
				System.out.println(pile.get(0).alt_arr);
				System.out.println("Do nothing!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
				
			}else {
				System.out.println(pile.get(0).alt_arr);
				if(pile.size()%3!=0) {
//					System.out.println("Has");
					adad=pile.size()%3;
					System.out.println(adad);
//					System.out.println("an");
						for(int i=0;i<adad;i++) {
							System.out.println("!");
							pile.remove(i);
							
						}
				}
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				
				model.addAttribute("img_source", pile);
//			     		model.addAttribute("width",pile.size());
		     	model.addAttribute("mult",pile.size()-1);
		     	model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
			}
			
			connection.close();
			prepare.close();
			result.close();
	
			
		}else if(page.contains("_ni")) {
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE "+key+" <"+num,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result=prepare.executeQuery();
			
			while(result.next()) {
				String src=result.getString(tab_sub_name+"_img_link");
				String alt=result.getString(tab_sub_name+"_descrp");
				Integer pr=result.getInt("price");
				Integer pr_coin=result.getInt("price_coin");
				
				
				pile.add(new Dis(src, alt, pr, pr_coin));
				
		
			}
			
			System.out.println(pile.get(0).alt_arr);
			
			System.out.println(pile.size());
//		
	     	int adad=0;
			
			if(pile.isEmpty()) {
				System.out.println("Do nothing is empty!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
				
			}else if(pile.size()<3){
				System.out.println(pile.get(0).alt_arr);
				System.out.println("Do nothing!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
				
			}else {
				System.out.println(pile.get(0).alt_arr);
				if(pile.size()%3!=0) {
//					System.out.println("Has");
					adad=pile.size()%3;
					System.out.println(adad);
//					System.out.println("an");
						for(int i=0;i<adad;i++) {
							System.out.println("!");
							pile.remove(i);
							
						}
				}
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				
				model.addAttribute("img_source", pile);
//			     		model.addAttribute("width",pile.size());
		     	model.addAttribute("mult",pile.size()-1);
		     	model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
			}
			
			connection.close();
			prepare.close();
			result.close();
			
		}else {
			
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE "+key+" <"+num,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result=prepare.executeQuery();
			
			while(result.next()) {
				String src=result.getString(tab_sub_name+"_img_link");
				String alt=result.getString(tab_sub_name+"_descrp");
				Integer pr=result.getInt("price");
				Integer pr_coin=result.getInt("price_coin");
				
				
				pile.add(new Dis(src, alt, pr, pr_coin));
				
		
			}
			
			System.out.println(pile.get(0).alt_arr);
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
	     	
	     	int adad=0;
			
			if(pile.isEmpty()) {
				System.out.println("Do nothing is empty!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
				
			}else if(pile.size()<3){
				System.out.println(pile.get(0).alt_arr);
				System.out.println("Do nothing!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
				
			}else {
				System.out.println(pile.get(0).alt_arr);
				if(pile.size()%3!=0) {
//					System.out.println("Has");
					adad=pile.size()%3;
					System.out.println(adad);
//					System.out.println("an");
						for(int i=0;i<adad;i++) {
							System.out.println("!");
							pile.remove(i);
							
						}
				}
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				
				model.addAttribute("img_source", pile);
//			     		model.addAttribute("width",pile.size());
		     	model.addAttribute("mult",pile.size()-1);
		     	model.addAttribute("low_price",tab_name+"_lp");
		     	model.addAttribute("high_price",tab_name+"_hp");
		     	model.addAttribute("new_item",tab_name+"_ni");
		     	model.addAttribute("size_one",tab_name+"_12");
		     	model.addAttribute("size_two",tab_name+"_24");
		     	model.addAttribute("size_three",tab_name+"_36");
		     	model.addAttribute("mass",tab_name);
		      	model.addAttribute("item",tab_name);
		     	model.addAttribute("url","_"+tab_name);
			}
			
			connection.close();
			prepare.close();
			result.close();
			
		}
			
		
		
	}
	
	

	
@GetMapping("/women_tops")
public String women_tops(Model model) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", " Manchester United Ladies Tops, Man Utd Ladies Shirt");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN TOPS");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Integer count=0;
//		ArrayList<Elements> price =new ArrayList<Elements>();
//		ArrayList<Elements> price_coin =new ArrayList<Elements>();
//		ArrayList<Elements> a =new ArrayList<Elements>();
//		ArrayList<Elements> img =new ArrayList<Elements>();
		
		//We are parsing the current page
		Document doc = Jsoup.connect("https://store.manutd.com/en/manchester-united-women-tops/t-92758946+ga-79+d-455010955+z-8-4246603138?pageSize=96&sortOption=TopSellers").get();  
//			       Element body = doc.body();
		Elements div =doc.select("div[class=grid-small-1-medium-3 row small-up-1 medium-up-3]");
		Elements cols =div.select("div[class=column]");
		Elements product =cols.select("div[class=product-image-container]");
		Elements span =cols.select("span[aria-hidden=true]");
		Elements price=span.select("span[aria-hidden=true]>span");
		Elements price_coin=span.select("span[aria-hidden=true]>span").next();
		Elements a =product.select("a");
		Elements img =a.select("img");
			       

	
		Parser(DB_URL,User,Pass,price,price_coin,a,img,count); 
			System.out.println("Testing connection to Postgresql JDBC");
		
			
			try {
				Class.forName("org.postgresql.Driver");
			}catch(ClassNotFoundException e) {
				System.out.println("Postgresql not found");
				e.printStackTrace();
				return "man_fk_non";
			}
			
		boolean is_empty=false;
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,price,price_coin,a,img,"women_tops");
		
	
			System.out.println('\n'); 
	       System.out.println(count);
	   	fill_db(connection,is_empty,DB_URL,User,Pass,price,price_coin,a,img,"women_tops","women_top");
 
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,"women_tops","women_top");
		
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
}


@GetMapping("/women_headwear")
public String women_headwear(Model model) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Ladies Hats, Man Utd Ladies Caps, Headwear");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN HEADWEAR");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Integer count=0;
//		ArrayList<Elements> price =new ArrayList<Elements>();
//		ArrayList<Elements> price_coin =new ArrayList<Elements>();
//		ArrayList<Elements> a =new ArrayList<Elements>();
//		ArrayList<Elements> img =new ArrayList<Elements>();
		
		//We are parsing the current page
		Document doc = Jsoup.connect("https://store.manutd.com/en/manchester-united-women-headwear/t-25083457+ga-24+d-122776362+z-8-3298996914?pageSize=96&sortOption=TopSellers").get();  
//			       Element body = doc.body();
		Elements div =doc.select("div[class=grid-small-1-medium-3 row small-up-1 medium-up-3]");
		Elements cols =div.select("div[class=column]");
		Elements product =cols.select("div[class=product-image-container]");
		Elements span =cols.select("span[aria-hidden=true]");
		Elements price=span.select("span[aria-hidden=true]>span");
		Elements price_coin=span.select("span[aria-hidden=true]>span").next();
		Elements a =product.select("a");
		Elements img =a.select("img");
			       

	
		Parser(DB_URL,User,Pass,price,price_coin,a,img,count); 
			System.out.println("Testing connection to Postgresql JDBC");
		
			
			try {
				Class.forName("org.postgresql.Driver");
			}catch(ClassNotFoundException e) {
				System.out.println("Postgresql not found");
				e.printStackTrace();
				return "man_fk_non";
			}
			
		boolean is_empty=false;
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,price,price_coin,a,img,"women_headwear");
		
	
			System.out.println('\n'); 
	       System.out.println(count);
	   	fill_db(connection,is_empty,DB_URL,User,Pass,price,price_coin,a,img,"women_headwear","women_headwear");
 
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,"women_headwear","women_headwear");
		
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
}
	
	
@GetMapping("/women_scarves")
public String women_scarves(Model model) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Scarf, Man Utd Scarves");
		model.addAttribute("direct_name","MANCHESTER UNITED SCARVES");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Integer count=0;
//		ArrayList<Elements> price =new ArrayList<Elements>();
//		ArrayList<Elements> price_coin =new ArrayList<Elements>();
//		ArrayList<Elements> a =new ArrayList<Elements>();
//		ArrayList<Elements> img =new ArrayList<Elements>();
		
		//We are parsing the current page
		Document doc = Jsoup.connect("https://store.manutd.com/en/manchester-united-scarves/t-36978902+d-8983874046+z-93-121596496?pageSize=96&sortOption=TopSellers").get();  
//			       Element body = doc.body();
		Elements div =doc.select("div[class=grid-small-1-medium-3 row small-up-1 medium-up-3]");
		Elements cols =div.select("div[class=column]");
		Elements product =cols.select("div[class=product-image-container]");
		Elements span =cols.select("span[aria-hidden=true]");
		Elements price=span.select("span[aria-hidden=true]>span");
		Elements price_coin=span.select("span[aria-hidden=true]>span").next();
		Elements a =product.select("a");
		Elements img =a.select("img");
			       

	
		Parser(DB_URL,User,Pass,price,price_coin,a,img,count); 
			System.out.println("Testing connection to Postgresql JDBC");
		
			
			try {
				Class.forName("org.postgresql.Driver");
			}catch(ClassNotFoundException e) {
				System.out.println("Postgresql not found");
				e.printStackTrace();
				return "man_fk_non";
			}
			
		boolean is_empty=false;
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,price,price_coin,a,img,"women_scarves");
		
	
			System.out.println('\n'); 
	       System.out.println(count);
	   	fill_db(connection,is_empty,DB_URL,User,Pass,price,price_coin,a,img,"women_scarves","women_scarve");
 
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,"women_scarves","women_scarve");
		
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
}	
	
	
	
@GetMapping("/women_tops_lp")
public String women_tops_lp(Model model) throws SQLException{
		model.addAttribute("title", " Manchester United Ladies Tops, Man Utd Ladies Shirt");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN TOPS");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		String tab_name="women_tops";
		String tab_sub_name="women_top";
		System.out.println("women_tops - lowest price!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

	
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			
	
		}
		
		System.out.println(pile.get(0).alt_arr);
		
		System.out.println(pile.size());
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else {
			System.out.println(pile.get(0).alt_arr);
			if(pile.size()%3!=0) {
//				System.out.println("Has");
				adad=pile.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int i=0;i<adad;i++) {
						System.out.println("!");
						pile.remove(i);
						
					}
			}
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//		     		model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
		}
		
		connection.close();
		prepare.close();
		result.close();
	
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
}
	
	
@GetMapping("/women_tops_hp")
public String women_tops_hp(Model model) throws SQLException{
	
		model.addAttribute("title", " Manchester United Ladies Tops, Man Utd Ladies Shirt");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN TOPS");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		String tab_name="women_tops";
		String tab_sub_name="women_top";
		System.out.println("women_tops - lowest price!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

	
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			
	
		}
		
		System.out.println(pile.get(0).alt_arr);
		
		System.out.println(pile.size());
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else {
			System.out.println(pile.get(0).alt_arr);
			if(pile.size()%3!=0) {
//				System.out.println("Has");
				adad=pile.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int i=0;i<adad;i++) {
						System.out.println("!");
						pile.remove(i);
						
					}
			}
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//		     		model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
		}
		connection.close();
		prepare.close();
		result.close();
	
		
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
		
}
	
	
@GetMapping("/women_tops_ni")
public String women_tops_ni(Model model) throws SQLException{
	
		model.addAttribute("title", " Manchester United Ladies Tops, Man Utd Ladies Shirt");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN TOPS");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		String tab_name="women_tops";
		String tab_sub_name="women_top";
		System.out.println("women_tops - lowest price!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

	
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			
	
		}
		
		System.out.println(pile.get(0).alt_arr);
		
		System.out.println(pile.size());
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else {
			System.out.println(pile.get(0).alt_arr);
			if(pile.size()%3!=0) {
//				System.out.println("Has");
				adad=pile.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int i=0;i<adad;i++) {
						System.out.println("!");
						pile.remove(i);
						
					}
			}
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			
			model.addAttribute("img_source", pile);
//		     		model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
		}
		
		connection.close();
		prepare.close();
		result.close();
	
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
}
	
	
@GetMapping("/women_tops_12")
public String women_tops_12(Model model,HttpServletRequest request) throws SQLException{
		model.addAttribute("title", " Manchester United Ladies Tops, Man Utd Ladies Shirt");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN TOPS");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		
		System.out.println("women_tops - size-12!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,"women_tops","women_top","women_top_id",request,13);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
		
}
	
	
@GetMapping("/women_tops_24")
public String women_tops_24(Model model,HttpServletRequest request) throws SQLException{
		
		model.addAttribute("title", " Manchester United Ladies Tops, Man Utd Ladies Shirt");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN TOPS");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		
		System.out.println("women_tops - size-24!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,"women_tops","women_top","women_top_id",request,25);
		
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
		
}
	

	
@GetMapping("/women_tops_36")
public String women_tops_36(Model model,HttpServletRequest request) throws SQLException{
		
		model.addAttribute("title", " Manchester United Ladies Tops, Man Utd Ladies Shirt");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN TOPS");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		
		System.out.println("women_tops - size-36!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,"women_tops","women_top","women_top_id",request,37);
		
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
		
}
	
	

@GetMapping("/women_headwear_lp")
public String women_headwear_lp(Model model) throws SQLException{
		model.addAttribute("title", "Manchester United Ladies Hats, Man Utd Ladies Caps, Headwear");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN HEADWEAR");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		String tab_name="women_headwear";
		String tab_sub_name="women_headwear";
		System.out.println("women_headwear - lowest price!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

	
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			
	
		}
		
		System.out.println(pile.get(0).alt_arr);
		
		System.out.println(pile.size());
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else {
			System.out.println(pile.get(0).alt_arr);
			if(pile.size()%3!=0) {
//				System.out.println("Has");
				adad=pile.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int i=0;i<adad;i++) {
						System.out.println("!");
						pile.remove(i);
						
					}
			}
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//		     		model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
		}
		
		connection.close();
		prepare.close();
		result.close();
	
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
}
	
	
@GetMapping("/women_headwear_hp")
public String women_headwear_hp(Model model) throws SQLException{
	
		model.addAttribute("title", "Manchester United Ladies Hats, Man Utd Ladies Caps, Headwear");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN HEADWEAR");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		String tab_name="women_headwear";
		String tab_sub_name="women_headwear";
		System.out.println("women_headwear - lowest price!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

	
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			
	
		}
		
		System.out.println(pile.get(0).alt_arr);
		
		System.out.println(pile.size());
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else {
			System.out.println(pile.get(0).alt_arr);
			if(pile.size()%3!=0) {
//				System.out.println("Has");
				adad=pile.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int i=0;i<adad;i++) {
						System.out.println("!");
						pile.remove(i);
						
					}
			}
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//		     		model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
		}
		connection.close();
		prepare.close();
		result.close();
	
		
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
		
}
	
	
@GetMapping("/women_headwear_ni")
public String women_headwear_ni(Model model) throws SQLException{
	
		model.addAttribute("title", "Manchester United Ladies Hats, Man Utd Ladies Caps, Headwear");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN HEADWEAR");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		String tab_name="women_headwear";
		String tab_sub_name="women_headwear";
		System.out.println("women_headwear - lowest price!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

	
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			
	
		}
		
		System.out.println(pile.get(0).alt_arr);
		
		System.out.println(pile.size());
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else {
			System.out.println(pile.get(0).alt_arr);
			if(pile.size()%3!=0) {
//				System.out.println("Has");
				adad=pile.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int i=0;i<adad;i++) {
						System.out.println("!");
						pile.remove(i);
						
					}
			}
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			
			model.addAttribute("img_source", pile);
//		     		model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
		}
		
		connection.close();
		prepare.close();
		result.close();
	
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
}
	
	
@GetMapping("/women_headwear_12")
public String women_headwear_12(Model model,HttpServletRequest request) throws SQLException{
		model.addAttribute("title", "Manchester United Ladies Hats, Man Utd Ladies Caps, Headwear");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN HEADWEAR");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		
		System.out.println("women_headwear - size-12!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,"women_headwear","women_headwear","women_headwear_id",request,13);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
		
}
	
	
@GetMapping("/women_headwear_24")
public String women_headwear_24(Model model,HttpServletRequest request) throws SQLException{
		
		model.addAttribute("title", "Manchester United Ladies Hats, Man Utd Ladies Caps, Headwear");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN HEADWEAR");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		
		System.out.println("women_headwear - size-24!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,"women_headwear","women_headwear","women_headwear_id",request,25);
		
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
		
}
	

	
@GetMapping("/women_headwear_36")
public String women_headwear_36(Model model,HttpServletRequest request) throws SQLException{
		
		model.addAttribute("title", "Manchester United Ladies Hats, Man Utd Ladies Caps, Headwear");
		model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN HEADWEAR");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		
		System.out.println("women_headwear - size-36!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,"women_headwear","women_headwear","women_headwear_id",request,37);
		
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
		
}
	
	


@GetMapping("/women_scarves_lp")
public String women_scarves_lp(Model model) throws SQLException{
		model.addAttribute("title", "Manchester United Scarf, Man Utd Scarves");
		model.addAttribute("direct_name","MANCHESTER UNITED SCARVES");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		String tab_name="women_scarves";
		String tab_sub_name="women_scarve";
		System.out.println("women_scarves - lowest price!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

	
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			
	
		}
		
		System.out.println(pile.get(0).alt_arr);
		
		System.out.println(pile.size());
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else {
			System.out.println(pile.get(0).alt_arr);
			if(pile.size()%3!=0) {
//				System.out.println("Has");
				adad=pile.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int i=0;i<adad;i++) {
						System.out.println("!");
						pile.remove(i);
						
					}
			}
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//		     		model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
		}
		
		connection.close();
		prepare.close();
		result.close();
	
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
}
	
	
@GetMapping("/women_scarves_hp")
public String women_scarves_hp(Model model) throws SQLException{
		model.addAttribute("title", "Manchester United Scarf, Man Utd Scarves");
		model.addAttribute("direct_name","MANCHESTER UNITED SCARVES");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		String tab_name="women_scarves";
		String tab_sub_name="women_scarve";
		System.out.println("women_scarves - lowest price!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

	
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			
	
		}
		
		System.out.println(pile.get(0).alt_arr);
		
		System.out.println(pile.size());
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else {
			System.out.println(pile.get(0).alt_arr);
			if(pile.size()%3!=0) {
//				System.out.println("Has");
				adad=pile.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int i=0;i<adad;i++) {
						System.out.println("!");
						pile.remove(i);
						
					}
			}
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//		     		model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
		}
		connection.close();
		prepare.close();
		result.close();
	
		
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
		
}
	
	
@GetMapping("/women_scarves_ni")
public String women_scarves_ni(Model model) throws SQLException{
	
		model.addAttribute("title", "Manchester United Scarf, Man Utd Scarves");
		model.addAttribute("direct_name","MANCHESTER UNITED SCARVES");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		String tab_name="women_scarves";
		String tab_sub_name="women_scarve";
		System.out.println("women_scarves - lowest price!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

	
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			
	
		}
		
		System.out.println(pile.get(0).alt_arr);
		
		System.out.println(pile.size());
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
			
		}else {
			System.out.println(pile.get(0).alt_arr);
			if(pile.size()%3!=0) {
//				System.out.println("Has");
				adad=pile.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int i=0;i<adad;i++) {
						System.out.println("!");
						pile.remove(i);
						
					}
			}
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			
			model.addAttribute("img_source", pile);
//		     		model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",tab_name+"_lp");
	     	model.addAttribute("high_price",tab_name+"_hp");
	     	model.addAttribute("new_item",tab_name+"_ni");
	     	model.addAttribute("size_one",tab_name+"_12");
	     	model.addAttribute("size_two",tab_name+"_24");
	     	model.addAttribute("size_three",tab_name+"_36");
	     	model.addAttribute("mass",tab_name);
	      	model.addAttribute("item",tab_name);
	     	model.addAttribute("url","_"+tab_name);
		}
		
		connection.close();
		prepare.close();
		result.close();
	
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
}
	
	
@GetMapping("/women_scarves_12")
public String women_scarves_12(Model model,HttpServletRequest request) throws SQLException{
		model.addAttribute("title", "Manchester United Scarf, Man Utd Scarves");
		model.addAttribute("direct_name","MANCHESTER UNITED SCARVES");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		
		System.out.println("women_scarves - size-12!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,"women_scarves","women_scarve","women_scarve_id",request,13);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
		
}
	
	
@GetMapping("/women_scarves_24")
public String women_scarves_24(Model model,HttpServletRequest request) throws SQLException{
		
		model.addAttribute("title", "Manchester United Scarf, Man Utd Scarves");
		model.addAttribute("direct_name","MANCHESTER UNITED SCARVES");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		
		System.out.println("women_scarves - size-24!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,"women_scarves","women_scarve","women_scarve_id",request,25);
		
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
		
}
	

	
@GetMapping("/women_scarves_36")
public String women_scarves_36(Model model,HttpServletRequest request) throws SQLException{
		
		model.addAttribute("title", "Manchester United Scarf, Man Utd Scarves");
		model.addAttribute("direct_name","MANCHESTER UNITED SCARVES");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		
		System.out.println("women_scarves - size-36!");
		
//		System.out.println(request.getHeader("Referer"));
//		
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
			return "man_fk_non";
		}
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;

		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,"women_scarves","women_scarve","women_scarve_id",request,37);
		
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("man_fk_non");
			return "man_fk_non";
		}
		
}
	
	
}
	