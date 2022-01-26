package com.manutd.demo;



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
import java.util.Comparator;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;



@Controller


public class PlayersController {



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
	

	
	
	
	
	public void info_out_filter_size(Connection connection,String DB_URL,String User,String Pass,List<Dis> pile,Model model,HttpServletRequest request) throws SQLException, UnsupportedEncodingException {
		String page=request.getHeader("Referer");
		String heq=URLDecoder.decode(page, "UTF-8");
		System.out.println(page);
//		
		ArrayList<Integer> pl_id=new ArrayList<Integer>(); 
		ArrayList<String> pl_name =new ArrayList<String>();
		System.out.println(pl_name);
		System.out.println(pl_id);
///////////Checking for _away
		if(heq.contains("_away")) {
			String tab_name="away_kits";
			String tab_sub_name="away";
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
///////////Checking for _away_pl_name_lp			
					if(page.contains("_lp")) {
						connection = DriverManager.getConnection(DB_URL,User,Pass);
						PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						prepare.setInt(1, pl_id.get(0).intValue());
						ResultSet result=prepare.executeQuery();
						
						
						if(!result.next()) {
							System.out.println("Is empty!");
							
						}else{
							result.beforeFirst();

							while(result.next()) {
								String src=result.getString(tab_sub_name+"_img_link");
								String alt=result.getString(tab_sub_name+"_kit_descrp");
								Integer pr=result.getInt("price");
								Integer pr_coin=result.getInt("price_coin");
								
								
								pile.add(new Dis(src, alt, pr, pr_coin));
								
						
							}
//							
						}
						
						
									
						
						int adad=0;
						
						if(pile.isEmpty()) {
							System.out.println("Do nothing is empty!");
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//							model.addAttribute("img_source", pile);
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("url","_away");
							
						}else if(pile.size()<3){
							System.out.println(pile.get(0).alt_arr);
							System.out.println("Do nothing!");
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("url","_away");
					     	
						}else {
							System.out.println(pile.get(0).alt_arr);
							if(pile.size()%3!=0) {
//								System.out.println("Has");
								adad=pile.size()%3;
								System.out.println(adad);
//								System.out.println("an");
									for(int i=0;i<adad;i++) {
										System.out.println("!");
										pile.remove(i);
										
									}
							}
							
							System.out.println(pl_name.get(0));
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("mult",pile.size()-1);
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					    	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("url","_away");
					     	
						}
						
						System.out.println(adad);
						System.out.println("\n");

						
						
		
						connection.close();
						prepare.close();
						result.close();
				
///////////Checking for _away_pl_name_hp						
					}else if(page.contains("_hp")) {
						connection = DriverManager.getConnection(DB_URL,User,Pass);
						PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						prepare.setInt(1, pl_id.get(0).intValue());
						ResultSet result=prepare.executeQuery();
						
						
						if(!result.next()) {
							System.out.println("Is empty!");
							
						}else{
							result.beforeFirst();

							while(result.next()) {
								String src=result.getString(tab_sub_name+"_img_link");
								String alt=result.getString(tab_sub_name+"_kit_descrp");
								Integer pr=result.getInt("price");
								Integer pr_coin=result.getInt("price_coin");
								
								
								pile.add(new Dis(src, alt, pr, pr_coin));
								
						
							}
//							
						}
						
					
						
						int adad=0;
						
						if(pile.isEmpty()) {
							System.out.println("Do nothing is empty!");
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//							model.addAttribute("img_source", pile);
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("url","_away");
							
						}else if(pile.size()<3){
							System.out.println(pile.get(0).alt_arr);
							System.out.println("Do nothing!");
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("url","_away");
					     	
						}else {
							System.out.println(pile.get(0).alt_arr);
							if(pile.size()%3!=0) {
//								System.out.println("Has");
								adad=pile.size()%3;
								System.out.println(adad);
//								System.out.println("an");
									for(int i=0;i<adad;i++) {
										System.out.println("!");
										pile.remove(i);
										
									}
							}
							
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("mult",pile.size()-1);
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("url","_away");
					     	
						}
						
						System.out.println(adad);
						System.out.println("\n");

						
						
		
						connection.close();
						prepare.close();
						result.close();
						
						
///////////Checking for _away_pl_name_ni
					}else if(page.contains("_ni")) {
							connection = DriverManager.getConnection(DB_URL,User,Pass);
							PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
							prepare.setInt(1, pl_id.get(0).intValue());
							ResultSet result=prepare.executeQuery();
							
							
							if(!result.next()) {
								System.out.println("Is empty!");
								
							}else{
								result.beforeFirst();
	
								while(result.next()) {
									String src=result.getString(tab_sub_name+"_img_link");
									String alt=result.getString(tab_sub_name+"_kit_descrp");
									Integer pr=result.getInt("price");
									Integer pr_coin=result.getInt("price_coin");
									
									
									pile.add(new Dis(src, alt, pr, pr_coin));
									
							
								}
	//							
							}
							
							
										
							
							int adad=0;
							
							if(pile.isEmpty()) {
								System.out.println("Do nothing is empty!");
								
								System.out.println(pile.size());
	//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
	//							model.addAttribute("img_source", pile);
	//					     	model.addAttribute("width",pile.size());
						     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
						     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
						     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
						     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
						     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
						     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
						     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
						     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
						     	model.addAttribute("url","_away");
								
							}else if(pile.size()<3){
								System.out.println(pile.get(0).alt_arr);
								System.out.println("Do nothing!");
								
								System.out.println(pile.size());
	//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
								model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
	//					     	model.addAttribute("width",pile.size());
						     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
						     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
						     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
						     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
						     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
						     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
						     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
						     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
						     	model.addAttribute("url","_away");
						     	
							}else {
								System.out.println(pile.get(0).alt_arr);
								if(pile.size()%3!=0) {
	//								System.out.println("Has");
									adad=pile.size()%3;
									System.out.println(adad);
	//								System.out.println("an");
										for(int i=0;i<adad;i++) {
											System.out.println("!");
											pile.remove(i);
											
										}
								}
								
								
								System.out.println(pile.size());
	//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
								model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
	//					     	model.addAttribute("width",pile.size());
						     	model.addAttribute("mult",pile.size()-1);
						     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
						     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
						     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
						     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
						     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
						     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
						     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
						     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
						     	model.addAttribute("url","_away");
						     	
							}
							
							System.out.println(adad);
							System.out.println("\n");
	
							
							
			
							connection.close();
							prepare.close();
							result.close();
							
					}
///////////Checking for _keeper		
	    }else if(heq.contains("_keeper")) {
			String tab_name="keeper_kits";
			String tab_sub_name="keeper";
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement slt=connection.prepareStatement("SELECT * FROM players",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rst=slt.executeQuery();
				while(rst.next()) {
					String player_name=rst.getString("player_name");
					Integer player_id=rst.getInt("player_id");
					if(heq.contains(player_name)==true) {
						pl_id.add(player_id);
						pl_name.add(player_name);
						break;
					}
				}
			slt.close();
			rst.close();
			connection.close();
///////////Checking for _keeper_pl_name_lp
			if(page.contains("_lp")) {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0));
				ResultSet result=prepare.executeQuery();
				
				
				if(!result.next()) {
					System.out.println("Is empty!");
					
				}else{
					result.beforeFirst();

					while(result.next()) {
						String src=result.getString(tab_sub_name+"_img_link");
						String alt=result.getString(tab_sub_name+"_kit_descrp");
						Integer pr=result.getInt("price");
						Integer pr_coin=result.getInt("price_coin");
						
						
						pile.add(new Dis(src, alt, pr, pr_coin));
						
				
					}
//					
				}
				
				
							
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("url","_keeper");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("url","_keeper");
			     	
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int i=0;i<adad;i++) {
								System.out.println("!");
								pile.remove(i);
								
							}
					}
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("url","_keeper");
			     	
				}
				
				System.out.println(adad);
				System.out.println("\n");

				
				

				connection.close();
				prepare.close();
				result.close();
		
///////////Checking for _keeper_pl_name_hp						
			}else if(page.contains("_hp")) {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0));
				ResultSet result=prepare.executeQuery();
				
				
				if(!result.next()) {
					System.out.println("Is empty!");
					
				}else{
					result.beforeFirst();

					while(result.next()) {
						String src=result.getString(tab_sub_name+"_img_link");
						String alt=result.getString(tab_sub_name+"_kit_descrp");
						Integer pr=result.getInt("price");
						Integer pr_coin=result.getInt("price_coin");
						
						
						pile.add(new Dis(src, alt, pr, pr_coin));
						
				
					}
//					
				}
				
				
							
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("url","_keeper");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("url","_keeper");
			     	
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int i=0;i<adad;i++) {
								System.out.println("!");
								pile.remove(i);
								
							}
					}
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("url","_keeper");
			     	
				}
				
				System.out.println(adad);
				System.out.println("\n");

				
				

				connection.close();
				prepare.close();
				result.close();
				
				
