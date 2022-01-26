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


public class Man_FootballKitsController {



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
	
	
public void func_man(Model model,HttpServletRequest request, List<Dis> pile) throws UnsupportedEncodingException, SQLException {
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		
		String page=request.getHeader("Referer");
		String heq=URLDecoder.decode(page, "UTF-8");
		System.out.println(page);
		
		boolean filter_contain=false;
		boolean filter_size=false;
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
		
		Connection connection=null;
		
		for(int i=0;i<filter_name.size();i++) {
			System.out.println("Here filters");
			if(heq.contains(filter_name.get(i))) {
				filter_contain=true;
					for(int j=0;j<tab_name.size();j++) {
					
			
						connection = DriverManager.getConnection(DB_URL,User,Pass);
						PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(j)+" WHERE category_id=6 OR category_id=7 OR category_id=0",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
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
					}
				int adad=0;
				
				if(pile.isEmpty()) {
					System.out.println("Do nothing is empty!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//					model.addAttribute("img_source", pile);
//			     	model.addAttribute("width",pile.size());
					model.addAttribute("low_price","man_fk"+"_lp");
			     	model.addAttribute("high_price","man_fk"+"_hp");
			     	model.addAttribute("new_item","man_fk"+"_ni");
			     	model.addAttribute("size_one","man_fk"+"_12");
			     	model.addAttribute("size_two","man_fk"+"_24");
			     	model.addAttribute("size_three","man_fk"+"_36");
			     	model.addAttribute("mass","Man_football_kits");
			     	model.addAttribute("item","Man_football_kits");
			     	model.addAttribute("url","Man_football_kits");
			     	model.addAttribute("title"," Manchester United Mens Kits, Man Utd Mens Shirt, Home & Away Kit");
			    	model.addAttribute("direct_name","MANCHESTER UNITED MEN FOOTBALL KITS");
					
				}else if(pile.size()<3){
					System.out.println(pile.get(0).alt_arr);
					System.out.println("Do nothing!");
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					if(heq.contains("_lp")) {
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
					}else if(heq.contains("_hp")) {
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
					}else if(heq.contains("_ni")) {
						model.addAttribute("img_source", pile);
					}
					
//			     	model.addAttribute("width",pile.size());
					model.addAttribute("low_price","man_fk"+"_lp");
			     	model.addAttribute("high_price","man_fk"+"_hp");
			     	model.addAttribute("new_item","man_fk"+"_ni");
			     	model.addAttribute("size_one","man_fk"+"_12");
			     	model.addAttribute("size_two","man_fk"+"_24");
			     	model.addAttribute("size_three","man_fk"+"_36");
			     	model.addAttribute("mass","Man_football_kits");
			     	model.addAttribute("item","Man_football_kits");
			     	model.addAttribute("url","Man_football_kits");
			     	model.addAttribute("title"," Manchester United Mens Kits, Man Utd Mens Shirt, Home & Away Kit");
			    	model.addAttribute("direct_name","MANCHESTER UNITED MEN FOOTBALL KITS");
					
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
					
					
					System.out.println(pile.size());
//					pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
					if(heq.contains("_lp")) {
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
					}else if(heq.contains("_hp")) {
						model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
					}else if(heq.contains("_ni")) {
						model.addAttribute("img_source", pile);
					}
//			     	model.addAttribute("width",pile.size());
			     	model.addAttribute("mult",pile.size()-1);
			     	model.addAttribute("low_price","man_fk"+"_lp");
			     	model.addAttribute("high_price","man_fk"+"_hp");
			     	model.addAttribute("new_item","man_fk"+"_ni");
			     	model.addAttribute("size_one","man_fk"+"_12");
			     	model.addAttribute("size_two","man_fk"+"_24");
			     	model.addAttribute("size_three","man_fk"+"_36");
			     	model.addAttribute("mass","Man_football_kits");
			     	model.addAttribute("item","Man_football_kits");
			     	model.addAttribute("url","Man_football_kits");
			     	model.addAttribute("title"," Manchester United Mens Kits, Man Utd Mens Shirt, Home & Away Kit");
			    	model.addAttribute("direct_name","MANCHESTER UNITED MEN FOOTBALL KITS");
			     	
				}
				
					connection.close();
				
				
				
				break;
			}
		}
		
		if(filter_contain==false) {
			for(int i=0;i<filter_num.size();i++) {
				System.out.println("Here nums");
				if(heq.contains(filter_num.get(i))) {
					filter_size=true;
					for(int j=0;j<tab_name.size();j++) {
						
						
						connection = DriverManager.getConnection(DB_URL,User,Pass);
						PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(j)+" WHERE category_id=6 OR category_id=7 OR category_id=0",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
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
					}
					
					
					int adad=0;
					
					if(pile.isEmpty()) {
						System.out.println("Do nothing is empty!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//						model.addAttribute("img_source", pile);
//				     	model.addAttribute("width",pile.size());
						model.addAttribute("low_price","man_fk"+"_lp");
				     	model.addAttribute("high_price","man_fk"+"_hp");
				     	model.addAttribute("new_item","man_fk"+"_ni");
				     	model.addAttribute("size_one","man_fk"+"_12");
				     	model.addAttribute("size_two","man_fk"+"_24");
				     	model.addAttribute("size_three","man_fk"+"_36");
				     	model.addAttribute("mass","Man_football_kits");
				     	model.addAttribute("item","Man_football_kits");
				     	model.addAttribute("url","Man_football_kits");
				     	model.addAttribute("title"," Manchester United Mens Kits, Man Utd Mens Shirt, Home & Away Kit");
				    	model.addAttribute("direct_name","MANCHESTER UNITED MEN FOOTBALL KITS");
						
					}else if(pile.size()<3){
						System.out.println(pile.get(0).alt_arr);
						System.out.println("Do nothing!");
						
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						model.addAttribute("img_source", pile);
//				     	model.addAttribute("width",pile.size());
						model.addAttribute("low_price","man_fk"+"_lp");
				     	model.addAttribute("high_price","man_fk"+"_hp");
				     	model.addAttribute("new_item","man_fk"+"_ni");
				     	model.addAttribute("size_one","man_fk"+"_12");
				     	model.addAttribute("size_two","man_fk"+"_24");
				     	model.addAttribute("size_three","man_fk"+"_36");
				     	model.addAttribute("mass","Man_football_kits");
				     	model.addAttribute("item","Man_football_kits");
				     	model.addAttribute("url","Man_football_kits");
				     	model.addAttribute("title"," Manchester United Mens Kits, Man Utd Mens Shirt, Home & Away Kit");
				    	model.addAttribute("direct_name","MANCHESTER UNITED MEN FOOTBALL KITS");
						
					}else {
						System.out.println(pile.get(0).alt_arr);
						if(pile.size()%3!=0) {
//							System.out.println("Has");
							adad=pile.size()%3;
							System.out.println(adad);
//							System.out.println("an");
								for(int i_1=0;i_1<adad;i_1++) {
									System.out.println("!");
									pile.remove(i_1);
									
								}
						}
						

					
						Collections.shuffle(pile);
						System.out.println(pile.size());
//						pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
						if(pile.size()>=decide_num) {
							System.out.println("pile>decide_num");
							model.addAttribute("img_source", pile.subList(0, decide_num));
							model.addAttribute("mult",pile.subList(0, decide_num).size()-1);
						}else {
							System.out.println("pile<decide_num");
							model.addAttribute("img_source", pile);
							model.addAttribute("mult",pile.size()-1);
						}
//				     	model.addAttribute("width",pile.size());
//				     	model.addAttribute("mult",pile.size()-1);
						model.addAttribute("low_price","man_fk"+"_lp");
				     	model.addAttribute("high_price","man_fk"+"_hp");
				     	model.addAttribute("new_item","man_fk"+"_ni");
				     	model.addAttribute("size_one","man_fk"+"_12");
				     	model.addAttribute("size_two","man_fk"+"_24");
				     	model.addAttribute("size_three","man_fk"+"_36");
				     	model.addAttribute("mass","Man_football_kits");
				     	model.addAttribute("item","Man_football_kits");
				     	model.addAttribute("url","Man_football_kits");
				     	model.addAttribute("title"," Manchester United Mens Kits, Man Utd Mens Shirt, Home & Away Kit");
				    	model.addAttribute("direct_name","MANCHESTER UNITED MEN FOOTBALL KITS");
					}
					
						connection.close();
					
					
						break;
					
				}
			}
		}
		
		if(filter_size==false && filter_contain==false) {
			for(int i=0;i<tab_name.size();i++) {
				System.out.println("Here nothing!");
				connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name.get(i)+" WHERE category_id=6 OR category_id=7 OR category_id=0",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
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
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","man_fk"+"_lp");
		     	model.addAttribute("high_price","man_fk"+"_hp");
		     	model.addAttribute("new_item","man_fk"+"_ni");
		     	model.addAttribute("size_one","man_fk"+"_12");
		     	model.addAttribute("size_two","man_fk"+"_24");
		     	model.addAttribute("size_three","man_fk"+"_36");
		     	model.addAttribute("mass","Man_football_kits");
		     	model.addAttribute("item","Man_football_kits");
		     	model.addAttribute("url","Man_football_kits");
		     	model.addAttribute("title"," Manchester United Mens Kits, Man Utd Mens Shirt, Home & Away Kit");
		    	model.addAttribute("direct_name","MANCHESTER UNITED MEN FOOTBALL KITS");
				
			}else if(pile.size()<3){
				System.out.println(pile.get(0).alt_arr);
				System.out.println("Do nothing!");
				
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
				model.addAttribute("low_price","man_fk"+"_lp");
		     	model.addAttribute("high_price","man_fk"+"_hp");
		     	model.addAttribute("new_item","man_fk"+"_ni");
		     	model.addAttribute("size_one","man_fk"+"_12");
		     	model.addAttribute("size_two","man_fk"+"_24");
		     	model.addAttribute("size_three","man_fk"+"_36");
		     	model.addAttribute("mass","Man_football_kits");
		     	model.addAttribute("item","Man_football_kits");
		     	model.addAttribute("url","Man_football_kits");
		     	model.addAttribute("title"," Manchester United Mens Kits, Man Utd Mens Shirt, Home & Away Kit");
		    	model.addAttribute("direct_name","MANCHESTER UNITED MEN FOOTBALL KITS");
				
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
				

			
				Collections.shuffle(pile);
				System.out.println(pile.size());
//				pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
				model.addAttribute("img_source", pile);
//		     	model.addAttribute("width",pile.size());
		     	model.addAttribute("mult",pile.size()-1);
				model.addAttribute("low_price","man_fk"+"_lp");
		     	model.addAttribute("high_price","man_fk"+"_hp");
		     	model.addAttribute("new_item","man_fk"+"_ni");
		     	model.addAttribute("size_one","man_fk"+"_12");
		     	model.addAttribute("size_two","man_fk"+"_24");
		     	model.addAttribute("size_three","man_fk"+"_36");
		     	model.addAttribute("mass","Man_football_kits");
		     	model.addAttribute("item","Man_football_kits");
		     	model.addAttribute("url","Man_football_kits");
		     	model.addAttribute("title"," Manchester United Mens Kits, Man Utd Mens Shirt, Home & Away Kit");
		    	model.addAttribute("direct_name","MANCHESTER UNITED MEN FOOTBALL KITS");
			}
			
				connection.close();
			
		}
		
}


@GetMapping("/man_football_kits")
public String man_football_kits(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	func_man(model, request, pile);
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


@GetMapping("/man_fk_lp")
public String man_fk_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	func_man(model, request, pile);
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


@GetMapping("/man_fk_hp")
public String man_fk_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	func_man(model, request, pile);
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
	

@GetMapping("/man_fk_ni")
public String man_fk_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	func_man(model, request, pile);
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


@GetMapping("/man_fk_12")
public String man_fk_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	func_man(model, request, pile);
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


@GetMapping("/man_fk_24")
public String man_fk_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	func_man(model, request, pile);
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



@GetMapping("/man_fk_36")
public String man_fk_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	List<Dis> pile =new ArrayList<Dis>();
	func_man(model, request, pile);
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