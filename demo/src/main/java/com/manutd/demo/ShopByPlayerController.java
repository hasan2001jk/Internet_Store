package com.manutd.demo;



import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.manutd.demo.AwayController.Dis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;



@Controller


public class ShopByPlayerController {



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
	

public void kit_type_func(Model model,HttpServletRequest request, List<Dis> pile,String tab_name,String tab_sub_name) throws UnsupportedEncodingException, SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	
	String page=request.getHeader("Referer");
	String heq=URLDecoder.decode(page, "UTF-8");
	System.out.println(page);
	
	ArrayList<Integer> pl_id=new ArrayList<Integer>(); 
	ArrayList<String> pl_name =new ArrayList<String>();

	
	Connection connection=null;
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement slt=connection.prepareStatement("SELECT * FROM players",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	ResultSet rst=slt.executeQuery();
		while(rst.next()) {
			String player_name=rst.getString("player_name");
			Integer player_id=rst.getInt("player_id");
			if(heq.contains(player_name)==true) {
				System.out.println(player_name);
				System.out.println(player_id);
				System.out.println("");
				pl_id.add(player_id);
				pl_name.add(player_name);
				System.out.println(pl_name.get(0).toString());
				System.out.println(pl_id.get(0).intValue());
				break;
			}
		}
	slt.close();
	rst.close();
	connection.close();
///////////////////////////////////
	
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	prepare.setInt(1, pl_id.get(0).intValue());
	ResultSet result=prepare.executeQuery();
	
	

	
	
		while(result.next()) {
			
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_kit_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			if(result.isAfterLast()) {
				break;
			}
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			
	
		}
		
		prepare.close();
		result.close();
		connection.close();
		
		
		
		
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price","func_pl"+"_lp");
	     	model.addAttribute("high_price","func_pl"+"_hp");
	     	model.addAttribute("new_item","func_pl"+"_ni");
	     	model.addAttribute("size_one","func_pl"+"_12");
	     	model.addAttribute("size_two","func_pl"+"_24");
	     	model.addAttribute("size_three","func_pl"+"_36");
	     	model.addAttribute("mass",tab_sub_name+"_"+pl_name.get(0));
	     	model.addAttribute("item",tab_sub_name+"_"+pl_name.get(0));
	     	model.addAttribute("url",tab_sub_name+"_"+pl_name.get(0));
	     	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
	    	model.addAttribute("direct_name",pl_name.get(0));
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price","func_pl"+"_lp");
	     	model.addAttribute("high_price","func_pl"+"_hp");
	     	model.addAttribute("new_item","func_pl"+"_ni");
	     	model.addAttribute("size_one","func_pl"+"_12");
	     	model.addAttribute("size_two","func_pl"+"_24");
	     	model.addAttribute("size_three","func_pl"+"_36");
	     	model.addAttribute("mass",tab_sub_name+"_"+pl_name.get(0));
	     	model.addAttribute("item",tab_sub_name+"_"+pl_name.get(0));
	     	model.addAttribute("url",tab_sub_name+"_"+pl_name.get(0));
	     	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
	    	model.addAttribute("direct_name",pl_name.get(0));
			
		}else {
			System.out.println(pile.get(0).alt_arr);
			if(pile.size()%3!=0) {
//				System.out.println("Has");
				adad=pile.size()%3;
				System.out.println(adad);
//				System.out.println("an");
					for(int k=0;k<adad;k++) {
						System.out.println("!");
						pile.remove(k);
						
					}
			}
			
			Collections.shuffle(pile);
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price","func_pl"+"_lp");
	     	model.addAttribute("high_price","func_pl"+"_hp");
	     	model.addAttribute("new_item","func_pl"+"_ni");
	     	model.addAttribute("size_one","func_pl"+"_12");
	     	model.addAttribute("size_two","func_pl"+"_24");
	     	model.addAttribute("size_three","func_pl"+"_36");
	     	model.addAttribute("mass",tab_sub_name+"_"+pl_name.get(0));
	     	model.addAttribute("item",tab_sub_name+"_"+pl_name.get(0));
	     	model.addAttribute("url",tab_sub_name+"_"+pl_name.get(0));
	     	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
	    	model.addAttribute("direct_name",pl_name.get(0));
		}
}
	

public void sub_func_kit(Model model,HttpServletRequest request, List<Dis> pile) throws UnsupportedEncodingException, SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	
	String page=request.getHeader("Referer");
	String heq=URLDecoder.decode(page, "UTF-8");
	System.out.println(page);
	
	boolean kit_contain=false;
	Integer tab_num = 0;
	
	ArrayList<String> tab_name = new ArrayList<String>();
	ArrayList<String> tab_sub_name = new ArrayList<String>();
	ArrayList<String> filter_name = new ArrayList<String>();
	ArrayList<String> filter_num = new ArrayList<String>();
	
	
	tab_name.add("away_kits");
	tab_name.add("keeper_kits");
	tab_name.add("home_kits");
	tab_name.add("third_kits");
/////////////////////////////////
	tab_sub_name.add("away");
	tab_sub_name.add("keeper");
	tab_sub_name.add("home");
	tab_sub_name.add("third");
/////////////////////////////////
	filter_name.add("_lp");
	filter_name.add("_hp");
	filter_name.add("_ni");