///////////Checking for _keeper_pl_name_ni
			}else if(page.contains("_ni")) {
					connection = DriverManager.getConnection(DB_URL,User,Pass);
					PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					prepare.setInt(1, pl_id.get(0));
					ResultSet result=prepare.executeQuery();
					
					
					if(!result.next()) {
						System.out.println("Is empty!");
						
					}else{
						result.beforeFirst();

						while(result.next()) {
							String src=result.getString(tab_sub_name+"_img_link");
							String alt=result.getString(tab_sub_name+"_kit_descrp");
							Integer pr=result.getInt("price");
							Integer pr_coin=result.getInt("price_coin");
							
							
							pile.add(new Dis(src, alt, pr, pr_coin));
							
					
						}
//							
					}
					
					
								
					
					int adad=0;
					
					if(pile.isEmpty()) {
						System.out.println("Do nothing is empty!");
						
						System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//							model.addAttribute("img_source", pile);
//					     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("url","_keeper");
						
					}else if(pile.size()<3){
						System.out.println(pile.get(0).alt_arr);
						System.out.println("Do nothing!");
						
						System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//					     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("url","_keeper");
				     	
					}else {
						System.out.println(pile.get(0).alt_arr);
						if(pile.size()%3!=0) {
//								System.out.println("Has");
							adad=pile.size()%3;
							System.out.println(adad);
//								System.out.println("an");
								for(int i=0;i<adad;i++) {
									System.out.println("!");
									pile.remove(i);
									
								}
						}
						
						
						System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//					     	model.addAttribute("width",pile.size());
				     	model.addAttribute("mult",pile.size()-1);
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("url","_keeper");
				     	
					}
					
					System.out.println(adad);
					System.out.println("\n");

					
					
	
					connection.close();
					prepare.close();
					result.close();
					
			}
///////////Checking for _home	    
	    }else if(heq.contains("_home")) {
	    	String tab_name="home_kits";
			String tab_sub_name="home";
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement slt=connection.prepareStatement("SELECT * FROM players",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rst=slt.executeQuery();
				while(rst.next()) {
					String player_name=rst.getString("player_name");
					Integer player_id=rst.getInt("player_id");
					if(heq.contains(player_name)==true) {
						pl_id.add(player_id);
						pl_name.add(player_name);
						break;
					}
				}
			slt.close();
			rst.close();
			connection.close();
	///////////Checking for _home_pl_name_lp
				if(page.contains("_lp")) {
					connection = DriverManager.getConnection(DB_URL,User,Pass);
					PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					prepare.setInt(1, pl_id.get(0));
					ResultSet result=prepare.executeQuery();
					
					
					if(!result.next()) {
						System.out.println("Is empty!");
						
					}else{
						result.beforeFirst();

						while(result.next()) {
							String src=result.getString(tab_sub_name+"_img_link");
							String alt=result.getString(tab_sub_name+"_kit_descrp");
							Integer pr=result.getInt("price");
							Integer pr_coin=result.getInt("price_coin");
							
							
							pile.add(new Dis(src, alt, pr, pr_coin));
							
					
						}
//						
					}
					
					
								
					
					int adad=0;
					
					if(pile.isEmpty()) {
						System.out.println("Do nothing is empty!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//						model.addAttribute("img_source", pile);
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("url","_home");
						
					}else if(pile.size()<3){
						System.out.println(pile.get(0).alt_arr);
						System.out.println("Do nothing!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("url","_home");
				     	
					}else {
						System.out.println(pile.get(0).alt_arr);
						if(pile.size()%3!=0) {
//							System.out.println("Has");
							adad=pile.size()%3;
							System.out.println(adad);
//							System.out.println("an");
								for(int i=0;i<adad;i++) {
									System.out.println("!");
									pile.remove(i);
									
								}
						}
						
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("mult",pile.size()-1);
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("url","_home");
				     	
					}
					
					System.out.println(adad);
					System.out.println("\n");

					
					

					connection.close();
					prepare.close();
					result.close();
			
	///////////Checking for _home_pl_name_hp						
				}else if(page.contains("_hp")) {
					connection = DriverManager.getConnection(DB_URL,User,Pass);
					PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					prepare.setInt(1, pl_id.get(0));
					ResultSet result=prepare.executeQuery();
					
					
					if(!result.next()) {
						System.out.println("Is empty!");
						
					}else{
						result.beforeFirst();

						while(result.next()) {
							String src=result.getString(tab_sub_name+"_img_link");
							String alt=result.getString(tab_sub_name+"_kit_descrp");
							Integer pr=result.getInt("price");
							Integer pr_coin=result.getInt("price_coin");
							
							
							pile.add(new Dis(src, alt, pr, pr_coin));
							
					
						}
//						
					}
					
					
								
					
					int adad=0;
					
					if(pile.isEmpty()) {
						System.out.println("Do nothing is empty!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//						model.addAttribute("img_source", pile);
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("url","_home");
						
					}else if(pile.size()<3){
						System.out.println(pile.get(0).alt_arr);
						System.out.println("Do nothing!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("url","_home");
				     	
					}else {
						System.out.println(pile.get(0).alt_arr);
						if(pile.size()%3!=0) {
//							System.out.println("Has");
							adad=pile.size()%3;
							System.out.println(adad);
//							System.out.println("an");
								for(int i=0;i<adad;i++) {
									System.out.println("!");
									pile.remove(i);
									
								}
						}
						
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("mult",pile.size()-1);
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("url","_home");
				     	
					}
					
					System.out.println(adad);
					System.out.println("\n");

					
					

					connection.close();
					prepare.close();
					result.close();
					
					
///////////Checking for _home_pl_name_ni
				}else if(page.contains("_ni")) {
						connection = DriverManager.getConnection(DB_URL,User,Pass);
						PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						prepare.setInt(1, pl_id.get(0));
						ResultSet result=prepare.executeQuery();
						
						
						if(!result.next()) {
							System.out.println("Is empty!");
							
						}else{
							result.beforeFirst();

							while(result.next()) {
								String src=result.getString(tab_sub_name+"_img_link");
								String alt=result.getString(tab_sub_name+"_kit_descrp");
								Integer pr=result.getInt("price");
								Integer pr_coin=result.getInt("price_coin");
								
								
								pile.add(new Dis(src, alt, pr, pr_coin));
								
						
							}
//								
						}
						
						
									
						
						int adad=0;
						
						if(pile.isEmpty()) {
							System.out.println("Do nothing is empty!");
							
							System.out.println(pile.size());
//								pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//								model.addAttribute("img_source", pile);
//						     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("url","_home");
							
						}else if(pile.size()<3){
							System.out.println(pile.get(0).alt_arr);
							System.out.println("Do nothing!");
							
							System.out.println(pile.size());
//								pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//						     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("url","_home");
					     	
						}else {
							System.out.println(pile.get(0).alt_arr);
							if(pile.size()%3!=0) {
//									System.out.println("Has");
								adad=pile.size()%3;
								System.out.println(adad);
//									System.out.println("an");
									for(int i=0;i<adad;i++) {
										System.out.println("!");
										pile.remove(i);
										
									}
							}
							
							
							System.out.println(pile.size());
//								pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//						     	model.addAttribute("width",pile.size());
					     	model.addAttribute("mult",pile.size()-1);
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("url","_home");
					     	
						}
						
						System.out.println(adad);
						System.out.println("\n");

						
						
		
						connection.close();
						prepare.close();
						result.close();
						
				}
///////////Checking for _third
	    }else if(heq.contains("_third")) {
	    	String tab_name="third_kits";
			String tab_sub_name="third";
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement slt=connection.prepareStatement("SELECT * FROM players",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rst=slt.executeQuery();
				while(rst.next()) {
					String player_name=rst.getString("player_name");
					Integer player_id=rst.getInt("player_id");
					if(heq.contains(player_name)==true) {
						pl_id.add(player_id);
						pl_name.add(player_name);
						break;
					}
				}
			slt.close();
			rst.close();
			connection.close();
///////////Checking for _third_pl_name_lp
			if(page.contains("_lp")) {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0));
				ResultSet result=prepare.executeQuery();
				
				
				if(!result.next()) {
					System.out.println("Is empty!");
					
				}else{
					result.beforeFirst();

					while(result.next()) {
						String src=result.getString(tab_sub_name+"_img_link");
						String alt=result.getString(tab_sub_name+"_kit_descrp");
						Integer pr=result.getInt("price");
						Integer pr_coin=result.getInt("price_coin");
						
						
						pile.add(new Dis(src, alt, pr, pr_coin));
						
				
					}
//					
				}
				
				
							
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("url","_third");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("url","_third");
			     	
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int i=0;i<adad;i++) {
								System.out.println("!");
								pile.remove(i);
								
							}
					}
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("url","_third");
			     	
				}
				
				System.out.println(adad);
				System.out.println("\n");

				
				

				connection.close();
				prepare.close();
				result.close();
		
///////////Checking for _third_pl_name_hp						
			}else if(page.contains("_hp")) {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0));
				ResultSet result=prepare.executeQuery();
				
				
				if(!result.next()) {
					System.out.println("Is empty!");
					
				}else{
					result.beforeFirst();

					while(result.next()) {
						String src=result.getString(tab_sub_name+"_img_link");
						String alt=result.getString(tab_sub_name+"_kit_descrp");
						Integer pr=result.getInt("price");
						Integer pr_coin=result.getInt("price_coin");
						
						
						pile.add(new Dis(src, alt, pr, pr_coin));
						
				
					}
//					
				}
				
				
							
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("url","_third");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("url","_third");
			     	
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int i=0;i<adad;i++) {
								System.out.println("!");
								pile.remove(i);
								
							}
					}
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("url","_third");;
			     	
				}
				
				System.out.println(adad);
				System.out.println("\n");

				
				

				connection.close();
				prepare.close();
				result.close();
				
				
