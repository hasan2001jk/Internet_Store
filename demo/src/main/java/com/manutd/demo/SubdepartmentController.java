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


public class SubdepartmentController {



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
	
	
	
	
	public boolean db_empty(HttpServletRequest request) throws UnsupportedEncodingException {
		boolean is_empty=false;
		
		String page=request.getHeader("Referer");
		String heq=URLDecoder.decode(page, "UTF-8");
		System.out.println(page);
		
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
		String User="user";
		String Pass="user";
		Connection connection=null;
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Postgresql not found");
			e.printStackTrace();
		}
		
		
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
	
	
	
	
	
	
	
	
	
	
public void func_subdep(List<Dis> pile,Model model,HttpServletRequest request,Integer num,String text) throws UnsupportedEncodingException, SQLException {
	String page=request.getHeader("Referer");
	String heq=URLDecoder.decode(page, "UTF-8");
	System.out.println(page);
	
	
	
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
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
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
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
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_away");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_away");
	     	
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
	     	model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_away");
	     	
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
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
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
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_home");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_home");
	     	
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
	     	model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
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
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
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
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_third");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_third");
	     	
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
	     	model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_third");
	     	
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
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
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
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_keeper");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
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
	     	model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_keeper");
	     	
		}
		
		System.out.println(adad);
		System.out.println("\n");
		
		
		
		connection.close();
		prepare.close();
		result.close();
		
		
	}
	
}
	




public void func_subdep(List<Dis> pile,Model model,HttpServletRequest request,Integer num,Integer num_extra,String text) throws UnsupportedEncodingException, SQLException {
	String page=request.getHeader("Referer");
	String heq=URLDecoder.decode(page, "UTF-8");
	System.out.println(page);
	
	
	
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
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
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
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
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_away");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_away");
	     	
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
	     	model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_away");
	     	
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
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
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
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_home");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_home");
	     	
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
	     	model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
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
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
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
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_third");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_third");
	     	
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
	     	model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_third");
	     	
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
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
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
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_keeper");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
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
	     	model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_keeper");
	     	
		}
		
		System.out.println(adad);
		System.out.println("\n");
		
		
		
		connection.close();
		prepare.close();
		result.close();
		
		
	}
	
}





public void func_subdep(List<Dis> pile,Model model,HttpServletRequest request,Integer num,Integer num_extra,Integer num_exx,String text) throws UnsupportedEncodingException, SQLException {
	String page=request.getHeader("Referer");
	String heq=URLDecoder.decode(page, "UTF-8");
	System.out.println(page);
	
	
	
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
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
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		prepare.setInt(3, num_exx);	
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
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_away");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_away");
	     	
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
	     	model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_away");
	     	
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
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		prepare.setInt(3, num_exx);
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
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_home");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_home");
	     	
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
	     	model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
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
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		prepare.setInt(3, num_exx);
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
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_third");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_third");
	     	
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
	     	model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_third");
	     	
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
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		prepare.setInt(3, num_exx);
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
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_keeper");
			
		}else if(pile.size()<3){
			System.out.println(pile.get(0).alt_arr);
			System.out.println("Do nothing!");
			
			System.out.println(pile.size());
//			pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
			model.addAttribute("img_source", pile);
//	     	model.addAttribute("width",pile.size());
			model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
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
	     	model.addAttribute("low_price",text+"_lp");
	     	model.addAttribute("high_price",text+"_hp");
	     	model.addAttribute("new_item",text+"_ni");
	     	model.addAttribute("size_one",text+"_12");
	     	model.addAttribute("size_two",text+"_24");
	     	model.addAttribute("size_three",text+"_36");
	     	model.addAttribute("mass",tab_name+"_"+text);
	     	model.addAttribute("item",tab_name+"_"+text);
	     	model.addAttribute("url","_keeper");
	     	
		}
		
		System.out.println(adad);
		System.out.println("\n");
		
		
		
		connection.close();
		prepare.close();
		result.close();
		
		
	}
	
}