////////////////////////////////
	filter_num.add("_12");
	filter_num.add("_24");
	filter_num.add("_36");
	
	int decide_num = 0;
	if(heq.contains("12")) {
		decide_num=12;
	}else if(heq.contains("24")) {
		decide_num=24;
	}else if(heq.contains("36")) {
		decide_num=36;
	}
	
	ArrayList<Integer> pl_id=new ArrayList<Integer>(); 
	ArrayList<String> pl_name =new ArrayList<String>();
	System.out.println(pl_name);
	System.out.println(pl_id);
	
	for(int i=0;i<tab_name.size();i++) {
		if(heq.contains(tab_sub_name.get(i))) {
			
			tab_num=i;
			break;
		}
	}
	
	
	Connection connection=null;
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement slt=connection.prepareStatement("SELECT * FROM players",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	ResultSet rst=slt.executeQuery();
		while(rst.next()) {
			String player_name=rst.getString("player_name");
			Integer player_id=rst.getInt("player_id");
			if(heq.contains(player_name)==true) {
				System.out.println(player_name);
				System.out.println(player_id);
				System.out.println("");
				pl_id.add(player_id);
				pl_name.add(player_name);
				System.out.println(pl_name.get(0).toString());
				System.out.println(pl_id.get(0).intValue());
				break;
			}
		}
	slt.close();
	rst.close();
	connection.close();
	
	for(int i=0;i<filter_name.size();i++) {
		if(heq.contains(filter_name.get(i))) {
			kit_contain=true;
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(tab_num)+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepare.setInt(1, pl_id.get(0).intValue());
			ResultSet result=prepare.executeQuery();
			
				while(result.next()) {
					
					String src=result.getString(tab_sub_name.get(tab_num)+"_img_link");
					String alt=result.getString(tab_sub_name.get(tab_num)+"_kit_descrp");
					Integer pr=result.getInt("price");
					Integer pr_coin=result.getInt("price_coin");
					
					if(result.isAfterLast()) {
						break;
					}
					
					pile.add(new Dis(src, alt, pr, pr_coin));
					
			
				}
				
			prepare.close();
			result.close();
			
			int adad=0;
			
			if(pile.isEmpty()) {
				System.out.println("Do nothing is empty!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","func_pl"+"_lp");
		     	model.addAttribute("high_price","func_pl"+"_hp");
		     	model.addAttribute("new_item","func_pl"+"_ni");
		     	model.addAttribute("size_one","func_pl"+"_12");
		     	model.addAttribute("size_two","func_pl"+"_24");
		     	model.addAttribute("size_three","func_pl"+"_36");
		     	model.addAttribute("mass",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("item",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("url",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
		    	model.addAttribute("direct_name",pl_name.get(0));
				
			}else if(pile.size()<3){
				System.out.println(pile.get(0).alt_arr);
				System.out.println("Do nothing!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				if(heq.contains("_lp")) {
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
				}else if(heq.contains("_hp")) {
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
				}else if(heq.contains("_ni")) {
					model.addAttribute("img_source", pile);
				}
				
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","func_pl"+"_lp");
		     	model.addAttribute("high_price","func_pl"+"_hp");
		     	model.addAttribute("new_item","func_pl"+"_ni");
		     	model.addAttribute("size_one","func_pl"+"_12");
		     	model.addAttribute("size_two","func_pl"+"_24");
		     	model.addAttribute("size_three","func_pl"+"_36");
		     	model.addAttribute("mass",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("item",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("url",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
		    	model.addAttribute("direct_name",pl_name.get(0));
				
			}else {
				System.out.println(pile.get(0).alt_arr);
				if(pile.size()%3!=0) {
//					System.out.println("Has");
					adad=pile.size()%3;
					System.out.println(adad);
//					System.out.println("an");
						for(int k=0;k<adad;k++) {
							System.out.println("!");
							pile.remove(k);
							
						}
				}
				
				Collections.shuffle(pile);
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				if(heq.contains("_lp")) {
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
				}else if(heq.contains("_hp")) {
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
				}else if(heq.contains("_ni")) {
					model.addAttribute("img_source", pile);
				}
//		     	model.addAttribute("width",pile.size());
		     	model.addAttribute("mult",pile.size()-1);
		     	model.addAttribute("low_price","func_pl"+"_lp");
		     	model.addAttribute("high_price","func_pl"+"_hp");
		     	model.addAttribute("new_item","func_pl"+"_ni");
		     	model.addAttribute("size_one","func_pl"+"_12");
		     	model.addAttribute("size_two","func_pl"+"_24");
		     	model.addAttribute("size_three","func_pl"+"_36");
		     	model.addAttribute("mass",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("item",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("url",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		    	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
		    	model.addAttribute("direct_name",pl_name.get(0));
		     	
			}
			
				connection.close();
			
			
			
			break;
		}
	}
	System.out.println(kit_contain);
	if(kit_contain==false) {
		for(int i=0;i<filter_num.size();i++) {
			if(heq.contains(filter_num.get(i))) {
				
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(tab_num)+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0).intValue());
				ResultSet result=prepare.executeQuery();
				
				if(result.isAfterLast()) {
					break;
				}
			
				
				
					while(result.next()) {
						
						String src=result.getString(tab_sub_name.get(tab_num)+"_img_link");
						String alt=result.getString(tab_sub_name.get(tab_num)+"_kit_descrp");
						Integer pr=result.getInt("price");
						Integer pr_coin=result.getInt("price_coin");
						
						
						pile.add(new Dis(src, alt, pr, pr_coin));
						
				
					}
					
			prepare.close();
			result.close();
				
			int adad=0;
			
			if(pile.isEmpty()) {
				System.out.println("Do nothing is empty!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","func_pl"+"_lp");
		     	model.addAttribute("high_price","func_pl"+"_hp");
		     	model.addAttribute("new_item","func_pl"+"_ni");
		     	model.addAttribute("size_one","func_pl"+"_12");
		     	model.addAttribute("size_two","func_pl"+"_24");
		     	model.addAttribute("size_three","func_pl"+"_36");
		     	model.addAttribute("mass",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("item",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("url",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		    	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
		    	model.addAttribute("direct_name",pl_name.get(0));
				
			}else if(pile.size()<3){
				System.out.println(pile.get(0).alt_arr);
				System.out.println("Do nothing!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","func_pl"+"_lp");
		     	model.addAttribute("high_price","func_pl"+"_hp");
		     	model.addAttribute("new_item","func_pl"+"_ni");
		     	model.addAttribute("size_one","func_pl"+"_12");
		     	model.addAttribute("size_two","func_pl"+"_24");
		     	model.addAttribute("size_three","func_pl"+"_36");
		     	model.addAttribute("mass",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("item",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("url",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		    	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
		    	model.addAttribute("direct_name",pl_name.get(0));
				
			}else {
				System.out.println(pile.get(0).alt_arr);
				if(pile.size()%3!=0) {
//					System.out.println("Has");
					adad=pile.size()%3;
					System.out.println(adad);
//					System.out.println("an");
						for(int i_1=0;i_1<adad;i_1++) {
							System.out.println("!");
							pile.remove(i_1);
							
						}
				}
				

			
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				if(pile.size()>=decide_num) {
					System.out.println("pile>decide_num");
					model.addAttribute("img_source", pile.subList(0, decide_num));
					model.addAttribute("mult",pile.subList(0, decide_num).size()-1);
				}else {
					System.out.println("pile<decide_num");
					model.addAttribute("img_source", pile);
					model.addAttribute("mult",pile.size()-1);
				}
//		     	model.addAttribute("width",pile.size());
//		     	model.addAttribute("mult",pile.size()-1);
				model.addAttribute("low_price","func_pl"+"_lp");
		     	model.addAttribute("high_price","func_pl"+"_hp");
		     	model.addAttribute("new_item","func_pl"+"_ni");
		     	model.addAttribute("size_one","func_pl"+"_12");
		     	model.addAttribute("size_two","func_pl"+"_24");
		     	model.addAttribute("size_three","func_pl"+"_36");
		     	model.addAttribute("mass",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("item",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		     	model.addAttribute("url",tab_sub_name.get(tab_num)+"_"+pl_name.get(0));
		    	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
		    	model.addAttribute("direct_name",pl_name.get(0));
			}
			
				connection.close();
			
			
				break;
			}
		}

	}
		
}




	
	
public void sub_func(Model model,HttpServletRequest request, List<Dis> pile,Integer cat_num,Integer num_extra,Integer num_exx) throws UnsupportedEncodingException, SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	
	String page=request.getHeader("Referer");
	String heq=URLDecoder.decode(page, "UTF-8");
	System.out.println(page);
	
	boolean kit_contain=false;
	
	ArrayList<String> tab_name = new ArrayList<String>();
	ArrayList<String> tab_sub_name = new ArrayList<String>();
	
	tab_name.add("away_kits");
	tab_name.add("keeper_kits");
	tab_name.add("home_kits");
	tab_name.add("third_kits");
/////////////////////////////////
	tab_sub_name.add("away");
	tab_sub_name.add("keeper");
	tab_sub_name.add("home");
	tab_sub_name.add("third");
	
	
	
	ArrayList<Integer> pl_id=new ArrayList<Integer>(); 
	ArrayList<String> pl_name =new ArrayList<String>();
	System.out.println(pl_name);
	System.out.println(pl_id);
	

	Connection connection=null;
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement slt=connection.prepareStatement("SELECT * FROM players",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	ResultSet rst=slt.executeQuery();
		while(rst.next()) {
			String player_name=rst.getString("player_name");
			Integer player_id=rst.getInt("player_id");
			if(heq.contains(player_name)==true) {
				System.out.println(player_name);
				System.out.println(player_id);
				System.out.println("");
				pl_id.add(player_id);
				pl_name.add(player_name);
				System.out.println(pl_name.get(0).toString());
				System.out.println(pl_id.get(0).intValue());
				break;
			}
		}
	slt.close();
	rst.close();
	connection.close();
	
	
	for(int i=0;i<tab_name.size();i++) {
		if(heq.contains(tab_sub_name.get(i))==true) {
			System.out.println("TRUE");
			if(heq.contains(pl_name.get(0))==true) {
				System.out.println("TRUE");
				kit_contain=true;
					if(num_extra==null && num_exx==null) {
						
						connection = DriverManager.getConnection(DB_URL,User,Pass);
						PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(i)+" WHERE player_id=(?) AND category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						prepare.setInt(1, pl_id.get(0).intValue());
						prepare.setInt(2, cat_num);
						ResultSet result=prepare.executeQuery();
							
						
							while(result.next()) {
								
								String src=result.getString(tab_sub_name.get(i)+"_img_link");
								String alt=result.getString(tab_sub_name.get(i)+"_kit_descrp");
								Integer pr=result.getInt("price");
								Integer pr_coin=result.getInt("price_coin");
								
								if(result.isAfterLast()) {
									break;
								}
								
								pile.add(new Dis(src, alt, pr, pr_coin));
								
						
							}
							
						prepare.close();
						result.close();
					}else {
						connection = DriverManager.getConnection(DB_URL,User,Pass);
						PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(i)+" WHERE player_id=(?) AND category_id=(?) OR category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						prepare.setInt(1, pl_id.get(0).intValue());
						prepare.setInt(2, cat_num);
						prepare.setInt(3, num_extra);
						prepare.setInt(4, num_exx);
						ResultSet result=prepare.executeQuery();
						
					
					
						
						
							while(result.next()) {
								
								String src=result.getString(tab_sub_name.get(i)+"_img_link");
								String alt=result.getString(tab_sub_name.get(i)+"_kit_descrp");
								Integer pr=result.getInt("price");
								Integer pr_coin=result.getInt("price_coin");
								
								
								if(result.isAfterLast()) {
									break;
								}
								
								pile.add(new Dis(src, alt, pr, pr_coin));
								
						
							}
							
					prepare.close();
					result.close();
					}
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
					model.addAttribute("low_price",pl_name.get(0)+"_lp");
			     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
			     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
			     	model.addAttribute("size_one",pl_name.get(0)+"_12");
			     	model.addAttribute("size_two",pl_name.get(0)+"_24");
			     	model.addAttribute("size_three",pl_name.get(0)+"_36");
			     	model.addAttribute("mass",tab_sub_name.get(i)+"_"+pl_name.get(0));
			     	model.addAttribute("item",tab_sub_name.get(i)+"_"+pl_name.get(0));
			     	model.addAttribute("url",tab_sub_name.get(i)+"_"+pl_name.get(0));
			     	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
			    	model.addAttribute("direct_name",pl_name.get(0));
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
					model.addAttribute("low_price",pl_name.get(0)+"_lp");
			     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
			     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
			     	model.addAttribute("size_one",pl_name.get(0)+"_12");
			     	model.addAttribute("size_two",pl_name.get(0)+"_24");
			     	model.addAttribute("size_three",pl_name.get(0)+"_36");
			     	model.addAttribute("mass",tab_sub_name.get(i)+"_"+pl_name.get(0));
			     	model.addAttribute("item",tab_sub_name.get(i)+"_"+pl_name.get(0));
			     	model.addAttribute("url",tab_sub_name.get(i)+"_"+pl_name.get(0));
			     	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
			    	model.addAttribute("direct_name",pl_name.get(0));
					
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int k=0;k<adad;k++) {
								System.out.println("!");
								pile.remove(k);
								
							}
					}
					
					Collections.shuffle(pile);
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
					model.addAttribute("low_price",pl_name.get(0)+"_lp");
			     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
			     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
			     	model.addAttribute("size_one",pl_name.get(0)+"_12");
			     	model.addAttribute("size_two",pl_name.get(0)+"_24");
			     	model.addAttribute("size_three",pl_name.get(0)+"_36");
			     	model.addAttribute("mass",tab_sub_name.get(i)+"_"+pl_name.get(0));
			     	model.addAttribute("item",tab_sub_name.get(i)+"_"+pl_name.get(0));
			     	model.addAttribute("url",tab_sub_name.get(i)+"_"+pl_name.get(0));
			    	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
			    	model.addAttribute("direct_name",pl_name.get(0));
			     	
				}
				
					connection.close();
				
				
/////////////interanl if ends	
			}
/////////////Exteranl if ends
		}
/////////////Exteranl for ends
	}
	
	if(kit_contain==true) {
		System.out.println("Contains");
	}else {
		
		for(int i=0;i<tab_name.size();i++) {
				if(heq.contains(pl_name.get(0))) {
					for(int j=0;j<tab_name.size();j++) {
						if(num_extra==null && num_exx==null) {
							
							connection = DriverManager.getConnection(DB_URL,User,Pass);
							PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(j)+" WHERE player_id=(?) AND category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
							prepare.setInt(1, pl_id.get(0).intValue());
							prepare.setInt(2, cat_num);
							ResultSet result=prepare.executeQuery();
							
							
						
							
							
								while(result.next()) {
									
									String src=result.getString(tab_sub_name.get(j)+"_img_link");
									String alt=result.getString(tab_sub_name.get(j)+"_kit_descrp");
									Integer pr=result.getInt("price");
									Integer pr_coin=result.getInt("price_coin");
									
									if(result.isAfterLast()) {
										break;
									}
									
									pile.add(new Dis(src, alt, pr, pr_coin));
									
							
								}
								
						prepare.close();
						result.close();
					}else {
						connection = DriverManager.getConnection(DB_URL,User,Pass);
						PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(j)+" WHERE player_id=(?) AND category_id=(?) OR category_id=(?) OR category_id=(?) ",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						prepare.setInt(1, pl_id.get(0).intValue());
						prepare.setInt(2, cat_num);
						prepare.setInt(3, num_extra);
						prepare.setInt(4, num_exx);
						ResultSet result=prepare.executeQuery();
						
						if(result.isAfterLast()) {
							break;
						}
					
						
						
							while(result.next()) {
								
								String src=result.getString(tab_sub_name.get(j)+"_img_link");
								String alt=result.getString(tab_sub_name.get(j)+"_kit_descrp");
								Integer pr=result.getInt("price");
								Integer pr_coin=result.getInt("price_coin");
								
								
								pile.add(new Dis(src, alt, pr, pr_coin));
								
						
							}
							
					prepare.close();
					result.close();
					}
				}
					
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
	//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
	//				model.addAttribute("img_source", pile);
	//		     	model.addAttribute("width",pile.size());
					model.addAttribute("low_price",pl_name.get(0)+"_lp");
			     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
			     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
			     	model.addAttribute("size_one",pl_name.get(0)+"_12");
			     	model.addAttribute("size_two",pl_name.get(0)+"_24");
			     	model.addAttribute("size_three",pl_name.get(0)+"_36");
			     	model.addAttribute("mass",pl_name.get(0));
			     	model.addAttribute("item",pl_name.get(0));
			     	model.addAttribute("url",pl_name.get(0));
			     	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
			    	model.addAttribute("direct_name",pl_name.get(0));
			    	
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
	//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile);
	//		     	model.addAttribute("width",pile.size());
					model.addAttribute("low_price",pl_name.get(0)+"_lp");
			     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
			     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
			     	model.addAttribute("size_one",pl_name.get(0)+"_12");
			     	model.addAttribute("size_two",pl_name.get(0)+"_24");
			     	model.addAttribute("size_three",pl_name.get(0)+"_36");
			     	model.addAttribute("mass",pl_name.get(0));
			     	model.addAttribute("item",pl_name.get(0));
			     	model.addAttribute("url",pl_name.get(0));
			     	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
			    	model.addAttribute("direct_name",pl_name.get(0));
					
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
	//					System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
	//					System.out.println("an");
							for(int k=0;k<adad;k++) {
								System.out.println("!");
								pile.remove(k);
								
							}
					}
					
					Collections.shuffle(pile);
					System.out.println(pile.size());
	//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile);
	//		     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
					model.addAttribute("low_price",pl_name.get(0)+"_lp");
			     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
			     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
			     	model.addAttribute("size_one",pl_name.get(0)+"_lp_12");
			     	model.addAttribute("size_two",pl_name.get(0)+"_lp_24");
			     	model.addAttribute("size_three",pl_name.get(0)+"_lp_36");
			     	model.addAttribute("mass",pl_name.get(0));
			     	model.addAttribute("item",pl_name.get(0));
			     	model.addAttribute("url",pl_name.get(0));
			     	model.addAttribute("title",pl_name.get(0)+" Manchester United Kits");
			    	model.addAttribute("direct_name",pl_name.get(0));
					
				}
				
					connection.close();
				
				
	/////////////interanl if ends	
					
				}
		}
	}
	
}
	
	
	
	
	
	
public boolean db_empty(String tab_name) {
	boolean is_empty=false;
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	
	Connection connection=null;
	
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
			connection.close();
		}catch(SQLException w) {
			w.printStackTrace();
		}
		
	return is_empty;
}
	




public void func_main(Model model,HttpServletRequest request, List<Dis> pile) throws UnsupportedEncodingException, SQLException {
	String page=request.getHeader("Referer");
	String heq=URLDecoder.decode(page, "UTF-8");
	System.out.println(page);
//	
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	
	Integer decide_num=0;
	
	if(heq.contains("12")) {
		decide_num=12;
	}else if(heq.contains("24")) {
		decide_num=24;
	}else if(heq.contains("36")) {
		decide_num=36;
	}
	
	
	ArrayList<String> tab_name = new ArrayList<String>();
	ArrayList<String> tab_sub_name = new ArrayList<String>();
	
	tab_name.add("away_kits");
	tab_name.add("keeper_kits");
	tab_name.add("home_kits");
	tab_name.add("third_kits");
/////////////////////////////////
	tab_sub_name.add("away");
	tab_sub_name.add("keeper");
	tab_sub_name.add("home");
	tab_sub_name.add("third");
	
	
	ArrayList<Integer> pl_id=new ArrayList<Integer>(); 
	ArrayList<String> pl_name =new ArrayList<String>();
	System.out.println(pl_name);
	System.out.println(pl_id);
	
//	String tab_name="away_kits";
//	String tab_sub_name="away";
	
	Connection connection=null;
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement slt=connection.prepareStatement("SELECT * FROM players",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	ResultSet rst=slt.executeQuery();
		while(rst.next()) {
			String player_name=rst.getString("player_name");
			Integer player_id=rst.getInt("player_id");
			if(heq.contains(player_name)==true) {
				System.out.println(player_name);
				System.out.println(player_id);
				System.out.println("");
				pl_id.add(player_id);
				pl_name.add(player_name);
				System.out.println(pl_name.get(0).toString());
				System.out.println(pl_id.get(0).intValue());
				break;
			}
		}
	slt.close();
	rst.close();
	connection.close();
	
	
	if(heq.contains("_lp_12") || heq.contains("_lp_24") || heq.contains("_lp_36")) {
		for(int i=0;i<tab_name.size();i++) {	
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(i)+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepare.setInt(1, pl_id.get(0).intValue());
			ResultSet result=prepare.executeQuery();
			
			if(result.isAfterLast()) {
				break;
			}
		
			
			
				while(result.next()) {
					
					String src=result.getString(tab_sub_name.get(i)+"_img_link");
					String alt=result.getString(tab_sub_name.get(i)+"_kit_descrp");
					Integer pr=result.getInt("price");
					Integer pr_coin=result.getInt("price_coin");
					
					
					pile.add(new Dis(src, alt, pr, pr_coin));
					
			
				}
				
		prepare.close();
		result.close();
///////////////////////for ends
		}
		
		
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	      	model.addAttribute("size_one",pl_name.get(0)+"_lp_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_lp_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_lp_36");
	     	model.addAttribute("mass",pl_name.get(0));
	      	model.addAttribute("item",pl_name.get(0)+"_lp");
	     	model.addAttribute("url","_"+pl_name.get(0));
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	      	model.addAttribute("size_one",pl_name.get(0)+"_lp_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_lp_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_lp_36");
	     	model.addAttribute("mass",pl_name.get(0));
	      	model.addAttribute("item",pl_name.get(0)+"_lp");
	     	model.addAttribute("url","_"+pl_name.get(0));
			
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
			
			Collections.shuffle(pile);
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			if(pile.size()>=decide_num) {
				System.out.println("pile>decide_num");
				model.addAttribute("img_source", pile.subList(0, decide_num).stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
				model.addAttribute("mult",pile.subList(0, decide_num).size()-1);
			}else {
				System.out.println("pile<decide_num");
				model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
				model.addAttribute("mult",pile.size()-1);
			}
//	     	model.addAttribute("width",pile.size());
//	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_lp_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_lp_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_lp_36");
	     	model.addAttribute("mass",pl_name.get(0));
	      	model.addAttribute("item",pl_name.get(0)+"_lp");
	     	model.addAttribute("url","_"+pl_name.get(0));
	     	
		}
		
			connection.close();
		
		
//		
	}else if(heq.contains("_hp_12") || heq.contains("_hp_24") || heq.contains("_hp_36")) {
		
		for(int i=0;i<tab_name.size();i++) {	
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(i)+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepare.setInt(1, pl_id.get(0).intValue());
			ResultSet result=prepare.executeQuery();
			
			if(result.isAfterLast()) {
				break;
			}
		
			
			
			while(result.next()) {
				
				String src=result.getString(tab_sub_name.get(i)+"_img_link");
				String alt=result.getString(tab_sub_name.get(i)+"_kit_descrp");
				Integer pr=result.getInt("price");
				Integer pr_coin=result.getInt("price_coin");
				
				
				pile.add(new Dis(src, alt, pr, pr_coin));
				
		
			}
				
		prepare.close();
		result.close();
///////////////////////for ends
		}
		
		
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_hp_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_hp_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_hp_36");
	     	model.addAttribute("mass",pl_name.get(0));
	      	model.addAttribute("item",pl_name.get(0)+"_hp");
	     	model.addAttribute("url","_"+pl_name.get(0));
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_hp_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_hp_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_hp_36");
	     	model.addAttribute("mass",pl_name.get(0));
	      	model.addAttribute("item",pl_name.get(0)+"_hp");
	     	model.addAttribute("url","_"+pl_name.get(0));
			
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
			
			Collections.shuffle(pile);
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			if(pile.size()>=decide_num) {
				System.out.println("pile>decide_num");
				model.addAttribute("img_source", pile.subList(0, decide_num).stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
				model.addAttribute("mult",pile.subList(0, decide_num).size()-1);
			}else {
				System.out.println("pile<decide_num");
				model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
				model.addAttribute("mult",pile.size()-1);
			}
//	     	model.addAttribute("width",pile.size());
//	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_hp_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_hp_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_hp_36");
	     	model.addAttribute("mass",pl_name.get(0));
	      	model.addAttribute("item",pl_name.get(0)+"_hp");
	     	model.addAttribute("url","_"+pl_name.get(0));
	     	
		}
		
			connection.close();
		
		
//		
		
	}else if(heq.contains("_ni_12") || heq.contains("_ni_24") || heq.contains("_ni_36")) {
		
		for(int i=0;i<tab_name.size();i++) {	
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(i)+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepare.setInt(1, pl_id.get(0).intValue());
			ResultSet result=prepare.executeQuery();
			
			if(result.isAfterLast()) {
				break;
			}
		
			
			
				while(result.next()) {
					
					String src=result.getString(tab_sub_name.get(i)+"_img_link");
					String alt=result.getString(tab_sub_name.get(i)+"_kit_descrp");
					Integer pr=result.getInt("price");
					Integer pr_coin=result.getInt("price_coin");
					
					
					pile.add(new Dis(src, alt, pr, pr_coin));
					
			
				}
				
		prepare.close();
		result.close();
///////////////////////for ends
		}
		
		
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_ni_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_ni_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_ni_36");
	     	model.addAttribute("mass",pl_name.get(0));
	      	model.addAttribute("item",pl_name.get(0)+"_ni");
	     	model.addAttribute("url","_"+pl_name.get(0));
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_ni_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_ni_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_ni_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0)+"_ni");
	     	model.addAttribute("url","_"+pl_name.get(0));
			
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
			
			Collections.shuffle(pile);
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			if(pile.size()>=decide_num) {
				System.out.println("pile>decide_num");
				model.addAttribute("img_source", pile.subList(0, decide_num));
				model.addAttribute("mult",pile.subList(0, decide_num).size()-1);
			}else {
				System.out.println("pile<decide_num");
				model.addAttribute("img_source", pile);
				model.addAttribute("mult",pile.size()-1);
			}
//	     	model.addAttribute("width",pile.size());
//	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_ni_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_ni_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_ni_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0)+"_ni");
	     	model.addAttribute("url","_"+pl_name.get(0));
	     	
		}
		
			connection.close();
		
		
//		
		
	}else if(heq.contains("_lp")) {
		for(int i=0;i<tab_name.size();i++) {	
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(i)+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepare.setInt(1, pl_id.get(0).intValue());
			ResultSet result=prepare.executeQuery();
			
			if(result.isAfterLast()) {
				break;
			}
		
			
			
			while(result.next()) {
				
				String src=result.getString(tab_sub_name.get(i)+"_img_link");
				String alt=result.getString(tab_sub_name.get(i)+"_kit_descrp");
				Integer pr=result.getInt("price");
				Integer pr_coin=result.getInt("price_coin");
				
				
				pile.add(new Dis(src, alt, pr, pr_coin));
				
		
			}
			
			
			
		prepare.close();
		result.close();
///////////////////////for ends
		}
		
		
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_lp_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_lp_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_lp_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0)+"_lp");
	     	model.addAttribute("url","_"+pl_name.get(0));
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_lp_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_lp_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_lp_36");
	     	model.addAttribute("mass",pl_name.get(0));
	      	model.addAttribute("item",pl_name.get(0)+"_lp");
	     	model.addAttribute("url","_"+pl_name.get(0));
			
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
			
			Collections.shuffle(pile);
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_lp_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_lp_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_lp_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0)+"_lp");
	     	model.addAttribute("url","_"+pl_name.get(0));
	     	
		}
		
			connection.close();
		
		
//		
	}else if(heq.contains("_hp")) {
		
		for(int i=0;i<tab_name.size();i++) {	
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(i)+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepare.setInt(1, pl_id.get(0).intValue());
			ResultSet result=prepare.executeQuery();
			
			if(result.isAfterLast()) {
				break;
			}
		
			
			
			while(result.next()) {
				
				String src=result.getString(tab_sub_name.get(i)+"_img_link");
				String alt=result.getString(tab_sub_name.get(i)+"_kit_descrp");
				Integer pr=result.getInt("price");
				Integer pr_coin=result.getInt("price_coin");
				
				
				pile.add(new Dis(src, alt, pr, pr_coin));
				
		
			}
				
		prepare.close();
		result.close();
///////////////////////for ends
		}
		
		
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_hp_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_hp_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_hp_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0)+"_hp");
	     	model.addAttribute("url","_"+pl_name.get(0));
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_hp_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_hp_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_hp_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0)+"_hp");
	     	model.addAttribute("url","_"+pl_name.get(0));
			
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
			
			Collections.shuffle(pile);
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//	     	model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_hp_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_hp_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_hp_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0)+"_hp");
	     	model.addAttribute("url","_"+pl_name.get(0));
	     	
		}
		
			connection.close();
		
		