///////////Checking for _third_pl_name_ni
			}else if(page.contains("_ni")) {
					connection = DriverManager.getConnection(DB_URL,User,Pass);
					PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					prepare.setInt(1, pl_id.get(0));
					ResultSet result=prepare.executeQuery();
					
					
					if(!result.next()) {
						System.out.println("Is empty!");
						
					}else{
						result.beforeFirst();

						while(result.next()) {
							String src=result.getString(tab_sub_name+"_img_link");
							String alt=result.getString(tab_sub_name+"_kit_descrp");
							Integer pr=result.getInt("price");
							Integer pr_coin=result.getInt("price_coin");
							
							
							pile.add(new Dis(src, alt, pr, pr_coin));
							
					
						}
//							
					}
					
					
								
					
					int adad=0;
					
					if(pile.isEmpty()) {
						System.out.println("Do nothing is empty!");
						
						System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//							model.addAttribute("img_source", pile);
//					     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("url","_third");
						
					}else if(pile.size()<3){
						System.out.println(pile.get(0).alt_arr);
						System.out.println("Do nothing!");
						
						System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//					     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("url","_third");
				     	
					}else {
						System.out.println(pile.get(0).alt_arr);
						if(pile.size()%3!=0) {
//								System.out.println("Has");
							adad=pile.size()%3;
							System.out.println(adad);
//								System.out.println("an");
								for(int i=0;i<adad;i++) {
									System.out.println("!");
									pile.remove(i);
									
								}
						}
						
						
						System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//					     	model.addAttribute("width",pile.size());
				     	model.addAttribute("mult",pile.size()-1);
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("url","_third");
				     	
					}
					
					System.out.println(adad);
					System.out.println("\n");

					
					
	
					connection.close();
					prepare.close();
					result.close();
					
			}
//////////////////////////END
	    }
		
		
						
	}
		
		

	
	
	
	
	
	
	
	
	
	
	public boolean db_empty(Connection connection,boolean is_empty,String DB_URL,String User,String Pass,String req) throws UnsupportedEncodingException {
		String heq=URLDecoder.decode(req, "UTF-8");
		
		if(heq.contains("away")) {
			String tab_name="away_kits";
			
			try {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement slt=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rst=slt.executeQuery();
				
					if (!rst.next()){
						is_empty=true;
//					Database is empty
						}
						
				slt.close();
				rst.close();
			}catch(SQLException w) {
				w.printStackTrace();
			}
		}else if(heq.contains("keeper")) {
			String tab_name="keeper_kits";
			
			try {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement slt=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rst=slt.executeQuery();
				
					if (!rst.next()){
						is_empty=true;
//					Database is empty
						}
						
				slt.close();
				rst.close();
			}catch(SQLException w) {
				w.printStackTrace();
			}
		}else if(heq.contains("home")) {
			String tab_name="home_kits";
			
			try {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement slt=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rst=slt.executeQuery();
				
					if (!rst.next()){
						is_empty=true;
//					Database is empty
						}
						
				slt.close();
				rst.close();
			}catch(SQLException w) {
				w.printStackTrace();
			}
		}else if(heq.contains("third")) {
			String tab_name="third_kits";
			
			try {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement slt=connection.prepareStatement("SELECT * FROM "+tab_name,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rst=slt.executeQuery();
				
					if (!rst.next()){
						is_empty=true;
//					Database is empty
						}
						
				slt.close();
				rst.close();
			}catch(SQLException w) {
				w.printStackTrace();
			}
		}
		
		
		
		return is_empty;
	}
	
	
	public void keeper_kit(Connection connection,String DB_URL,String User,String Pass,List<Dis> pile,Model model,Integer player_num,String pl_name) throws SQLException {
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
		}
		
		System.out.println("Testing connection to Postgresql JDBC");
		
		
		String tab_name="keeper_kits";
		String tab_sub_name="keeper";
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, player_num);
		ResultSet result=prepare.executeQuery();

		if(!result.next()) {
			System.out.println("Is empty!");
			
		}else{
			result.beforeFirst();

			while(result.next()) {
				String src=result.getString(tab_sub_name+"_img_link");
				String alt=result.getString(tab_sub_name+"_kit_descrp");
				Integer pr=result.getInt("price");
				Integer pr_coin=result.getInt("price_coin");
				
				
				pile.add(new Dis(src, alt, pr, pr_coin));
				
		
			}
//			
		}
		
		
					
		
		int adad=0;
		
		if(pile.isEmpty()) {
			System.out.println("Do nothing is empty!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price","_"+pl_name+"_lp");
	     	model.addAttribute("high_price","_"+pl_name+"_hp");
	     	model.addAttribute("new_item","_"+pl_name+"_ni");
	     	model.addAttribute("size_one","_"+pl_name+"_12");
	     	model.addAttribute("size_two","_"+pl_name+"_24");
	     	model.addAttribute("size_three","_"+pl_name+"_36");
	     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
	     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name);
	     	model.addAttribute("url","_keeper");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price","_"+pl_name+"_lp");
	     	model.addAttribute("high_price","_"+pl_name+"_hp");
	     	model.addAttribute("new_item","_"+pl_name+"_ni");
	     	model.addAttribute("size_one","_"+pl_name+"_12");
	     	model.addAttribute("size_two","_"+pl_name+"_24");
	     	model.addAttribute("size_three","_"+pl_name+"_36");
	     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
	     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name);
	     	model.addAttribute("url","_keeper");
	     	
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
//	     	model.addAttribute("width",pile.size());
	     	model.addAttribute("mult",pile.size()-1);
	     	model.addAttribute("low_price","_"+pl_name+"_lp");
	     	model.addAttribute("high_price","_"+pl_name+"_hp");
	     	model.addAttribute("new_item","_"+pl_name+"_ni");
	     	model.addAttribute("size_one","_"+pl_name+"_12");
	     	model.addAttribute("size_two","_"+pl_name+"_24");
	     	model.addAttribute("size_three","_"+pl_name+"_36");
	     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
	     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name);
	     	model.addAttribute("url","_keeper");
	     	
		}
		
		System.out.println(adad);
		System.out.println("\n");
		
		
		
		connection.close();
		prepare.close();
		result.close();

}

		
	
	
	
	
	public void info_out_size(Connection connection,String DB_URL,String User,String Pass,List<Dis> pile,Model model,HttpServletRequest request) throws SQLException, UnsupportedEncodingException {
		String page=request.getHeader("Referer");
		String heq=URLDecoder.decode(page, "UTF-8");
		System.out.println(page);
//		
		ArrayList<Integer> pl_id=new ArrayList<Integer>(); 
		ArrayList<String> pl_name =new ArrayList<String>();
		System.out.println(pl_name);
		System.out.println(pl_id);
///////////Checking for _away
		if(heq.contains("_away")) {
			String tab_name="away_kits";
			String tab_sub_name="away";
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
			
///////////Checking for _away_pl_name_lp_12 or _lp_24 or _lp_36
			if(heq.contains("_lp_12") || heq.contains("_lp_24") || heq.contains("_lp_36")) {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0).intValue());
				ResultSet result=prepare.executeQuery();
				
				
				if(!result.next()) {
					System.out.println("Is empty!");
					
				}else{
					result.beforeFirst();

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
//					
				}
				
			
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("url","_away");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("url","_away");
			     	
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int i=0;i<adad;i++) {
								System.out.println("!");
								pile.remove(i);
								
							}
					}
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("url","_away");
			     	
				}
				
				System.out.println(adad);
				System.out.println("\n");

				
				

				connection.close();
				prepare.close();
				result.close();				
///////////Checking for _away_pl_name_hp_12 or _hp_24 or _hp_36				
			}else if(heq.contains("_hp_12") || heq.contains("_hp_24") || heq.contains("_hp_36")){
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0).intValue());
				ResultSet result=prepare.executeQuery();
				
				
				if(!result.next()) {
					System.out.println("Is empty!");
					
				}else{
					result.beforeFirst();

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
//					
				}
				
			
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("url","_away");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("url","_away");
			     	
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int i=0;i<adad;i++) {
								System.out.println("!");
								pile.remove(i);
								
							}
					}
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("url","_away");
			     	
				}
				
				System.out.println(adad);
				System.out.println("\n");

				
				

				connection.close();
				prepare.close();
				result.close();
///////////Checking for _away_pl_name_ni_12 or _ni_24 or _ni_36
			}else if(heq.contains("_ni_12") || heq.contains("_ni_24") || heq.contains("_ni_36")) {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0).intValue());
				ResultSet result=prepare.executeQuery();
				
				
				if(!result.next()) {
					System.out.println("Is empty!");
					
				}else{
					result.beforeFirst();

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
//					
				}
				
			
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("url","_away");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("url","_away");
			     	
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int i=0;i<adad;i++) {
								System.out.println("!");
								pile.remove(i);
								
							}
					}
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("url","_away");
			     	
				}
				
				System.out.println(adad);
				System.out.println("\n");

				
				

				connection.close();
				prepare.close();
				result.close();
///////////Checking for _away_pl_name_12 or _24 or _36
			}else if(heq.contains("_12") || heq.contains("_24") || heq.contains("_36")) {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0).intValue());
				ResultSet result=prepare.executeQuery();
				
				
				if(!result.next()) {
					System.out.println("Is empty!");
					
				}else{
					result.beforeFirst();

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
//					
				}
				
			
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("url","_away");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("url","_away");
			     	
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int i=0;i<adad;i++) {
								System.out.println("!");
								pile.remove(i);
								
							}
					}
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("url","_away");
			     	
				}
				
				System.out.println(adad);
				System.out.println("\n");

				
				

				connection.close();
				prepare.close();
				result.close();

			}