public void size_lp(List<Dis> pile,Model model,HttpServletRequest request,String tab_name,String tab_sub_name,Integer num,String text) throws SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		System.out.println("Postgresql not found");
		e.printStackTrace();
	}
	
	System.out.println("Testing connection to Postgresql JDBC");
	
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	prepare.setInt(1, num);
	ResultSet result=prepare.executeQuery();
	
	while(result.next()) {
		String src=result.getString(tab_sub_name+"_img_link");
		String alt=result.getString(tab_sub_name+"_kit_descrp");
		Integer pr=result.getInt("price");
		Integer pr_coin=result.getInt("price_coin");
		
		
		pile.add(new Dis(src, alt, pr, pr_coin));
		

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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
	}else if(pile.size()<3){
		System.out.println(pile.get(0).alt_arr);
		System.out.println("Do nothing!");
		
		System.out.println(pile.size());
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
		model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//     	model.addAttribute("width",pile.size());
		model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
		
		
		System.out.println(pile.size());
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
		model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//     	model.addAttribute("width",pile.size());
     	model.addAttribute("mult",pile.size()-1);
     	model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
     	
	}
	
	connection.close();
	prepare.close();
	result.close();
	
}


public void size_lp(List<Dis> pile,Model model,HttpServletRequest request,String tab_name,String tab_sub_name,Integer num,String text,Integer num_extra,Integer num_exx) throws SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		System.out.println("Postgresql not found");
		e.printStackTrace();
	}
	
	System.out.println("Testing connection to Postgresql JDBC");
	
	if(num_exx!=null) {
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		prepare.setInt(3, num_exx);	
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
	}else {
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
	}else if(pile.size()<3){
		System.out.println(pile.get(0).alt_arr);
		System.out.println("Do nothing!");
		
		System.out.println(pile.size());
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
		model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//     	model.addAttribute("width",pile.size());
		model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
		
		
		System.out.println(pile.size());
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
		model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList()));
//     	model.addAttribute("width",pile.size());
     	model.addAttribute("mult",pile.size()-1);
     	model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
     	
	}
	
	connection.close();
	
	
}


public void size_hp(List<Dis> pile,Model model,HttpServletRequest request,String tab_name,String tab_sub_name,Integer num,String text) throws SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		System.out.println("Postgresql not found");
		e.printStackTrace();
	}
	
	System.out.println("Testing connection to Postgresql JDBC");
	
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	prepare.setInt(1, num);
	ResultSet result=prepare.executeQuery();
	
	while(result.next()) {
		String src=result.getString(tab_sub_name+"_img_link");
		String alt=result.getString(tab_sub_name+"_kit_descrp");
		Integer pr=result.getInt("price");
		Integer pr_coin=result.getInt("price_coin");
		
		
		pile.add(new Dis(src, alt, pr, pr_coin));
		

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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
	}else if(pile.size()<3){
		System.out.println(pile.get(0).alt_arr);
		System.out.println("Do nothing!");
		
		System.out.println(pile.size());
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
		model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//     	model.addAttribute("width",pile.size());
		model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
		
		
		System.out.println(pile.size());
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
		model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//     	model.addAttribute("width",pile.size());
     	model.addAttribute("mult",pile.size()-1);
     	model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
     	
	}
	
	connection.close();
	prepare.close();
	result.close();
	
}



public void size_hp(List<Dis> pile,Model model,HttpServletRequest request,String tab_name,String tab_sub_name,Integer num,String text,Integer num_extra,Integer num_exx) throws SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		System.out.println("Postgresql not found");
		e.printStackTrace();
	}
	
	System.out.println("Testing connection to Postgresql JDBC");
	
	if(num_exx!=null) {
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		prepare.setInt(3, num_exx);	
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
	}else {
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
	}else if(pile.size()<3){
		System.out.println(pile.get(0).alt_arr);
		System.out.println("Do nothing!");
		
		System.out.println(pile.size());
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
		model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//     	model.addAttribute("width",pile.size());
		model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
		
		
		System.out.println(pile.size());
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
		model.addAttribute("img_source", pile.stream().sorted(Comparator.comparing(Dis::getPr_arr).reversed()).collect(Collectors.toList()));
//     	model.addAttribute("width",pile.size());
     	model.addAttribute("mult",pile.size()-1);
     	model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
     	
	}
	
	connection.close();
	
	
}