//		
		
	}else if(heq.contains("_ni")) {
		
		for(int i=0;i<tab_name.size();i++) {	
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(i)+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepare.setInt(1, pl_id.get(0).intValue());
			ResultSet result=prepare.executeQuery();
			
			if(result.isAfterLast()) {
				break;
			}
		
			
			
			while(result.next()) {
				
				String src=result.getString(tab_sub_name.get(i)+"_img_link");
				String alt=result.getString(tab_sub_name.get(i)+"_kit_descrp");
				Integer pr=result.getInt("price");
				Integer pr_coin=result.getInt("price_coin");
				
				
				pile.add(new Dis(src, alt, pr, pr_coin));
				
		
			}
				
		prepare.close();
		result.close();
///////////////////////for ends
		}
		
		
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_ni_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_ni_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_ni_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0)+"_ni");
	     	model.addAttribute("url","_"+pl_name.get(0));
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	       	model.addAttribute("size_one",pl_name.get(0)+"_ni_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_ni_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_ni_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0)+"_ni");
	     	model.addAttribute("url","_"+pl_name.get(0));
			
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
			///////here fix size check
			Collections.shuffle(pile);
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_ni_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_ni_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_ni_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0)+"_ni");
	     	model.addAttribute("url","_"+pl_name.get(0));
	     	
		}
		
			connection.close();
		
		