///////////Checking for _keeper
		}else if(heq.contains("_keeper")) {
			String tab_name="keeper_kits";
			String tab_sub_name="keeper";
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
///////////Checking for _keeper_pl_name_lp_12 or _lp_24 or lp_36
			if(heq.contains("_lp_12") || heq.contains("_lp_24") || heq.contains("_lp_36")) {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0).intValue());
				ResultSet result=prepare.executeQuery();
				
				
				if(!result.next()) {
					System.out.println("Is empty!");
					
				}else{
					result.beforeFirst();

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
//					
				}
				
			
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("url","_keeper");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("url","_keeper");
			     	
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int i=0;i<adad;i++) {
								System.out.println("!");
								pile.remove(i);
								
							}
					}
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("url","_keeper");
			     	
				}
				
				System.out.println(adad);
				System.out.println("\n");

				
				

				connection.close();
				prepare.close();
				result.close();				
///////////Checking for _keeper_pl_name_hp_12 or _hp_24 or _hp_36				
			}else if(heq.contains("_hp_12") || heq.contains("_hp_24") || heq.contains("_hp_36")){
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0).intValue());
				ResultSet result=prepare.executeQuery();
				
				
				if(!result.next()) {
					System.out.println("Is empty!");
					
				}else{
					result.beforeFirst();

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
//					
				}
				
			
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("url","_keeper");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("url","_keeper");
			     	
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int i=0;i<adad;i++) {
								System.out.println("!");
								pile.remove(i);
								
							}
					}
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("url","_keeper");
			     	
				}
				
				System.out.println(adad);
				System.out.println("\n");

				
				

				connection.close();
				prepare.close();
				result.close();
///////////Checking for _keeper_pl_name_ni_12 or _ni_24 or _ni_36
			}else if(heq.contains("_ni_12") || heq.contains("_ni_24") || heq.contains("_ni_36")) {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0).intValue());
				ResultSet result=prepare.executeQuery();
				
				
				if(!result.next()) {
					System.out.println("Is empty!");
					
				}else{
					result.beforeFirst();

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
//					
				}
				
			
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("url","_keeper");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("url","_keeper");
			     	
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int i=0;i<adad;i++) {
								System.out.println("!");
								pile.remove(i);
								
							}
					}
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("url","_keeper");
			     	
				}
				
				System.out.println(adad);
				System.out.println("\n");

				
				

				connection.close();
				prepare.close();
				result.close();
///////////Checking for _keeper_pl_name_12 or _24 or _36
			}else if(heq.contains("_12") || heq.contains("_24") || heq.contains("_36")) {
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				prepare.setInt(1, pl_id.get(0).intValue());
				ResultSet result=prepare.executeQuery();
				
				
				if(!result.next()) {
					System.out.println("Is empty!");
					
				}else{
					result.beforeFirst();

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
//					
				}
				
			
				
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("url","_keeper");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("url","_keeper");
			     	
				}else {
					System.out.println(pile.get(0).alt_arr);
					if(pile.size()%3!=0) {
//						System.out.println("Has");
						adad=pile.size()%3;
						System.out.println(adad);
//						System.out.println("an");
							for(int i=0;i<adad;i++) {
								System.out.println("!");
								pile.remove(i);
								
							}
					}
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
			     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
			     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
			     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
			     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
			     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
			     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
			     	model.addAttribute("url","_keeper");
			     	
				}
				
				System.out.println(adad);
				System.out.println("\n");

				
				

				connection.close();
				prepare.close();
				result.close();

			}