public void size_ni(List<Dis> pile,Model model,HttpServletRequest request,String tab_name,String tab_sub_name,Integer num,String text) throws SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		System.out.println("Postgresql not found");
		e.printStackTrace();
	}
	
	System.out.println("Testing connection to Postgresql JDBC");
	
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	prepare.setInt(1, num);
	ResultSet result=prepare.executeQuery();
	
	while(result.next()) {
		String src=result.getString(tab_sub_name+"_img_link");
		String alt=result.getString(tab_sub_name+"_kit_descrp");
		Integer pr=result.getInt("price");
		Integer pr_coin=result.getInt("price_coin");
		
		
		pile.add(new Dis(src, alt, pr, pr_coin));
		

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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
     	
	}
	
	connection.close();
	prepare.close();
	result.close();
}



public void size_ni(List<Dis> pile,Model model,HttpServletRequest request,String tab_name,String tab_sub_name,Integer num,String text,Integer num_extra,Integer num_exx) throws SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		System.out.println("Postgresql not found");
		e.printStackTrace();
	}
	
	System.out.println("Testing connection to Postgresql JDBC");
	
	if(num_exx!=null) {
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		prepare.setInt(3, num_exx);	
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
	}else {
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
     	
	}
	
	connection.close();
	
	
}




public void size_12(List<Dis> pile,Model model,HttpServletRequest request,String tab_name,String tab_sub_name,Integer num,String text) throws SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		System.out.println("Postgresql not found");
		e.printStackTrace();
	}
	
	System.out.println("Testing connection to Postgresql JDBC");
	
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	prepare.setInt(1, num);
	ResultSet result=prepare.executeQuery();
	
	while(result.next()) {
		String src=result.getString(tab_sub_name+"_img_link");
		String alt=result.getString(tab_sub_name+"_kit_descrp");
		Integer pr=result.getInt("price");
		Integer pr_coin=result.getInt("price_coin");
		
		if(result.isAfterLast()) {
			break;
		}
		
		if(pile.size()==12) {
			break;
		}
		
		pile.add(new Dis(src, alt, pr, pr_coin));
		

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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
     	
	}
	
	connection.close();
	prepare.close();
	result.close();
}




public void size_12(List<Dis> pile,Model model,HttpServletRequest request,String tab_name,String tab_sub_name,Integer num,String text,Integer num_extra,Integer num_exx) throws SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		System.out.println("Postgresql not found");
		e.printStackTrace();
	}
	
	System.out.println("Testing connection to Postgresql JDBC");
	
	if(num_exx!=null) {
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		prepare.setInt(3, num_exx);	
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_kit_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			if(result.isAfterLast()) {
				break;
			}
			
			if(pile.size()==12) {
				break;
			}
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			

		}
			
		prepare.close();
		result.close();
	}else {
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_kit_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			if(result.isAfterLast()) {
				break;
			}
			
			if(pile.size()==12) {
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
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//		model.addAttribute("img_source", pile);
//     	model.addAttribute("width",pile.size());
		model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
     	
	}
	
	connection.close();
}





public void size_24(List<Dis> pile,Model model,HttpServletRequest request,String tab_name,String tab_sub_name,Integer num,String text) throws SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		System.out.println("Postgresql not found");
		e.printStackTrace();
	}
	
	System.out.println("Testing connection to Postgresql JDBC");
	
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	prepare.setInt(1, num);
	ResultSet result=prepare.executeQuery();
	
	while(result.next()) {
		String src=result.getString(tab_sub_name+"_img_link");
		String alt=result.getString(tab_sub_name+"_kit_descrp");
		Integer pr=result.getInt("price");
		Integer pr_coin=result.getInt("price_coin");
		
		if(result.isAfterLast()) {
			break;
		}
		
		if(pile.size()==24) {
			break;
		}
		
		pile.add(new Dis(src, alt, pr, pr_coin));
		

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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
     	
	}
	
	connection.close();
	prepare.close();
	result.close();
}