//		
		
		
	}else if(heq.contains("_12") || heq.contains("_24") || heq.contains("_36")) {
		
		for(int i=0;i<tab_name.size();i++) {	
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(i)+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepare.setInt(1, pl_id.get(0).intValue());
			ResultSet result=prepare.executeQuery();
			
			if(result.isAfterLast()) {
				break;
			}
		
			
			
				while(result.next()) {
					
					String src=result.getString(tab_sub_name.get(i)+"_img_link");
					String alt=result.getString(tab_sub_name.get(i)+"_kit_descrp");
					Integer pr=result.getInt("price");
					Integer pr_coin=result.getInt("price_coin");
					
					
					pile.add(new Dis(src, alt, pr, pr_coin));
					
			
				}
				
		prepare.close();
		result.close();
///////////////////////for ends
		}
		
		
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	       	model.addAttribute("size_one",pl_name.get(0)+"_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0));
	     	model.addAttribute("url","_"+pl_name.get(0));
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	       	model.addAttribute("size_one",pl_name.get(0)+"_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0));
	     	model.addAttribute("url","_"+pl_name.get(0));
			
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
			
			Collections.shuffle(pile);
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			if(pile.size()>=decide_num) {
				System.out.println("pile>decide_num");
				model.addAttribute("img_source", pile.subList(0, decide_num));
				model.addAttribute("mult",pile.subList(0, decide_num).size()-1);
			}else {
				System.out.println("pile<decide_num");
				model.addAttribute("img_source", pile);
				model.addAttribute("mult",pile.size()-1);
			}
//	     	model.addAttribute("width",pile.size());
//	     	model.addAttribute("mult",pile.subList(0, decide_num).size()-1);
	     	model.addAttribute("low_price",pl_name.get(0)+"_lp");
	     	model.addAttribute("high_price",pl_name.get(0)+"_hp");
	     	model.addAttribute("new_item",pl_name.get(0)+"_ni");
	     	model.addAttribute("size_one",pl_name.get(0)+"_12");
	     	model.addAttribute("size_two",pl_name.get(0)+"_24");
	     	model.addAttribute("size_three",pl_name.get(0)+"_36");
	     	model.addAttribute("mass",pl_name.get(0));
	     	model.addAttribute("item",pl_name.get(0));
	     	model.addAttribute("url","_"+pl_name.get(0));
	     	
		}
		
			connection.close();
		
	}
	
}



	
public void select_pl(List<Dis> pile,Model model,Integer num,String text) throws UnsupportedEncodingException, SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	
	ArrayList<String> tab_name = new ArrayList<String>();
	ArrayList<String> tab_sub_name = new ArrayList<String>();
	
	
	
	Connection connection=null;
	tab_name.add("away_kits");
	tab_name.add("keeper_kits");
	tab_name.add("home_kits");
	tab_name.add("third_kits");