///////////Checking for _home			
		}else if(heq.contains("_home")) {
			String tab_name="home_kits";
			String tab_sub_name="home";
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
			
///////////Checking for _keeper_pl_name_lp_12 or _lp_24 or lp_36
				if(heq.contains("_lp_12") || heq.contains("_lp_24") || heq.contains("_lp_36")) {
					connection = DriverManager.getConnection(DB_URL,User,Pass);
					PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					prepare.setInt(1, pl_id.get(0).intValue());
					ResultSet result=prepare.executeQuery();
					
					
					if(!result.next()) {
						System.out.println("Is empty!");
						
					}else{
						result.beforeFirst();

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
//						
					}
					
				
					
					int adad=0;
					
					if(pile.isEmpty()) {
						System.out.println("Do nothing is empty!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//						model.addAttribute("img_source", pile);
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("url","_home");
						
					}else if(pile.size()<3){
						System.out.println(pile.get(0).alt_arr);
						System.out.println("Do nothing!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("url","_home");
				     	
					}else {
						System.out.println(pile.get(0).alt_arr);
						if(pile.size()%3!=0) {
//							System.out.println("Has");
							adad=pile.size()%3;
							System.out.println(adad);
//							System.out.println("an");
								for(int i=0;i<adad;i++) {
									System.out.println("!");
									pile.remove(i);
									
								}
						}
						
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("mult",pile.size()-1);
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("url","_home");
				     	
					}
					
					System.out.println(adad);
					System.out.println("\n");

					
					

					connection.close();
					prepare.close();
					result.close();				
	///////////Checking for _home_pl_name_hp_12 or _hp_24 or _hp_36				
				}else if(heq.contains("_hp_12") || heq.contains("_hp_24") || heq.contains("_hp_36")){
					connection = DriverManager.getConnection(DB_URL,User,Pass);
					PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					prepare.setInt(1, pl_id.get(0).intValue());
					ResultSet result=prepare.executeQuery();
					
					
					if(!result.next()) {
						System.out.println("Is empty!");
						
					}else{
						result.beforeFirst();

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
//						
					}
					
				
					
					int adad=0;
					
					if(pile.isEmpty()) {
						System.out.println("Do nothing is empty!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//						model.addAttribute("img_source", pile);
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("url","_home");
						
					}else if(pile.size()<3){
						System.out.println(pile.get(0).alt_arr);
						System.out.println("Do nothing!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("url","_home");
				     	
					}else {
						System.out.println(pile.get(0).alt_arr);
						if(pile.size()%3!=0) {
//							System.out.println("Has");
							adad=pile.size()%3;
							System.out.println(adad);
//							System.out.println("an");
								for(int i=0;i<adad;i++) {
									System.out.println("!");
									pile.remove(i);
									
								}
						}
						
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("mult",pile.size()-1);
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("url","_home");
				     	
					}
					
					System.out.println(adad);
					System.out.println("\n");

					
					

					connection.close();
					prepare.close();
					result.close();
	///////////Checking for _home_pl_name_ni_12 or _ni_24 or _ni_36
				}else if(heq.contains("_ni_12") || heq.contains("_ni_24") || heq.contains("_ni_36")) {
					connection = DriverManager.getConnection(DB_URL,User,Pass);
					PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					prepare.setInt(1, pl_id.get(0).intValue());
					ResultSet result=prepare.executeQuery();
					
					
					if(!result.next()) {
						System.out.println("Is empty!");
						
					}else{
						result.beforeFirst();

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
//						
					}
					
				
					
					int adad=0;
					
					if(pile.isEmpty()) {
						System.out.println("Do nothing is empty!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//						model.addAttribute("img_source", pile);
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("url","_home");
						
					}else if(pile.size()<3){
						System.out.println(pile.get(0).alt_arr);
						System.out.println("Do nothing!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile);
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("url","_home");
				     	
					}else {
						System.out.println(pile.get(0).alt_arr);
						if(pile.size()%3!=0) {
//							System.out.println("Has");
							adad=pile.size()%3;
							System.out.println(adad);
//							System.out.println("an");
								for(int i=0;i<adad;i++) {
									System.out.println("!");
									pile.remove(i);
									
								}
						}
						
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile);
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("mult",pile.size()-1);
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("url","_home");
				     	
					}
					
					System.out.println(adad);
					System.out.println("\n");

					
					

					connection.close();
					prepare.close();
					result.close();
	///////////Checking for _home_pl_name_12 or _24 or _36
				}else if(heq.contains("_12") || heq.contains("_24") || heq.contains("_36")) {
					connection = DriverManager.getConnection(DB_URL,User,Pass);
					PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					prepare.setInt(1, pl_id.get(0).intValue());
					ResultSet result=prepare.executeQuery();
					
					
					if(!result.next()) {
						System.out.println("Is empty!");
						
					}else{
						result.beforeFirst();

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
//						
					}
					
				
					
					int adad=0;
					
					if(pile.isEmpty()) {
						System.out.println("Do nothing is empty!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//						model.addAttribute("img_source", pile);
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("url","_home");
						
					}else if(pile.size()<3){
						System.out.println(pile.get(0).alt_arr);
						System.out.println("Do nothing!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile);
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("url","_home");
				     	
					}else {
						System.out.println(pile.get(0).alt_arr);
						if(pile.size()%3!=0) {
//							System.out.println("Has");
							adad=pile.size()%3;
							System.out.println(adad);
//							System.out.println("an");
								for(int i=0;i<adad;i++) {
									System.out.println("!");
									pile.remove(i);
									
								}
						}
						
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile);
//				     	model.addAttribute("width",pile.size());
				     	model.addAttribute("mult",pile.size()-1);
				     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
				     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
				     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
				     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
				     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
				     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
				     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
				     	model.addAttribute("url","_home");
				     	
					}
					
					System.out.println(adad);
					System.out.println("\n");

					
					

					connection.close();
					prepare.close();
					result.close();

				}
///////////Checking for _third
		}else if(heq.contains("_third")) {
			String tab_name="third_kits";
			String tab_sub_name="third";
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
///////////Checking for _third_pl_name_lp_12 or _lp_24 or lp_36
					if(heq.contains("_lp_12") || heq.contains("_lp_24") || heq.contains("_lp_36")) {
						connection = DriverManager.getConnection(DB_URL,User,Pass);
						PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						prepare.setInt(1, pl_id.get(0).intValue());
						ResultSet result=prepare.executeQuery();
						
						
						if(!result.next()) {
							System.out.println("Is empty!");
							
						}else{
							result.beforeFirst();

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
//							
						}
						
					
						
						int adad=0;
						
						if(pile.isEmpty()) {
							System.out.println("Do nothing is empty!");
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//							model.addAttribute("img_source", pile);
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("url","_third");
							
						}else if(pile.size()<3){
							System.out.println(pile.get(0).alt_arr);
							System.out.println("Do nothing!");
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("url","_third");
					     	
						}else {
							System.out.println(pile.get(0).alt_arr);
							if(pile.size()%3!=0) {
//								System.out.println("Has");
								adad=pile.size()%3;
								System.out.println(adad);
//								System.out.println("an");
									for(int i=0;i<adad;i++) {
										System.out.println("!");
										pile.remove(i);
										
									}
							}
							
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("mult",pile.size()-1);
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("url","_third");
					     	
						}
						
						System.out.println(adad);
						System.out.println("\n");

						
						

						connection.close();
						prepare.close();
						result.close();				
///////////Checking for _third_pl_name_hp_12 or _hp_24 or _hp_36				
					}else if(heq.contains("_hp_12") || heq.contains("_hp_24") || heq.contains("_hp_36")){
						connection = DriverManager.getConnection(DB_URL,User,Pass);
						PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						prepare.setInt(1, pl_id.get(0).intValue());
						ResultSet result=prepare.executeQuery();
						
						
						if(!result.next()) {
							System.out.println("Is empty!");
							
						}else{
							result.beforeFirst();

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
//							
						}
						
					
						
						int adad=0;
						
						if(pile.isEmpty()) {
							System.out.println("Do nothing is empty!");
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//							model.addAttribute("img_source", pile);
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("url","_third");
							
						}else if(pile.size()<3){
							System.out.println(pile.get(0).alt_arr);
							System.out.println("Do nothing!");
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("url","_third");
					     	
						}else {
							System.out.println(pile.get(0).alt_arr);
							if(pile.size()%3!=0) {
//								System.out.println("Has");
								adad=pile.size()%3;
								System.out.println(adad);
//								System.out.println("an");
									for(int i=0;i<adad;i++) {
										System.out.println("!");
										pile.remove(i);
										
									}
							}
							
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("mult",pile.size()-1);
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("url","_third");
					     	
						}
						
						System.out.println(adad);
						System.out.println("\n");

						
						

						connection.close();
						prepare.close();
						result.close();
///////////Checking for _third_pl_name_ni_12 or _ni_24 or _ni_36
					}else if(heq.contains("_ni_12") || heq.contains("_ni_24") || heq.contains("_ni_36")) {
						connection = DriverManager.getConnection(DB_URL,User,Pass);
						PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						prepare.setInt(1, pl_id.get(0).intValue());
						ResultSet result=prepare.executeQuery();
						
						
						if(!result.next()) {
							System.out.println("Is empty!");
							
						}else{
							result.beforeFirst();

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
//							
						}
						
					
						
						int adad=0;
						
						if(pile.isEmpty()) {
							System.out.println("Do nothing is empty!");
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//							model.addAttribute("img_source", pile);
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("url","_third");
							
						}else if(pile.size()<3){
							System.out.println(pile.get(0).alt_arr);
							System.out.println("Do nothing!");
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile);
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("url","_third");
					     	
						}else {
							System.out.println(pile.get(0).alt_arr);
							if(pile.size()%3!=0) {
//								System.out.println("Has");
								adad=pile.size()%3;
								System.out.println(adad);
//								System.out.println("an");
									for(int i=0;i<adad;i++) {
										System.out.println("!");
										pile.remove(i);
										
									}
							}
							
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile);
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("mult",pile.size()-1);
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("url","_third");
					     	
						}
						
						System.out.println(adad);
						System.out.println("\n");

						
						

						connection.close();
						prepare.close();
						result.close();
///////////Checking for _third_pl_name_12 or _24 or _36
					}else if(heq.contains("_12") || heq.contains("_24") || heq.contains("_36")) {
						connection = DriverManager.getConnection(DB_URL,User,Pass);
						PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						prepare.setInt(1, pl_id.get(0).intValue());
						ResultSet result=prepare.executeQuery();
						
						
						if(!result.next()) {
							System.out.println("Is empty!");
							
						}else{
							result.beforeFirst();

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
//							
						}
						
					
						
						int adad=0;
						
						if(pile.isEmpty()) {
							System.out.println("Do nothing is empty!");
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//							model.addAttribute("img_source", pile);
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("url","_third");
							
						}else if(pile.size()<3){
							System.out.println(pile.get(0).alt_arr);
							System.out.println("Do nothing!");
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile);
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("url","_third");
					     	
						}else {
							System.out.println(pile.get(0).alt_arr);
							if(pile.size()%3!=0) {
//								System.out.println("Has");
								adad=pile.size()%3;
								System.out.println(adad);
//								System.out.println("an");
									for(int i=0;i<adad;i++) {
										System.out.println("!");
										pile.remove(i);
										
									}
							}
							
							
							System.out.println(pile.size());
//							pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
							model.addAttribute("img_source", pile);
//					     	model.addAttribute("width",pile.size());
					     	model.addAttribute("mult",pile.size()-1);
					     	model.addAttribute("low_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_lp");
					     	model.addAttribute("high_price","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_hp");
					     	model.addAttribute("new_item","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_ni");
					     	model.addAttribute("size_one","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_12");
					     	model.addAttribute("size_two","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_24");
					     	model.addAttribute("size_three","_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", "")+"_36");
					     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name.get(0).toString().replaceAll("^\\[|\\]$", ""));
					     	model.addAttribute("url","_third");
					     	
						}
						
						System.out.println(adad);
						System.out.println("\n");

						
						

						connection.close();
						prepare.close();
						result.close();

					}

			///////////END
					
						
		}
///////////END
		
				
}	
	
	
	
	
	
	
	
	
	
	
	
	
	public void info_out(Connection connection,String DB_URL,String User,String Pass,List<Dis> pile,Model model,Integer player_num,String req,String pl_name) throws SQLException, UnsupportedEncodingException {
		String heq=URLDecoder.decode(req, "UTF-8");
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
		}
		
		System.out.println("Testing connection to Postgresql JDBC");

		
		if(heq.contains("away")) {
			String tab_name="away_kits";
			String tab_sub_name="away";
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepare.setInt(1, player_num);
			ResultSet result=prepare.executeQuery();

			if(!result.next()) {
				System.out.println("Is empty!");
				
			}else{
				result.beforeFirst();

				while(result.next()) {
					String src=result.getString(tab_sub_name+"_img_link");
					String alt=result.getString(tab_sub_name+"_kit_descrp");
					Integer pr=result.getInt("price");
					Integer pr_coin=result.getInt("price_coin");
					
					
					pile.add(new Dis(src, alt, pr, pr_coin));
					
			
				}
//				
			}
			
			
						
			
			int adad=0;
			
			if(pile.isEmpty()) {
				System.out.println("Do nothing is empty!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
			 	model.addAttribute("low_price","_"+pl_name+"_lp");
		     	model.addAttribute("high_price","_"+pl_name+"_hp");
		     	model.addAttribute("new_item","_"+pl_name+"_ni");
		     	model.addAttribute("size_one","_"+pl_name+"_12");
		     	model.addAttribute("size_two","_"+pl_name+"_24");
		     	model.addAttribute("size_three","_"+pl_name+"_36");
		     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("url","_away");
				
				
			}else if(pile.size()<3){
				System.out.println(pile.get(0).alt_arr);
				System.out.println("Do nothing!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
			 	model.addAttribute("low_price","_"+pl_name+"_lp");
		     	model.addAttribute("high_price","_"+pl_name+"_hp");
		     	model.addAttribute("new_item","_"+pl_name+"_ni");
		     	model.addAttribute("size_one","_"+pl_name+"_12");
		     	model.addAttribute("size_two","_"+pl_name+"_24");
		     	model.addAttribute("size_three","_"+pl_name+"_36");
		     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("url","_away");
				
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
//		     	model.addAttribute("width",pile.size());
		     	model.addAttribute("mult",pile.size()-1);
			 	model.addAttribute("low_price","_"+pl_name+"_lp");
		     	model.addAttribute("high_price","_"+pl_name+"_hp");
		     	model.addAttribute("new_item","_"+pl_name+"_ni");
		     	model.addAttribute("size_one","_"+pl_name+"_12");
		     	model.addAttribute("size_two","_"+pl_name+"_24");
		     	model.addAttribute("size_three","_"+pl_name+"_36");
		     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("item","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("url","_away");
				
			}
			
			System.out.println(adad);
			System.out.println("\n");
			
			
			
			connection.close();
			prepare.close();
			result.close();

		}else if(heq.contains("keeper")) {
			
			String tab_name="keeper_kits";
			String tab_sub_name="keeper";
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepare.setInt(1, player_num);
			ResultSet result=prepare.executeQuery();
			
			if(!result.next()) {
				System.out.println("Is empty!");
			}else {
				result.beforeFirst();
				while(result.next()) {
					String src=result.getString(tab_sub_name+"_img_link");
					String alt=result.getString(tab_sub_name+"_kit_descrp");
					Integer pr=result.getInt("price");
					Integer pr_coin=result.getInt("price_coin");
					
					
					pile.add(new Dis(src, alt, pr, pr_coin));
					
			
				}
				
				System.out.println(pile.get(0).alt_arr);
				
			}
			
			
			int adad=0;
			

			if(pile.isEmpty()) {
				System.out.println("Do nothing is empty!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
			 	model.addAttribute("low_price","_"+pl_name+"_lp");
		     	model.addAttribute("high_price","_"+pl_name+"_hp");
		     	model.addAttribute("new_item","_"+pl_name+"_ni");
		     	model.addAttribute("size_one","_"+pl_name+"_12");
		     	model.addAttribute("size_two","_"+pl_name+"_24");
		     	model.addAttribute("size_three","_"+pl_name+"_36");
		     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("url","_keeper");
				
				
			}else if(pile.size()<3){
				System.out.println("Do nothing!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","_"+pl_name+"_lp");
		     	model.addAttribute("high_price","_"+pl_name+"_hp");
		     	model.addAttribute("new_item","_"+pl_name+"_ni");
		     	model.addAttribute("size_one","_"+pl_name+"_12");
		     	model.addAttribute("size_two","_"+pl_name+"_24");
		     	model.addAttribute("size_three","_"+pl_name+"_36");
		     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("url","_keeper");
				
			}else {
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
//		     	model.addAttribute("width",pile.size());
		     	model.addAttribute("mult",pile.size()-1);
		     	model.addAttribute("low_price","_"+pl_name+"_lp");
		     	model.addAttribute("high_price","_"+pl_name+"_hp");
		     	model.addAttribute("new_item","_"+pl_name+"_ni");
		     	model.addAttribute("size_one","_"+pl_name+"_12");
		     	model.addAttribute("size_two","_"+pl_name+"_24");
		     	model.addAttribute("size_three","_"+pl_name+"_36");
		     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("url","_keeper");
				
			}
			
			
			System.out.println(adad);
			System.out.println("\n");
			
			

	     	
			connection.close();
			prepare.close();
			result.close();

			
		}else if(heq.contains("home")) {
			String tab_name="home_kits";
			String tab_sub_name="home";
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepare.setInt(1, player_num);
			ResultSet result=prepare.executeQuery();
			
			if(!result.next()) {
				System.out.println("Is empty!");
			}else {
				result.beforeFirst();
				while(result.next()) {
					String src=result.getString(tab_sub_name+"_img_link");
					String alt=result.getString(tab_sub_name+"_kit_descrp");
					Integer pr=result.getInt("price");
					Integer pr_coin=result.getInt("price_coin");
					
					
					pile.add(new Dis(src, alt, pr, pr_coin));
					
			
				}
				
				
			}	
			
			int adad=0;
			
			
			if(pile.isEmpty()) {
				System.out.println("Do nothing is empty!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","_"+pl_name+"_lp");
		     	model.addAttribute("high_price","_"+pl_name+"_hp");
		     	model.addAttribute("new_item","_"+pl_name+"_ni");
		     	model.addAttribute("size_one","_"+pl_name+"_12");
		     	model.addAttribute("size_two","_"+pl_name+"_24");
		     	model.addAttribute("size_three","_"+pl_name+"_36");
		     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("url","_home");
				
				
			}else if(pile.size()<3){
				System.out.println("Do nothing!");
				System.out.println(pile.get(0).alt_arr);
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","_"+pl_name+"_lp");
		     	model.addAttribute("high_price","_"+pl_name+"_hp");
		     	model.addAttribute("new_item","_"+pl_name+"_ni");
		     	model.addAttribute("size_one","_"+pl_name+"_12");
		     	model.addAttribute("size_two","_"+pl_name+"_24");
		     	model.addAttribute("size_three","_"+pl_name+"_36");
		     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("url","_home");
				
			}else {
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
				
				System.out.println(pile.get(0).alt_arr);
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
		     	model.addAttribute("mult",pile.size()-1);
		     	model.addAttribute("low_price","_"+pl_name+"_lp");
		     	model.addAttribute("high_price","_"+pl_name+"_hp");
		     	model.addAttribute("new_item","_"+pl_name+"_ni");
		     	model.addAttribute("size_one","_"+pl_name+"_12");
		     	model.addAttribute("size_two","_"+pl_name+"_24");
		     	model.addAttribute("size_three","_"+pl_name+"_36");
		     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("url","_home");
				
			}
			
			
			System.out.println(adad);
			System.out.println("\n");
			
			
			
			
			
			connection.close();
			prepare.close();
			result.close();

		}else if(heq.contains("third")) {
			String tab_name="third_kits";
			String tab_sub_name="third";
			connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE player_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			prepare.setInt(1, player_num);
			ResultSet result=prepare.executeQuery();
			
			if(!result.next()) {
				System.out.println("Is empty!");
			}else {
				result.beforeFirst();
				while(result.next()) {
					String src=result.getString(tab_sub_name+"_img_link");
					String alt=result.getString(tab_sub_name+"_kit_descrp");
					Integer pr=result.getInt("price");
					Integer pr_coin=result.getInt("price_coin");
					
					
					pile.add(new Dis(src, alt, pr, pr_coin));
					
					System.out.println(alt);
					
				}
				
			}
			
			int adad=0;
			
			
			if(pile.isEmpty()) {
				System.out.println("Do nothing is empty!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","_"+pl_name+"_lp");
		     	model.addAttribute("high_price","_"+pl_name+"_hp");
		     	model.addAttribute("new_item","_"+pl_name+"_ni");
		     	model.addAttribute("size_one","_"+pl_name+"_12");
		     	model.addAttribute("size_two","_"+pl_name+"_24");
		     	model.addAttribute("size_three","_"+pl_name+"_36");
		     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("url","_third");
				
				
			}else if(pile.size()<3){
				System.out.println("Do nothing!");
				System.out.println(pile.get(0).alt_arr);
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","_"+pl_name+"_lp");
		     	model.addAttribute("high_price","_"+pl_name+"_hp");
		     	model.addAttribute("new_item","_"+pl_name+"_ni");
		     	model.addAttribute("size_one","_"+pl_name+"_12");
		     	model.addAttribute("size_two","_"+pl_name+"_24");
		     	model.addAttribute("size_three","_"+pl_name+"_36");
		     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("url","_third");
		     	
			}else {
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
				
				System.out.println(pile.get(0).alt_arr);
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
		     	model.addAttribute("mult",pile.size()-1);
		     	model.addAttribute("low_price","_"+pl_name+"_lp");
		     	model.addAttribute("high_price","_"+pl_name+"_hp");
		     	model.addAttribute("new_item","_"+pl_name+"_ni");
		     	model.addAttribute("size_one","_"+pl_name+"_12");
		     	model.addAttribute("size_two","_"+pl_name+"_24");
		     	model.addAttribute("size_three","_"+pl_name+"_36");
		     	model.addAttribute("mass","_"+tab_sub_name+"_"+pl_name);
		     	model.addAttribute("url","_third");
		     	
			}
			
			
			System.out.println(adad);
			System.out.println("\n");
			
			

			
			connection.close();
			prepare.close();
			result.close();

		}
		
						
			
			
	}
	
	

	
	@GetMapping("/ronaldo")
	public String ronaldo(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Cristiano Ronaldo Football Kits");
		model.addAttribute("direct_name", "Cristiano Ronaldo");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,1,req,"Ronaldo");
	   	
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
		
		
	}
	
	
	@GetMapping("/rashford")
	public String rashford(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Marcus Rashford Football Kits");
		model.addAttribute("direct_name", "Marcus Rashford");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,2,req,"Rashford");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	@GetMapping("/fernandes")
	public String fernandes(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Bruno Fernandes Football Kits");
		model.addAttribute("direct_name", "Bruno Fernandes");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,3,req,"Fernandes");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	
	@GetMapping("/greenwood")
	public String greenwood(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Mason Greenwood Football Kits");
		model.addAttribute("direct_name", "Mason Greenwood");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,4,req,"Greenwood");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	
	@GetMapping("/sancho")
	public String sancho(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Jadon Sancho Football Kits");
		model.addAttribute("direct_name", "Jadon Sancho");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,5,req,"Sancho");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	
	
	@GetMapping("/cavani")
	public String cavani(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Edinson Cavani Football Kits");
		model.addAttribute("direct_name", "Edinson Cavani");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,6,req,"Cavani");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	
	@GetMapping("/pogba")
	public String pogba(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Paul Pogba Football Kits");
		model.addAttribute("direct_name", "Paul Pogba");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,7,req,"Pogba");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	
	@GetMapping("/varane")
	public String varane(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Raphael Varane Football Kits");
		model.addAttribute("direct_name", "Raphael Varane");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,8,req,"Varane");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	
	@GetMapping("/de_gea")
	public String de_gea(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United David De Gea Football Kits");
		model.addAttribute("direct_name", "David De Gea");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	keeper_kit(connection,DB_URL,User,Pass,pile,model,9,"De Gea");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "man_fk_goalkeeper";
		}

	   	
	}
	
	
	@GetMapping("/wan_bissaka")
	public String wan_bissaka(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Aaron Wan-Bissaka Football Kits");
		model.addAttribute("direct_name", "Aaron Wan-Bissaka");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,10,req,"Wan-Bissaka");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
	   	
	}

	
	@GetMapping("/bailly")
	public String bailly(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Eric Bailly Football Kits");
		model.addAttribute("direct_name", "Eric Bailly");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,11,req,"Bailly");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	
	@GetMapping("/lingard")
	public String lingard(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Jesse Lingard Football Kits");
		model.addAttribute("direct_name", "Jesse Lingard");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,12,req,"Lingard");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	
	@GetMapping("/van_de_beek")
	public String van_de_beek(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Donny Van De Beek Football Kits");
		model.addAttribute("direct_name", "Donny Van De Beek");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,13,req,"Van De Beek");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	
	@GetMapping("/matic")
	public String matic(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Nemanja Matić Football Kits");
		model.addAttribute("direct_name", "Nemanja Matić");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,14,req,"Matic");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	
	@GetMapping("/mctominay")
	public String mctominay(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Scott McTominay Football Kits");
		model.addAttribute("direct_name", "Scott McTominay");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,15,req,"McTominay");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	
	@GetMapping("/fred")
	public String fred(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Fred Football Kits");
		model.addAttribute("direct_name", "Fred");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,16,req,"Fred");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	
	@GetMapping("/shaw")
	public String shaw(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Luke Shaw Football Kits");
		model.addAttribute("direct_name", "Luke Shaw");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,17,req,"Shaw");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}

	   	
	}
	
	@GetMapping("/maguire")
	public String maguire(Model model,HttpServletRequest request) throws IOException, SQLException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[]/");
		model.addAttribute("title", "Manchester United Harry Maguire Football Kits");
		model.addAttribute("direct_name", "Harry Maguire");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		boolean is_empty=false;
		String req = request.getHeader("referer");
		
		
		System.out.println("PostgreSQL JDBC Driver successfully connected");
		Connection connection = null;