public void size_24(List<Dis> pile,Model model,HttpServletRequest request,String tab_name,String tab_sub_name,Integer num,String text,Integer num_extra,Integer num_exx) throws SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		System.out.println("Postgresql not found");
		e.printStackTrace();
	}
	
	System.out.println("Testing connection to Postgresql JDBC");
	
	if(num_exx!=null) {
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		prepare.setInt(3, num_exx);	
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_kit_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			if(result.isAfterLast()) {
				break;
			}
			
			if(pile.size()==24) {
				break;
			}
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			

		}
			
		prepare.close();
		result.close();
	}else {
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_kit_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			if(result.isAfterLast()) {
				break;
			}
			
			if(pile.size()==24) {
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
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//		model.addAttribute("img_source", pile);
//     	model.addAttribute("width",pile.size());
		model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
     	
	}
	
	connection.close();
}





public void size_36(List<Dis> pile,Model model,HttpServletRequest request,String tab_name,String tab_sub_name,Integer num,String text) throws SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		System.out.println("Postgresql not found");
		e.printStackTrace();
	}
	
	System.out.println("Testing connection to Postgresql JDBC");
	
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	prepare.setInt(1, num);
	ResultSet result=prepare.executeQuery();
	
	while(result.next()) {
		String src=result.getString(tab_sub_name+"_img_link");
		String alt=result.getString(tab_sub_name+"_kit_descrp");
		Integer pr=result.getInt("price");
		Integer pr_coin=result.getInt("price_coin");
		
		if(result.isAfterLast()) {
			break;
		}
		
		if(pile.size()==36) {
			break;
		}
		
		pile.add(new Dis(src, alt, pr, pr_coin));
		

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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
     	
	}
	
	connection.close();
	prepare.close();
	result.close();
}