/////////////////////////////////
	tab_sub_name.add("away");
	tab_sub_name.add("keeper");
	tab_sub_name.add("home");
	tab_sub_name.add("third");
	
	for(int i=0;i<tab_name.size();i++) {
		
			
			try {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement slt=connection.prepareStatement("SELECT * FROM "+tab_name.get(i)+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				slt.setInt(1, num);
				ResultSet rst=slt.executeQuery();
					while(rst.next()) {
						String src=rst.getString(tab_sub_name.get(i)+"_img_link");
						String alt=rst.getString(tab_sub_name.get(i)+"_kit_descrp");
						Integer pr=rst.getInt("price");
						Integer pr_coin=rst.getInt("price_coin");
						
						if(rst.isAfterLast()) {
							break;
						}
						
						pile.add(new Dis(src, alt, pr, pr_coin));

					}
						
				slt.close();
				rst.close();
				
			}catch(SQLException w) {
				w.printStackTrace();
			}
	////////////////////for ends		
	}
	
	
	int adad=0;
	
	if(pile.isEmpty()) {
		System.out.println("Do nothing is empty!");
		
		System.out.println(pile.size());
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//		model.addAttribute("img_source", pile);
//     	model.addAttribute("width",pile.size());
		model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",text);
     	model.addAttribute("item",text);
     	model.addAttribute("url","_"+text);
		
	}else if(pile.size()<3){
		System.out.println(pile.get(0).alt_arr);
		System.out.println("Do nothing!");
		
		System.out.println(pile.size());
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
		model.addAttribute("img_source", pile);
//     	model.addAttribute("width",pile.size());
		model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",text);
     	model.addAttribute("item",text);
     	model.addAttribute("url","_"+text);
		
	}else {
		System.out.println(pile.get(0).alt_arr);
		if(pile.size()%3!=0) {
//			System.out.println("Has");
			adad=pile.size()%3;
			System.out.println(adad);
//			System.out.println("an");
				for(int i=0;i<adad;i++) {
					System.out.println("!");
					pile.remove(i);
					
				}
		}
		
		Collections.shuffle(pile);
		System.out.println(pile.size());
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
		model.addAttribute("img_source", pile);
//     	model.addAttribute("width",pile.size());
     	model.addAttribute("mult",pile.size()-1);
     	model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",text);
     	model.addAttribute("item",text);
     	model.addAttribute("url","_"+text);
     	
	}
	
		connection.close();

	
}



@GetMapping("/shop_Ronaldo")
public String shop_Ronaldo(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,1,"Ronaldo");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}



@GetMapping("/shop_Wan-Bissaka")
public String shop_Wan_Bissaka(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name", "AARON WAN-BISSAKA");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,10,"Wan-Bissaka");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}
	

@GetMapping("/shop_Fernandes")
public String shop_Fernandes(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,3,"Fernandes");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}



@GetMapping("/shop_De Gea")
public String shop_De_Gea(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David De Gea Manchester United Kits");
	model.addAttribute("direct_name","DAVID DE GEA");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,9,"De Gea");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}



@GetMapping("/shop_Cavani")
public String shop_Cavani(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,6,"Cavani");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}


@GetMapping("/shop_Bailly")
public String shop_Bailly(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","ERIC BAILLY");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,11,"Bailly");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}


@GetMapping("/shop_Varane")
public String shop_Varane(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","RAPHAEL VARANE");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,8,"Varane");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}



@GetMapping("/shop_Lingard")
public String shop_Lingard(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","JESSE LINGARD");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,12,"Lingard");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}



@GetMapping("/shop_Sancho")
public String shop_Sancho(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,5,"Sancho");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}





@GetMapping("/shop_Van_De_Beek")
public String shop_Van_De_Beek(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","DONNY VAN DE BEEK");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,13,"Van De Beek");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}
	

@GetMapping("/shop_Greenwood")
public String shop_Greenwood(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,4,"Greenwood");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}
	


@GetMapping("/shop_Matic")
public String shop_Matic(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","NEMANJA MATIĆ");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,14,"Matic");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}


@GetMapping("/shop_Pogba")
public String shop_Pogba(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,7,"Pogba");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}

@GetMapping("/shop_McTominay")
public String shop_McTominay(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","SCOTT MCTOMINAY");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,15,"McTominay");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}



@GetMapping("/shop_Fred")
public String shop_Fred(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","FRED");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,16,"Fred");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}




@GetMapping("/shop_Rashford")
public String shop_Rashford(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,2,"Rashford");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}



@GetMapping("/shop_Shaw")
public String shop_Shaw(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","LUKE SHAW");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,17,"Shaw");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}



@GetMapping("/shop_Maguire")
public String shop_Maguire(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","HARRY MAGUIRE");
    List<Dis> pile =new ArrayList<Dis>();
    select_pl(pile,model,18,"Harry Maguire");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}


//////////////////////////////////filters
@GetMapping("/Ronaldo_lp")
public String Ronaldo_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}



@GetMapping("/Ronaldo_hp")
public String Ronaldo_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}


@GetMapping("/Ronaldo_ni")
public String Ronaldo_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}


@GetMapping("/Ronaldo_12")
public String Ronaldo_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}