//			If the database is empty?
		db_empty(connection,is_empty,DB_URL,User,Pass,req);
	  
	   	info_out(connection,DB_URL,User,Pass,pile,model,18,req,"Maguire");
		
	   	if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
	   	
	}
///////////////////////Filters
	@GetMapping("/_Ronaldo_lp")
	public String _Ronaldo_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Cristiano Ronaldo Football Kits");
		model.addAttribute("direct_name", "Cristiano Ronaldo");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Ronaldo_hp")
	public String _Ronaldo_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Cristiano Ronaldo Football Kits");
		model.addAttribute("direct_name", "Cristiano Ronaldo");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Ronaldo_ni")
	public String _Ronaldo_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Cristiano Ronaldo Football Kits");
		model.addAttribute("direct_name", "Cristiano Ronaldo");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Ronaldo_12")
	public String _Ronaldo_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Cristiano Ronaldo Football Kits");
		model.addAttribute("direct_name", "Cristiano Ronaldo");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Ronaldo_24")
	public String _Ronaldo_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Cristiano Ronaldo Football Kits");
		model.addAttribute("direct_name", "Cristiano Ronaldo");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Ronaldo_36")
	public String _Ronaldo_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Cristiano Ronaldo Football Kits");
		model.addAttribute("direct_name", "Cristiano Ronaldo");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