public void size_36(List<Dis> pile,Model model,HttpServletRequest request,String tab_name,String tab_sub_name,Integer num,String text,Integer num_extra,Integer num_exx) throws SQLException {
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	Connection connection=null;
	
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		System.out.println("Postgresql not found");
		e.printStackTrace();
	}
	
	System.out.println("Testing connection to Postgresql JDBC");
	
	if(num_exx!=null) {
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		prepare.setInt(3, num_exx);	
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_kit_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			if(result.isAfterLast()) {
				break;
			}
			
			if(pile.size()==36) {
				break;
			}
			
			pile.add(new Dis(src, alt, pr, pr_coin));
			

		}
			
		prepare.close();
		result.close();
	}else {
		connection = DriverManager.getConnection(DB_URL,User,Pass);
		PreparedStatement prepare=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE category_id=(?) OR category_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		prepare.setInt(1, num);
		prepare.setInt(2, num_extra);
		ResultSet result=prepare.executeQuery();
		
		while(result.next()) {
			String src=result.getString(tab_sub_name+"_img_link");
			String alt=result.getString(tab_sub_name+"_kit_descrp");
			Integer pr=result.getInt("price");
			Integer pr_coin=result.getInt("price_coin");
			
			if(result.isAfterLast()) {
				break;
			}
			
			if(pile.size()==36) {
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
//		pile.stream().sorted(Comparator.comparing(Dis::getPr_arr)).collect(Collectors.toList());
//		model.addAttribute("img_source", pile);
//     	model.addAttribute("width",pile.size());
		model.addAttribute("low_price",text+"_lp");
     	model.addAttribute("high_price",text+"_hp");
     	model.addAttribute("new_item",text+"_ni");
     	model.addAttribute("size_one",text+"_12");
     	model.addAttribute("size_two",text+"_24");
     	model.addAttribute("size_three",text+"_36");
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
		
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
     	model.addAttribute("mass",tab_name+"_"+text);
     	model.addAttribute("item",tab_name+"_"+text);
     	model.addAttribute("url","_"+tab_sub_name);
     	
	}
	
	connection.close();
}


public void filters(List<Dis> pile,Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	String page=request.getHeader("Referer");
	String heq=URLDecoder.decode(page, "UTF-8");
	System.out.println(page);
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	
	ArrayList<Integer> cat_id=new ArrayList<Integer>(); 
	ArrayList<String> cat_name =new ArrayList<String>();
	
	
	Connection connection=null;
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement slt=connection.prepareStatement("SELECT * FROM category",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	ResultSet rst=slt.executeQuery();
		while(rst.next()) {
			String category_name=rst.getString("category_name");
			Integer category_id=rst.getInt("category_id");
			if(heq.contains(category_name)==true) {
				System.out.println(category_name);
				System.out.println(category_id);
				System.out.println("");
				cat_id.add(category_id);
				cat_name.add(category_name);
				System.out.println(cat_name.get(0).toString());
				System.out.println(cat_id.get(0).intValue());
				break;
			}
		}
	slt.close();
	rst.close();
	connection.close();
	
	
	if(heq.contains("away")) {
		String tab_name="away_kits";
		String tab_sub_name="away";
		
		if(heq.contains("_lp")) {
			size_lp(pile, model, request, tab_name, tab_sub_name, cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_hp")) {
			size_hp(pile, model, request, tab_name, tab_sub_name, cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_ni")) {
			size_ni(pile, model, request, tab_name, tab_sub_name, cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_12")) {
			size_12(pile,model,request,tab_name,tab_sub_name,cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_24")) {
			size_24(pile,model,request,tab_name,tab_sub_name,cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_36")) {
			size_36(pile,model,request,tab_name,tab_sub_name,cat_id.get(0).intValue(),cat_name.get(0).toString());
		}
	}else if(heq.contains("home")) {
		String tab_name="home_kits";
		String tab_sub_name="home";
		
		if(heq.contains("_lp")) {
			size_lp(pile, model, request, tab_name, tab_sub_name, cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_hp")) {
			size_hp(pile, model, request, tab_name, tab_sub_name, cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_ni")) {
			size_ni(pile, model, request, tab_name, tab_sub_name, cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_12")) {
			size_12(pile,model,request,tab_name,tab_sub_name,cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_24")) {
			size_24(pile,model,request,tab_name,tab_sub_name,cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_36")) {
			size_36(pile,model,request,tab_name,tab_sub_name,cat_id.get(0).intValue(),cat_name.get(0).toString());
		}
	}else if(heq.contains("third")) {
		String tab_name="third_kits";
		String tab_sub_name="third";
		
		if(heq.contains("_lp")) {
			size_lp(pile, model, request, tab_name, tab_sub_name, cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_hp")) {
			size_hp(pile, model, request, tab_name, tab_sub_name, cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_ni")) {
			size_ni(pile, model, request, tab_name, tab_sub_name, cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_12")) {
			size_12(pile,model,request,tab_name,tab_sub_name,cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_24")) {
			size_24(pile,model,request,tab_name,tab_sub_name,cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_36")) {
			size_36(pile,model,request,tab_name,tab_sub_name,cat_id.get(0).intValue(),cat_name.get(0).toString());
		}
	}else if(heq.contains("keeper")) {
		String tab_name="keeper_kits";
		String tab_sub_name="keeper";
		if(heq.contains("_lp")) {
			size_lp(pile, model, request, tab_name, tab_sub_name, cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_hp")) {
			size_hp(pile, model, request, tab_name, tab_sub_name, cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_ni")) {
			size_ni(pile, model, request, tab_name, tab_sub_name, cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_12")) {
			size_12(pile,model,request,tab_name,tab_sub_name,cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_24")) {
			size_24(pile,model,request,tab_name,tab_sub_name,cat_id.get(0).intValue(),cat_name.get(0).toString());
		}else if(heq.contains("_36")) {
			size_36(pile,model,request,tab_name,tab_sub_name,cat_id.get(0).intValue(),cat_name.get(0).toString());
		}
	}
	
}





public void filters_kit(List<Dis> pile,Model model,HttpServletRequest request,Integer num,Integer num_extra,Integer num_exx) throws UnsupportedEncodingException, SQLException {
	String page=request.getHeader("Referer");
	String heq=URLDecoder.decode(page, "UTF-8");
	System.out.println(page);
	String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	String User="user";
	String Pass="user";
	
	ArrayList<Integer> cat_id=new ArrayList<Integer>(); 
	ArrayList<String> cat_name =new ArrayList<String>();
	
	
	Connection connection=null;
	connection = DriverManager.getConnection(DB_URL,User,Pass);
	PreparedStatement slt=connection.prepareStatement("SELECT * FROM category",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	ResultSet rst=slt.executeQuery();
		while(rst.next()) {
			String category_name=rst.getString("category_name");
			Integer category_id=rst.getInt("category_id");
			if(heq.contains(category_name)==true) {
				System.out.println(category_name);
				System.out.println(category_id);
				System.out.println("");
				cat_id.add(category_id);
				cat_name.add(category_name);
				System.out.println(cat_name.get(0).toString());
				System.out.println(cat_id.get(0).intValue());
				break;
			}
		}
	slt.close();
	rst.close();
	connection.close();
	
	
	if(heq.contains("away")) {
		String tab_name="away_kits";
		String tab_sub_name="away";
		
		if(heq.contains("_lp")) {
			size_lp(pile, model, request, tab_name, tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_hp")) {
			size_hp(pile, model, request, tab_name, tab_sub_name, num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_ni")) {
			size_ni(pile, model, request, tab_name, tab_sub_name, num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_12")) {
			size_12(pile,model,request,tab_name,tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_24")) {
			size_24(pile,model,request,tab_name,tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_36")) {
			size_36(pile,model,request,tab_name,tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}
	}else if(heq.contains("home")) {
		String tab_name="home_kits";
		String tab_sub_name="home";
		
		if(heq.contains("_lp")) {
			size_lp(pile, model, request, tab_name, tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_hp")) {
			size_hp(pile, model, request, tab_name, tab_sub_name, num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_ni")) {
			size_ni(pile, model, request, tab_name, tab_sub_name, num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_12")) {
			size_12(pile,model,request,tab_name,tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_24")) {
			size_24(pile,model,request,tab_name,tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_36")) {
			size_36(pile,model,request,tab_name,tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}
	}else if(heq.contains("third")) {
		String tab_name="third_kits";
		String tab_sub_name="third";
		
		if(heq.contains("_lp")) {
			size_lp(pile, model, request, tab_name, tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_hp")) {
			size_hp(pile, model, request, tab_name, tab_sub_name, num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_ni")) {
			size_ni(pile, model, request, tab_name, tab_sub_name, num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_12")) {
			size_12(pile,model,request,tab_name,tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_24")) {
			size_24(pile,model,request,tab_name,tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_36")) {
			size_36(pile,model,request,tab_name,tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}
	}else if(heq.contains("keeper")) {
		String tab_name="keeper_kits";
		String tab_sub_name="keeper";
		
		if(heq.contains("_lp")) {
			size_lp(pile, model, request, tab_name, tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_hp")) {
			size_hp(pile, model, request, tab_name, tab_sub_name, num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_ni")) {
			size_ni(pile, model, request, tab_name, tab_sub_name, num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_12")) {
			size_12(pile,model,request,tab_name,tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_24")) {
			size_24(pile,model,request,tab_name,tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}else if(heq.contains("_36")) {
			size_36(pile,model,request,tab_name,tab_sub_name,num,cat_name.get(0).toString(),num_extra,num_exx);
		}
	}
	
}





	

@GetMapping("/full_kits")
public String full_kits(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Full Kits Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED FULL KITS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
	func_subdep(pile,model,request,4,9,"Full Kits");
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


@GetMapping("/shirts")
public String shirts(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shirts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHIRTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
	func_subdep(pile,model,request,6,"Shirt");
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


@GetMapping("/shorts")
public String shorts(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shorts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHORTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
	func_subdep(pile,model,request,7,"Shorts");
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
	
	

@GetMapping("/socks")
public String socks(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Socks Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SOCKS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
	func_subdep(pile,model,request,8,"Socks");
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


@GetMapping("/men")
public String men(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Mens Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED MEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
	func_subdep(pile,model,request,6,7,0,"Men");
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


@GetMapping("/women")
public String women(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Ladies Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
	func_subdep(pile,model,request,2,"Womens");
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



@GetMapping("/kids")
public String kids(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Kids Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED KIDS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
	func_subdep(pile,model,request,3,"Kids");
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




@GetMapping("/baby")
public String baby(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Baby Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED Babies FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
	func_subdep(pile,model,request,4,"Babykit");
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


@GetMapping("/Full Kits_lp")
public String Full_Kits_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Full Kits Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED FULL KITS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters_kit(pile,model,request,4,9,null);
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

@GetMapping("/Full Kits_hp")
public String Full_Kits_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Full Kits Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED FULL KITS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters_kit(pile,model,request,4,9,null);
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


@GetMapping("/Full Kits_ni")
public String Full_Kits_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Full Kits Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED FULL KITS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters_kit(pile,model,request,4,9,null);
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


@GetMapping("/Full Kits_12")
public String Full_Kits_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Full Kits Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED FULL KITS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters_kit(pile,model,request,4,9,null);
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



@GetMapping("/Full Kits_24")
public String Full_Kits_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Full Kits Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED FULL KITS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters_kit(pile,model,request,4,9,null);
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


@GetMapping("/Full Kits_36")
public String Full_Kits_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Full Kits Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED FULL KITS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters_kit(pile,model,request,4,9,null);
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


@GetMapping("/Men_lp")
public String Men_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Mens Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED MEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters_kit(pile,model,request,6,7,0);
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


@GetMapping("/Men_hp")
public String Men_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Mens Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED MEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters_kit(pile,model,request,6,7,0);
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


@GetMapping("/Men_ni")
public String Men_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Mens Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED MEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters_kit(pile,model,request,6,7,0);
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


@GetMapping("/Men_12")
public String Men_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Mens Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED MEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters_kit(pile,model,request,6,7,0);
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


@GetMapping("/Men_24")
public String Men_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Mens Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED MEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters_kit(pile,model,request,6,7,0);
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


@GetMapping("/Men_36")
public String Men_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Mens Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED MEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters_kit(pile,model,request,6,7,0);
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


@GetMapping("/Womens_lp")
public String Womens_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Ladies Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Womens_hp")
public String Womens_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Ladies Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Womens_ni")
public String Womens_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Ladies Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Womens_12")
public String Womens_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Ladies Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Womens_24")
public String Womens_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Ladies Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Womens_36")
public String Womens_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Ladies Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED WOMEN FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Kids_lp")
public String Kids_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Kids Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED KIDS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Kids_hp")
public String Kids_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Kids Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED KIDS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Kids_ni")
public String Kids_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Kids Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED KIDS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Kids_12")
public String Kids_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Kids Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED KIDS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Kids_24")
public String Kids_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Kids Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED KIDS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Kids_36")
public String Kids_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Kids Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED KIDS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Shirt_lp")
public String Shirt_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shirts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHIRTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Shirt_hp")
public String Shirt_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shirts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHIRTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Shirt_ni")
public String Shirt_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shirts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHIRTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Shirt_12")
public String Shirt_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shirts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHIRTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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



@GetMapping("/Shirt_24")
public String Shirt_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shirts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHIRTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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



@GetMapping("/Shirt_36")
public String Shirt_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shirts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHIRTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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



@GetMapping("/Shorts_lp")
public String Shorts_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shorts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHORTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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



@GetMapping("/Shorts_hp")
public String Shorts_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shorts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHORTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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



@GetMapping("/Shorts_ni")
public String Shorts_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shorts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHORTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Shorts_12")
public String Shorts_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shorts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHORTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Shorts_24")
public String Shorts_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shorts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHORTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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



@GetMapping("/Shorts_36")
public String Shorts_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Shorts Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SHORTS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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

@GetMapping("/Socks_lp")
public String Socks_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Socks Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SOCKS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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



@GetMapping("/Socks_hp")
public String Socks_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Socks Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SOCKS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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



@GetMapping("/Socks_ni")
public String Socks_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Socks Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SOCKS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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



@GetMapping("/Socks_12")
public String Socks_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Socks Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SOCKS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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



@GetMapping("/Socks_24")
public String Socks_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Socks Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SOCKS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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



@GetMapping("/Socks_36")
public String Socks_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Socks Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED SOCKS FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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



@GetMapping("/Babykit_lp")
public String Babykit_lp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Baby Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED Babies FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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
	

@GetMapping("/Babykit_hp")
public String Babykit_hp(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Baby Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED Babies FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Babykit_ni")
public String Babykit_ni(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Baby Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED Babies FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Babykit_12")
public String Babykit_12(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Baby Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED Babies FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Babykit_24")
public String Babykit_24(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Baby Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED Babies FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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


@GetMapping("/Babykit_36")
public String Babykit_36(Model model,HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
	model.addAttribute("title", "Manchester United Baby Football Kits");
	model.addAttribute("direct_name", "MANCHESTER UNITED Babies FOOTBALL KITS");
    List<Dis> pile =new ArrayList<Dis>();
    filters(pile,model,request);
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