@GetMapping("/Ronaldo_24")
public String Ronaldo_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}


@GetMapping("/Ronaldo_36")
public String Ronaldo_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}

//////////////////////////////////additional filters
@GetMapping("/Ronaldo_lp_12")
public String Ronaldo_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}



@GetMapping("/Ronaldo_lp_24")
public String Ronaldo_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}



@GetMapping("/Ronaldo_lp_36")
public String Ronaldo_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}


@GetMapping("/Ronaldo_hp_12")
public String Ronaldo_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}




@GetMapping("/Ronaldo_hp_24")
public String Ronaldo_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}




@GetMapping("/Ronaldo_hp_36")
public String Ronaldo_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}



@GetMapping("/Ronaldo_ni_12")
public String Ronaldo_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}




@GetMapping("/Ronaldo_ni_24")
public String Ronaldo_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}




@GetMapping("/Ronaldo_ni_36")
public String Ronaldo_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Ronaldo Manchester United Shirts");
	model.addAttribute("direct_name", "CRISTIANO RONALDO");
    List<Dis> pile =new ArrayList<Dis>();
    func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
		
}




//////////////////////////////////filters
@GetMapping("/Rashford_lp")
public String Rashford_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "shop_by_pl";
		}

}



@GetMapping("/Rashford_hp")
public String Rashford_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "shop_by_pl";
		}

}


@GetMapping("/Rashford_ni")
public String Rashford_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "shop_by_pl";
		}

}


@GetMapping("/Rashford_12")
public String Rashford_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "shop_by_pl";
		}

}

@GetMapping("/Rashford_24")
public String Rashford_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "shop_by_pl";
		}

}


@GetMapping("/Rashford_36")
public String Rashford_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "shop_by_pl";
		}

}

//////////////////////////////////additional filters
@GetMapping("/Rashford_lp_12")
public String Rashford_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "shop_by_pl";
		}

}



@GetMapping("/Rashford_lp_24")
public String Rashford_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "shop_by_pl";
		}

}



@GetMapping("/Rashford_lp_36")
public String Rashford_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Rashford_hp_12")
public String Rashford_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Rashford_hp_24")
public String Rashford_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Rashford_hp_36")
public String Rashford_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Rashford_ni_12")
public String Rashford_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Rashford_ni_24")
public String Rashford_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Rashford_ni_36")
public String Rashford_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Marcus Rashford Manchester United Kits");
	model.addAttribute("direct_name","MARCUS RASHFORD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}





//////////////////////////////////filters
@GetMapping("/Fernandes_lp")
public String Fernandes_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}



@GetMapping("/Fernandes_hp")
public String Fernandes_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Fernandes_ni")
public String Fernandes_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Fernandes_12")
public String Fernandes_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}

@GetMapping("/Fernandes_24")
public String Fernandes_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Fernandes_36")
public String Fernandes_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}

//////////////////////////////////additional filters
@GetMapping("/Fernandes_lp_12")
public String Fernandes_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}



@GetMapping("/Fernandes_lp_24")
public String Fernandes_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}



@GetMapping("/Fernandes_lp_36")
public String Fernandes_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Fernandes_hp_12")
public String Fernandes_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Fernandes_hp_24")
public String Fernandes_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Fernandes_hp_36")
public String Fernandes_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Fernandes_ni_12")
public String Fernandes_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Fernandes_ni_24")
public String Fernandes_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Fernandes_ni_36")
public String Fernandes_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Bruno Fernandes Manchester United Kits");
	model.addAttribute("direct_name","BRUNO FERNANDES");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



//////////////////////////////////filters
@GetMapping("/Greenwood_lp")
public String Greenwood_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}



@GetMapping("/Greenwood_hp")
public String Greenwood_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Greenwood_ni")
public String Greenwood_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Greenwood_12")
public String Greenwood_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}

@GetMapping("/Greenwood_24")
public String Greenwood_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}


@GetMapping("/Greenwood_36")
public String Greenwood_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}

//////////////////////////////////additional filters
@GetMapping("/Greenwood_lp_12")
public String Greenwood_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}



@GetMapping("/Greenwood_lp_24")
public String Greenwood_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}



@GetMapping("/Greenwood_lp_36")
public String Greenwood_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Greenwood_hp_12")
public String Greenwood_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}







@GetMapping("/Greenwood_hp_24")
public String Greenwood_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Greenwood_hp_36")
public String Greenwood_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Greenwood_ni_12")
public String Greenwood_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}







@GetMapping("/Greenwood_ni_24")
public String Greenwood_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Greenwood_ni_36")
public String Greenwood_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Mason Greenwood Manchester United Kits");
	model.addAttribute("direct_name","MASON GREENWOOD");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




//////////////////////////////////filters
@GetMapping("/Sancho_lp")
public String Sancho_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}



@GetMapping("/Sancho_hp")
public String Sancho_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Sancho_ni")
public String Sancho_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}


@GetMapping("/Sancho_12")
public String Sancho_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}

@GetMapping("/Sancho_24")
public String Sancho_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}


@GetMapping("/Sancho_36")
public String Sancho_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}

//////////////////////////////////additional filters
@GetMapping("/Sancho_lp_12")
public String Sancho_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}



@GetMapping("/Sancho_lp_24")
public String Sancho_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}



@GetMapping("/Sancho_lp_36")
public String Sancho_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}


@GetMapping("/Sancho_hp_12")
public String Sancho_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







@GetMapping("/Sancho_hp_24")
public String Sancho_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Sancho_hp_36")
public String Sancho_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Sancho_ni_12")
public String Sancho_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







@GetMapping("/Sancho_ni_24")
public String Sancho_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Sancho_ni_36")
public String Sancho_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jadon Sancho Manchester United Kits");
	model.addAttribute("direct_name","JADON SANCHO");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}





//////////////////////////////////filters
@GetMapping("/Cavani_lp")
public String Cavani_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}



@GetMapping("/Cavani_hp")
public String Cavani_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Cavani_ni")
public String Cavani_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}


@GetMapping("/Cavani_12")
public String Cavani_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}

@GetMapping("/Cavani_24")
public String Cavani_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}


@GetMapping("/Cavani_36")
public String Cavani_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}

//////////////////////////////////additional filters
@GetMapping("/Cavani_lp_12")
public String Cavani_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}



@GetMapping("/Cavani_lp_24")
public String Cavani_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}



@GetMapping("/Cavani_lp_36")
public String Cavani_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Cavani_hp_12")
public String Cavani_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







@GetMapping("/Cavani_hp_24")
public String Cavani_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Cavani_hp_36")
public String Cavani_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Cavani_ni_12")
public String Cavani_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







@GetMapping("/Cavani_ni_24")
public String Cavani_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Cavani_ni_36")
public String Cavani_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Edinson Cavani Manchester United Kits");
	model.addAttribute("direct_name","EDINSON CAVANI");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




//////////////////////////////////filters
@GetMapping("/Pogba_lp")
public String Pogba_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}



@GetMapping("/Pogba_hp")
public String Pogba_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Pogba_ni")
public String Pogba_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Pogba_12")
public String Pogba_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}

@GetMapping("/Pogba_24")
public String Pogba_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}


@GetMapping("/Pogba_36")
public String Pogba_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}

//////////////////////////////////additional filters
@GetMapping("/Pogba_lp_12")
public String Pogba_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}



@GetMapping("/Pogba_lp_24")
public String Pogba_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}



@GetMapping("/Pogba_lp_36")
public String Pogba_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Pogba_hp_12")
public String Pogba_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







@GetMapping("/Pogba_hp_24")
public String Pogba_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Pogba_hp_36")
public String Pogba_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Pogba_ni_12")
public String Pogba_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







@GetMapping("/Pogba_ni_24")
public String Pogba_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Pogba_ni_36")
public String Pogba_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Paul Pogba Manchester United Kits");
	model.addAttribute("direct_name","PAUL POGBA");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}





//////////////////////////////////filters
@GetMapping("/Varane_lp")
public String Varane_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}



@GetMapping("/Varane_hp")
public String Varane_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}


@GetMapping("/Varane_ni")
public String Varane_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}


@GetMapping("/Varane_12")
public String Varane_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}

@GetMapping("/Varane_24")
public String Varane_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}





}


@GetMapping("/Varane_36")
public String Varane_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}

//////////////////////////////////additional filters
@GetMapping("/Varane_lp_12")
public String Varane_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}



@GetMapping("/Varane_lp_24")
public String Varane_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}





}



@GetMapping("/Varane_lp_36")
public String Varane_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Varane_hp_12")
public String Varane_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}







@GetMapping("/Varane_hp_24")
public String Varane_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Varane_hp_36")
public String Varane_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}