//Rashford filters
	@GetMapping("/_Rashford_lp")
	public String _Rashford_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Marcus Rashford Football Kits");
		model.addAttribute("direct_name", "Marcus Rashford");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Rashford_hp")
	public String _Rashford_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Marcus Rashford Football Kits");
		model.addAttribute("direct_name", "Marcus Rashford");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Rashford_ni")
	public String _Rashford_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Marcus Rashford Football Kits");
		model.addAttribute("direct_name", "Marcus Rashford");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Rashford_12")
	public String _Rashford_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Marcus Rashford Football Kits");
		model.addAttribute("direct_name", "Marcus Rashford");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Rashford_24")
	public String _Rashford_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Marcus Rashford Football Kits");
		model.addAttribute("direct_name", "Marcus Rashford");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Rashford_36")
	public String _Rashford_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Marcus Rashford Football Kits");
		model.addAttribute("direct_name", "Marcus Rashford");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
//Fernandes	
	@GetMapping("/_Fernandes_lp")
	public String _Fernandes_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Bruno Fernandes Football Kits");
		model.addAttribute("direct_name", "Bruno Fernandes");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Fernandes_hp")
	public String _Fernandes_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Bruno Fernandes Football Kits");
		model.addAttribute("direct_name", "Bruno Fernandes");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Fernandes_ni")
	public String _Fernandes_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Bruno Fernandes Football Kits");
		model.addAttribute("direct_name", "Bruno Fernandes");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Fernandes_12")
	public String _Fernandes_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Bruno Fernandes Football Kits");
		model.addAttribute("direct_name", "Bruno Fernandes");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Fernandes_24")
	public String _Fernandes_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Bruno Fernandes Football Kits");
		model.addAttribute("direct_name", "Bruno Fernandes");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Fernandes_36")
	public String _Fernandes_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Bruno Fernandes Football Kits");
		model.addAttribute("direct_name", "Bruno Fernandes");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}	
	
///Greenwood
	@GetMapping("/_Greenwood_lp")
	public String _Greenwood_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Mason Greenwood Football Kits");
		model.addAttribute("direct_name", "Mason Greenwood");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Greenwood_hp")
	public String _Greenwood_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Mason Greenwood Football Kits");
		model.addAttribute("direct_name", "Mason Greenwood");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Greenwood_ni")
	public String _Greenwood_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Mason Greenwood Football Kits");
		model.addAttribute("direct_name", "Mason Greenwood");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Greenwood_12")
	public String _Greenwood_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Mason Greenwood Football Kits");
		model.addAttribute("direct_name", "Mason Greenwood");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Greenwood_24")
	public String _Greenwood_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Mason Greenwood Football Kits");
		model.addAttribute("direct_name", "Mason Greenwood");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Greenwood_36")
	public String _Greenwood_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Mason Greenwood Football Kits");
		model.addAttribute("direct_name", "Mason Greenwood");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
////Sancho
	@GetMapping("/_Sancho_lp")
	public String _Sancho_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Jadon Sancho Football Kits");
		model.addAttribute("direct_name", "Jadon Sancho");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Sancho_hp")
	public String _Sancho_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Jadon Sancho Football Kits");
		model.addAttribute("direct_name", "Jadon Sancho");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Sancho_ni")
	public String _Sancho_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Jadon Sancho Football Kits");
		model.addAttribute("direct_name", "Jadon Sancho");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Sancho_12")
	public String _Sancho_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Jadon Sancho Football Kits");
		model.addAttribute("direct_name", "Jadon Sancho");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Sancho_24")
	public String _Sancho_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Jadon Sancho Football Kits");
		model.addAttribute("direct_name", "Jadon Sancho");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Sancho_36")
	public String _Sancho_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Jadon Sancho Football Kits");
		model.addAttribute("direct_name", "Jadon Sancho");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
/////////Cavani
	@GetMapping("/_Cavani_lp")
	public String _Cavani_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Edinson Cavani Football Kits");
		model.addAttribute("direct_name", "Edinson Cavani");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Cavani_hp")
	public String _Cavani_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Edinson Cavani Football Kits");
		model.addAttribute("direct_name", "Edinson Cavani");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Cavani_ni")
	public String _Cavani_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Edinson Cavani Football Kits");
		model.addAttribute("direct_name", "Edinson Cavani");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Cavani_12")
	public String _Cavani_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Edinson Cavani Football Kits");
		model.addAttribute("direct_name", "Edinson Cavani");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Cavani_24")
	public String _Cavani_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Edinson Cavani Football Kits");
		model.addAttribute("direct_name", "Edinson Cavani");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Cavani_36")
	public String _Cavani_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Edinson Cavani Football Kits");
		model.addAttribute("direct_name", "Edinson Cavani");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
////Pogba
	@GetMapping("/_Pogba_lp")
	public String _Pogba_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Paul Pogba Football Kits");
		model.addAttribute("direct_name", "Paul Pogba");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Pogba_hp")
	public String _Pogba_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Paul Pogba Football Kits");
		model.addAttribute("direct_name", "Paul Pogba");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Pogba_ni")
	public String _Pogba_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Paul Pogba Football Kits");
		model.addAttribute("direct_name", "Paul Pogba");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Pogba_12")
	public String _Pogba_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Paul Pogba Football Kits");
		model.addAttribute("direct_name", "Paul Pogba");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Pogba_24")
	public String _Pogba_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Paul Pogba Football Kits");
		model.addAttribute("direct_name", "Paul Pogba");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Pogba_36")
	public String _Pogba_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Paul Pogba Football Kits");
		model.addAttribute("direct_name", "Paul Pogba");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
//////////Varane
	@GetMapping("/_Varane_lp")
	public String _Varane_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Raphael Varane Football Kits");
		model.addAttribute("direct_name", "Raphael Varane");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Varane_hp")
	public String _Varane_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Raphael Varane Football Kits");
		model.addAttribute("direct_name", "Raphael Varane");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Varane_ni")
	public String _Varane_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Raphael Varane Football Kits");
		model.addAttribute("direct_name", "Raphael Varane");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Varane_12")
	public String _Varane_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Raphael Varane Football Kits");
		model.addAttribute("direct_name", "Raphael Varane");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Varane_24")
	public String _Varane_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Raphael Varane Football Kits");
		model.addAttribute("direct_name", "Raphael Varane");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Varane_36")
	public String _Varane_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Raphael Varane Football Kits");
		model.addAttribute("direct_name", "Raphael Varane");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