@GetMapping("/Varane_ni_12")
public String Varane_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}







@GetMapping("/Varane_ni_24")
public String Varane_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Varane_ni_36")
public String Varane_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Raphael Varane Manchester United Kits");
	model.addAttribute("direct_name","Raphael Varane");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







//////////////////////////////////filters
@GetMapping("/De Gea_lp")
public String De_Gea_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}



@GetMapping("/De Gea_hp")
public String De_Gea_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}


@GetMapping("/De Gea_ni")
public String De_Gea_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}


@GetMapping("/De Gea_12")
public String De_Gea_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}





}

@GetMapping("/De Gea_24")
public String De_Gea_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}





}


@GetMapping("/De Gea_36")
public String De_Gea_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}





}

//////////////////////////////////additional filters
@GetMapping("/De Gea_lp_12")
public String De_Gea_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}





}



@GetMapping("/De Gea_lp_24")
public String De_Gea_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}






}



@GetMapping("/De Gea_lp_36")
public String De_Gea_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}


@GetMapping("/De Gea_hp_12")
public String De_Gea_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}







@GetMapping("/De Gea_hp_24")
public String De_Gea_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}




@GetMapping("/De Gea_hp_36")
public String De_Gea_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}



@GetMapping("/De Gea_ni_12")
public String De_Gea_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}







@GetMapping("/De Gea_ni_24")
public String De_Gea_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}



}




@GetMapping("/De Gea_ni_36")
public String De_Gea_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "David de Gea Manchester United Kits");
	model.addAttribute("direct_name","David de Gea");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}





//////////////////////////////////filters
@GetMapping("/Wan-Bissaka_lp")
public String Wan_Bissaka_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Wan-Bissaka_hp")
public String Wan_Bissaka_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Wan-Bissaka_ni")
public String Wan_Bissaka_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Wan-Bissaka_12")
public String Wan_Bissaka_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}

@GetMapping("/Wan-Bissaka_24")
public String Wan_Bissaka_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Wan-Bissaka_36")
public String Wan_Bissaka_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}

//////////////////////////////////additional filters
@GetMapping("/Wan-Bissaka_lp_12")
public String Wan_Bissaka_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","David Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Wan-Bissaka_lp_24")
public String Wan_Bissaka_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Wan-Bissaka_lp_36")
public String Wan_Bissaka_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}


@GetMapping("/Wan-Bissaka_hp_12")
public String Wan_Bissaka_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}







@GetMapping("/Wan-Bissaka_hp_24")
public String Wan_Bissaka_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}




@GetMapping("/Wan-Bissaka_hp_36")
public String Wan_Bissaka_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}



@GetMapping("/Wan-Bissaka_ni_12")
public String Wan_Bissaka_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}







@GetMapping("/Wan-Bissaka_ni_24")
public String Wan_Bissaka_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}




}




@GetMapping("/Wan-Bissaka_ni_36")
public String Wan_Bissaka_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Aaron Wan-Bissaka Manchester United Kits");
	model.addAttribute("direct_name","Aaron Wan-Bissaka");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}





//////////////////////////////////filters
@GetMapping("/Bailly_lp")
public String Bailly_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Bailly_hp")
public String Bailly_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Bailly_ni")
public String Bailly_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Bailly_12")
public String Bailly_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}

@GetMapping("/Bailly_24")
public String Bailly_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Bailly_36")
public String Bailly_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}

//////////////////////////////////additional filters
@GetMapping("/Bailly_lp_12")
public String Bailly_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Bailly_lp_24")
public String Bailly_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}



@GetMapping("/Bailly_lp_36")
public String Bailly_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Bailly_hp_12")
public String Bailly_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}







@GetMapping("/Bailly_hp_24")
public String Bailly_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Bailly_hp_36")
public String Bailly_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Bailly_ni_12")
public String Bailly_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







@GetMapping("/Bailly_ni_24")
public String Bailly_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Bailly_ni_36")
public String Bailly_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Eric Bailly Manchester United Kits");
	model.addAttribute("direct_name","Eric Bailly");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}






//////////////////////////////////filters
@GetMapping("/Lingard_lp")
public String Lingard_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Lingard_hp")
public String Lingard_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Lingard_ni")
public String Lingard_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Lingard_12")
public String Lingard_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}

@GetMapping("/Lingard_24")
public String Lingard_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Lingard_36")
public String Lingard_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}

//////////////////////////////////additional filters
@GetMapping("/Lingard_lp_12")
public String Lingard_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Lingard_lp_24")
public String Lingard_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}



@GetMapping("/Lingard_lp_36")
public String Lingard_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Lingard_hp_12")
public String Lingard_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}







@GetMapping("/Lingard_hp_24")
public String Lingard_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Lingard_hp_36")
public String Lingard_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Lingard_ni_12")
public String Lingard_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







@GetMapping("/Lingard_ni_24")
public String Lingard_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Lingard_ni_36")
public String Lingard_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Jesse Lingard Manchester United Kits");
	model.addAttribute("direct_name","Jesse Lingard");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




//////////////////////////////////filters
@GetMapping("/Matic_lp")
public String Matic_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Matic_hp")
public String Matic_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Matic_ni")
public String Matic_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Matic_12")
public String Matic_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}

@GetMapping("/Matic_24")
public String Matic_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Matic_36")
public String Matic_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}

//////////////////////////////////additional filters
@GetMapping("/Matic_lp_12")
public String Matic_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Matic_lp_24")
public String Matic_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Matic_lp_36")
public String Matic_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}


@GetMapping("/Matic_hp_12")
public String Matic_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







@GetMapping("/Matic_hp_24")
public String Matic_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Matic_hp_36")
public String Matic_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}



@GetMapping("/Matic_ni_12")
public String Matic_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}







@GetMapping("/Matic_ni_24")
public String Matic_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Matic_ni_36")
public String Matic_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Nemanja Matic Manchester United Kits");
	model.addAttribute("direct_name","Nemanja Matic");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




//////////////////////////////////filters
@GetMapping("/McTominay_lp")
public String McTominay_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/McTominay_hp")
public String McTominay_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/McTominay_ni")
public String McTominay_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/McTominay_12")
public String McTominay_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}

@GetMapping("/McTominay_24")
public String McTominay_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/McTominay_36")
public String McTominay_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}

//////////////////////////////////additional filters
@GetMapping("/McTominay_lp_12")
public String McTominay_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/McTominay_lp_24")
public String McTominay_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/McTominay_lp_36")
public String McTominay_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/McTominay_hp_12")
public String McTominay_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







@GetMapping("/McTominay_hp_24")
public String McTominay_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/McTominay_hp_36")
public String McTominay_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/McTominay_ni_12")
public String McTominay_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}







@GetMapping("/McTominay_ni_24")
public String McTominay_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/McTominay_ni_36")
public String McTominay_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Scott McTominay Manchester United Kits");
	model.addAttribute("direct_name","Scott McTominay");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}





//////////////////////////////////filters
@GetMapping("/Fred_lp")
public String Fred_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Fred_hp")
public String Fred_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Fred_ni")
public String Fred_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Fred_12")
public String Fred_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}

@GetMapping("/Fred_24")
public String Fred_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Fred_36")
public String Fred_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}

//////////////////////////////////additional filters
@GetMapping("/Fred_lp_12")
public String Fred_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
	
}



@GetMapping("/Fred_lp_24")
public String Fred_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}



@GetMapping("/Fred_lp_36")
public String Fred_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Fred_hp_12")
public String Fred_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}







@GetMapping("/Fred_hp_24")
public String Fred_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}




@GetMapping("/Fred_hp_36")
public String Fred_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}



@GetMapping("/Fred_ni_12")
public String Fred_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}







@GetMapping("/Fred_ni_24")
public String Fred_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}




@GetMapping("/Fred_ni_36")
public String Fred_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Fred Manchester United Kits");
	model.addAttribute("direct_name","Fred");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



//////////////////////////////////filters
@GetMapping("/Shaw_lp")
public String Shaw_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}



@GetMapping("/Shaw_hp")
public String Shaw_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Shaw_ni")
public String Shaw_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Shaw_12")
public String Shaw_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}

@GetMapping("/Shaw_24")
public String Shaw_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Shaw_36")
public String Shaw_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}

//////////////////////////////////additional filters
@GetMapping("/Shaw_lp_12")
public String Shaw_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Shaw_lp_24")
public String Shaw_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}



@GetMapping("/Shaw_lp_36")
public String Shaw_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Shaw_hp_12")
public String Shaw_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}







@GetMapping("/Shaw_hp_24")
public String Shaw_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}




@GetMapping("/Shaw_hp_36")
public String Shaw_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}



@GetMapping("/Shaw_ni_12")
public String Shaw_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}







@GetMapping("/Shaw_ni_24")
public String Shaw_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Shaw_ni_36")
public String Shaw_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Luke Shaw Manchester United Kits");
	model.addAttribute("direct_name","Luke Shaw");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




//////////////////////////////////filters
@GetMapping("/Maguire_lp")
public String Maguire_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Maguire_hp")
public String Maguire_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Maguire_ni")
public String Maguire_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Maguire_12")
public String Maguire_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}

@GetMapping("/Maguire_24")
public String Maguire_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Maguire_36")
public String Maguire_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}

//////////////////////////////////additional filters
@GetMapping("/Maguire_lp_12")
public String Maguire_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}


}



@GetMapping("/Maguire_lp_24")
public String Maguire_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Maguire_lp_36")
public String Maguire_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}


@GetMapping("/Maguire_hp_12")
public String Maguire_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







@GetMapping("/Maguire_hp_24")
public String Maguire_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Maguire_hp_36")
public String Maguire_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/Maguire_ni_12")
public String Maguire_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}







@GetMapping("/Maguire_ni_24")
public String Maguire_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}




@GetMapping("/Maguire_ni_36")
public String Maguire_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Harry Maguire Manchester United Kits");
	model.addAttribute("direct_name","Harry Maguire");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



 

//////////////////////////////////filters
@GetMapping("/Van De Beek_lp")
public String Van_De_Beek_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}



@GetMapping("/Van De Beek_hp")
public String Van_De_Beek_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Van De Beek_ni")
public String Van_De_Beek_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Van De Beek_12")
public String Van_De_Beek_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}

@GetMapping("/Van De Beek_24")
public String Van_De_Beek_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Van De Beek_36")
public String Van_De_Beek_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}

//////////////////////////////////additional filters
@GetMapping("/Van De Beek_lp_12")
public String Van_De_Beek_lp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}



@GetMapping("/Van De Beek_lp_24")
public String Van_De_Beek_lp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}



@GetMapping("/Van De Beek_lp_36")
public String Van_De_Beek_lp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}


@GetMapping("/Van De Beek_hp_12")
public String Van_De_Beek_hp_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}







@GetMapping("/Van De Beek_hp_24")
public String Van_De_Beek_hp_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}




@GetMapping("/Van De Beek_hp_36")
public String Van_De_Beek_hp_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}



@GetMapping("/Van De Beek_ni_12")
public String Van_De_Beek_ni_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}







@GetMapping("/Van De Beek_ni_24")
public String Van_De_Beek_ni_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}
}




@GetMapping("/Van De Beek_ni_36")
public String Van_De_Beek_ni_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Donny Van De Beek Manchester United Kits");
	model.addAttribute("direct_name","Donny Van De Beek");
	List<Dis> pile =new ArrayList<Dis>();
	func_main(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "shop_by_pl";
	}

}



@GetMapping("/men_pl")
public String men_pl(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	sub_func(model,request,pile,6, 7,0);
	String p =(String) model.getAttribute("url");
	System.out.println(p);
	if(pile.size()==0) {
		if(p.contains("away")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}else if(p.contains("home")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}else if(p.contains("third")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}else if(p.contains("keeper")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}
		System.out.println("empty_pl_not");
		return "empty_pl_not";
	}else if(pile.size()<3){
		if(p.contains("away")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}else if(p.contains("home")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}else if(p.contains("third")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}else if(p.contains("keeper")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}
		System.out.println("odd");
		return "odd_pl_not";
	}else {
		if(p.contains("away")) {
			System.out.println("shop_by_pl");
			return "players_kit_type";
		}else if(p.contains("home")) {
			System.out.println("shop_by_pl");
			return "players_kit_type";
		}else if(p.contains("third")) {
			System.out.println("shop_by_pl");
			return "players_kit_type";
		}else if(p.contains("keeper")) {
			System.out.println("players_kit_type");
			return "players_kit_type";
		}
		System.out.println("shop_by_pl");
		return "shop_by_pl";
	}
}


@GetMapping("/women_pl")
public String women_pl(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	sub_func(model,request,pile,2,null,null);
	String p =(String) model.getAttribute("url");
	System.out.println(p);
	if(pile.size()==0) {
		if(p.contains("away")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}else if(p.contains("home")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}else if(p.contains("third")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}else if(p.contains("keeper")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}
		System.out.println("empty_pl_not");
		return "empty_pl_not";
	}else if(pile.size()<3){
		if(p.contains("away")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}else if(p.contains("home")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}else if(p.contains("third")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}else if(p.contains("keeper")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}
		System.out.println("odd");
		return "odd_pl_not";
	}else {
		if(p.contains("away")) {
			System.out.println("shop_by_pl");
			return "players_kit_type";
		}else if(p.contains("home")) {
			System.out.println("shop_by_pl");
			return "players_kit_type";
		}else if(p.contains("third")) {
			System.out.println("shop_by_pl");
			return "players_kit_type";
		}else if(p.contains("keeper")) {
			System.out.println("players_kit_type");
			return "players_kit_type";
		}
		System.out.println("shop_by_pl");
		return "shop_by_pl";
	}

}

@GetMapping("/kids_pl")
public String kids_pl(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	sub_func(model,request,pile,3,null,null);
	String p =(String) model.getAttribute("url");
	System.out.println(p);
	if(pile.size()==0) {
		if(p.contains("away")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}else if(p.contains("home")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}else if(p.contains("third")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}else if(p.contains("keeper")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}
		System.out.println("empty_pl_not");
		return "empty_pl_not";
	}else if(pile.size()<3){
		if(p.contains("away")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}else if(p.contains("home")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}else if(p.contains("third")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}else if(p.contains("keeper")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}
		System.out.println("odd");
		return "odd_pl_not";
	}else {
		if(p.contains("away")) {
			System.out.println("shop_by_pl");
			return "players_kit_type";
		}else if(p.contains("home")) {
			System.out.println("shop_by_pl");
			return "players_kit_type";
		}else if(p.contains("third")) {
			System.out.println("shop_by_pl");
			return "players_kit_type";
		}else if(p.contains("keeper")) {
			System.out.println("players_kit_type");
			return "players_kit_type";
		}
		System.out.println("shop_by_pl");
		return "shop_by_pl";
	}

}

	

@GetMapping("/baby_pl")
public String baby_pl(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	sub_func(model,request,pile,4,null,null);
	String p =(String) model.getAttribute("url");
	System.out.println(p);
	if(pile.size()==0) {
		if(p.contains("away")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}else if(p.contains("home")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}else if(p.contains("third")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}else if(p.contains("keeper")) {
			System.out.println("empty_pl");
			return "empty_pl";
		}
		System.out.println("empty_pl_not");
		return "empty_pl_not";
	}else if(pile.size()<3){
		if(p.contains("away")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}else if(p.contains("home")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}else if(p.contains("third")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}else if(p.contains("keeper")) {
			System.out.println("odd_pl");
			return "odd_pl";
		}
		System.out.println("odd");
		return "odd_pl_not";
	}else {
		if(p.contains("away")) {
			System.out.println("shop_by_pl");
			return "players_kit_type";
		}else if(p.contains("home")) {
			System.out.println("shop_by_pl");
			return "players_kit_type";
		}else if(p.contains("third")) {
			System.out.println("shop_by_pl");
			return "players_kit_type";
		}else if(p.contains("keeper")) {
			System.out.println("players_kit_type");
			return "players_kit_type";
		}
		System.out.println("shop_by_pl");
		return "shop_by_pl";
	}
}





@GetMapping("/home_pl")
public String home_pl(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	kit_type_func(model,request,pile,"home_kits","home");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "players_kit_type";
	}

}



@GetMapping("/away_pl")
public String away_pl(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	kit_type_func(model,request,pile,"away_kits","away");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "players_kit_type";
	}

}




@GetMapping("/third_pl")
public String third_pl(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	kit_type_func(model,request,pile,"third_kits","third");
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "players_kit_type";
	}

}


@GetMapping("/func_pl_lp")
public String func_pl_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	sub_func_kit(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "players_kit_type";
	}

}


@GetMapping("/func_pl_hp")
public String func_pl_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	sub_func_kit(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "players_kit_type";
	}

}



@GetMapping("/func_pl_ni")
public String func_pl_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	sub_func_kit(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "players_kit_type";
	}

}



@GetMapping("/func_pl_12")
public String func_pl_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	sub_func_kit(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "players_kit_type";
	}

}


@GetMapping("/func_pl_24")
public String func_pl_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	sub_func_kit(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "players_kit_type";
	}

}



@GetMapping("/func_pl_36")
public String func_pl_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	sub_func_kit(model,request,pile);
	if(pile.size()==0) {
		System.out.println("empty");
		return "empty";
	}else if(pile.size()<3){
		System.out.println("odd");
		return "odd";
	}else {
		System.out.println("away");
		return "players_kit_type";
	}

}

//controller ends
}