////////De Gea
	@GetMapping("/_De Gea_lp")
	public String _De_Gea_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United David De Gea Football Kits");
		model.addAttribute("direct_name", "David De Gea");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_De Gea_hp")
	public String _De_Gea_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United David De Gea Football Kits");
		model.addAttribute("direct_name", "David De Gea");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_De Gea_ni")
	public String _De_Gea_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United David De Gea Football Kits");
		model.addAttribute("direct_name", "David De Gea");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_De Gea_12")
	public String _De_Gea_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United David De Gea Football Kits");
		model.addAttribute("direct_name", "David De Gea");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_De Gea_24")
	public String _De_Gea_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United David De Gea Football Kits");
		model.addAttribute("direct_name", "David De Gea");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_De Gea_36")
	public String _De_Gea_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United David De Gea Football Kits");
		model.addAttribute("direct_name", "David De Gea");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
//////////////Wan-Bissaka
	@GetMapping("/_Wan-Bissaka_lp")
	public String _Wan_Bissaka_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Aaron Wan-Bissaka Football Kits");
		model.addAttribute("direct_name", "Aaron Wan-Bissaka");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Wan-Bissaka_hp")
	public String _Wan_Bissaka_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Aaron Wan-Bissaka Football Kits");
		model.addAttribute("direct_name", "Aaron Wan-Bissaka");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Wan-Bissaka_ni")
	public String _Wan_Bissaka_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Aaron Wan-Bissaka Football Kits");
		model.addAttribute("direct_name", "Aaron Wan-Bissaka");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Wan-Bissaka_12")
	public String _Wan_Bissaka_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Aaron Wan-Bissaka Football Kits");
		model.addAttribute("direct_name", "Aaron Wan-Bissaka");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Wan-Bissaka_24")
	public String _Wan_Bissaka_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Aaron Wan-Bissaka Football Kits");
		model.addAttribute("direct_name", "Aaron Wan-Bissaka");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Wan-Bissaka_36")
	public String _Wan_Bissaka_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Aaron Wan-Bissaka Football Kits");
		model.addAttribute("direct_name", "Aaron Wan-Bissaka");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
/////////////Bailly
	@GetMapping("/_Bailly_lp")
	public String _Bailly_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Eric Bailly Football Kits");
		model.addAttribute("direct_name", "Eric Bailly");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Bailly_hp")
	public String _Bailly_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Eric Bailly Football Kits");
		model.addAttribute("direct_name", "Eric Bailly");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Bailly_ni")
	public String _Bailly_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Eric Bailly Football Kits");
		model.addAttribute("direct_name", "Eric Bailly");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Bailly_12")
	public String _Bailly_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Eric Bailly Football Kits");
		model.addAttribute("direct_name", "Eric Bailly");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Bailly_24")
	public String _Bailly_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Eric Bailly Football Kits");
		model.addAttribute("direct_name", "Eric Bailly");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Bailly_36")
	public String _Bailly_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Eric Bailly Football Kits");
		model.addAttribute("direct_name", "Eric Bailly");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
/////////////Lingard
	@GetMapping("/_Lingard_lp")
	public String _Lingard_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Jesse Lingard Football Kits");
		model.addAttribute("direct_name", "Jesse Lingard");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Lingard_hp")
	public String _Lingard_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Jesse Lingard Football Kits");
		model.addAttribute("direct_name", "Jesse Lingard");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Lingard_ni")
	public String _Lingard_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Jesse Lingard Football Kits");
		model.addAttribute("direct_name", "Jesse Lingard");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Lingard_12")
	public String _Lingard_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Jesse Lingard Football Kits");
		model.addAttribute("direct_name", "Jesse Lingard");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Lingard_24")
	public String _Lingard_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Jesse Lingard Football Kits");
		model.addAttribute("direct_name", "Jesse Lingard");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Lingard_36")
	public String _Lingard_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Jesse Lingard Football Kits");
		model.addAttribute("direct_name", "Jesse Lingard");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
/////////Matic
	@GetMapping("/_Matic_lp")
	public String _Matic_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Nemanja Matic Football Kits");
		model.addAttribute("direct_name", "Nemanja Matic");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Matic_hp")
	public String _Matic_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Nemanja Matic Football Kits");
		model.addAttribute("direct_name", "Nemanja Matic");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Matic_ni")
	public String _Matic_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Nemanja Matic Football Kits");
		model.addAttribute("direct_name", "Nemanja Matic");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Matic_12")
	public String _Matic_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Nemanja Matic Football Kits");
		model.addAttribute("direct_name", "Nemanja Matic");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Matic_24")
	public String _Matic_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Nemanja Matic Football Kits");
		model.addAttribute("direct_name", "Nemanja Matic");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Matic_36")
	public String _Matic_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Nemanja Matic Football Kits");
		model.addAttribute("direct_name", "Nemanja Matic");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
////////////McTominay
	@GetMapping("/_McTominay_lp")
	public String _McTominay_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Scott McTominay Football Kits");
		model.addAttribute("direct_name", "Scott McTominay");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_McTominay_hp")
	public String _McTominay_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Scott McTominay Football Kits");
		model.addAttribute("direct_name", "Scott McTominay");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_McTominay_ni")
	public String _McTominay_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Scott McTominay Football Kits");
		model.addAttribute("direct_name", "Scott McTominay");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_McTominay_12")
	public String _McTominay_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Scott McTominay Football Kits");
		model.addAttribute("direct_name", "Scott McTominay");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_McTominay_24")
	public String _McTominay_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Scott McTominay Football Kits");
		model.addAttribute("direct_name", "Scott McTominay");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_McTominay_36")
	public String _McTominay_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Scott McTominay Football Kits");
		model.addAttribute("direct_name", "Scott McTominay");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
///////////////////////Fred
	@GetMapping("/_Fred_lp")
	public String _Fred_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Fred Football Kits");
		model.addAttribute("direct_name", "Fred");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Fred_hp")
	public String _Fred_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Fred Football Kits");
		model.addAttribute("direct_name", "Fred");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Fred_ni")
	public String _Fred_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Fred Football Kits");
		model.addAttribute("direct_name", "Fred");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Fred_12")
	public String _Fred_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Fred Football Kits");
		model.addAttribute("direct_name", "Fred");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Fred_24")
	public String _Fred_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Fred Football Kits");
		model.addAttribute("direct_name", "Fred");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Fred_36")
	public String _Fred_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Fred Football Kits");
		model.addAttribute("direct_name", "Fred");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
///////////////////Shaw
	@GetMapping("/_Shaw_lp")
	public String _Shaw_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Luke Shaw Football Kits");
		model.addAttribute("direct_name", "Luke Shaw");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Shaw_hp")
	public String _Shaw_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Luke Shaw Football Kits");
		model.addAttribute("direct_name", "Luke Shaw");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Shaw_ni")
	public String _Shaw_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Luke Shaw Football Kits");
		model.addAttribute("direct_name", "Luke Shaw");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Shaw_12")
	public String _Shaw_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Luke Shaw Football Kits");
		model.addAttribute("direct_name", "Luke Shaw");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Shaw_24")
	public String _Shaw_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Luke Shaw Football Kits");
		model.addAttribute("direct_name", "Luke Shaw");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Shaw_36")
	public String _Shaw_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Luke Shaw Football Kits");
		model.addAttribute("direct_name", "Luke Shaw");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
////////////////Maguire
	@GetMapping("/_Maguire_lp")
	public String _Maguire_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Harry Maguire Football Kits");
		model.addAttribute("direct_name", "Harry Maguire");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Maguire_hp")
	public String _Maguire_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Harry Maguire Football Kits");
		model.addAttribute("direct_name", "Harry Maguire");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Maguire_ni")
	public String _Maguire_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Harry Maguire Football Kits");
		model.addAttribute("direct_name", "Harry Maguire");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Maguire_12")
	public String _Maguire_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Harry Maguire Football Kits");
		model.addAttribute("direct_name", "Harry Maguire");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Maguire_24")
	public String _Maguire_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Harry Maguire Football Kits");
		model.addAttribute("direct_name", "Harry Maguire");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Maguire_36")
	public String _Maguire_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Harry Maguire Football Kits");
		model.addAttribute("direct_name", "Harry Maguire");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
//////////////Van De Beek
	@GetMapping("/_Van De Beek_lp")
	public String _Van_De_Beek_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Donny van de Beek Football Kits");
		model.addAttribute("direct_name", "Donny van de Beek");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	@GetMapping("/_Van De Beek_hp")
	public String _Van_De_Beek_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Donny van de Beek Football Kits");
		model.addAttribute("direct_name", "Donny van de Beek");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Van De Beek_ni")
	public String _Van_De_Beek_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Donny van de Beek Football Kits");
		model.addAttribute("direct_name", "Donny van de Beek");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_filter_size(connection,DB_URL,User,Pass,pile,model,request);

		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	@GetMapping("/_Van De Beek_12")
	public String _Van_De_Beek_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Donny van de Beek Football Kits");
		model.addAttribute("direct_name", "Donny van de Beek");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Van De Beek_24")
	public String _Van_De_Beek_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Donny van de Beek Football Kits");
		model.addAttribute("direct_name", "Donny van de Beek");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	@GetMapping("/_Van De Beek_36")
	public String _Van_De_Beek_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		model.addAttribute("title", "Manchester United Donny van de Beek Football Kits");
		model.addAttribute("direct_name", "Donny van de Beek");
	    List<Dis> pile =new ArrayList<Dis>();
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		info_out_size(connection,DB_URL,User,Pass,pile,model,request);
		if(pile.size()==0) {
			System.out.println("empty");
			return "empty";
		}else if(pile.size()<3){
			System.out.println("odd");
			return "odd";
		}else {
			System.out.println("away");
			return "away";
		}
			
	}
	
	
	
	
}
