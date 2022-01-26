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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.manutd.demo.SearchController.Dis;

@Controller
public class SLinkController {
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
	
	
	public void func(Model model,String tab_name, String tab_string, String req,List<Dis> list) throws UnsupportedEncodingException {

		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	    String User="user";
	    String Pass="user";
	    String heq=URLDecoder.decode(req, "UTF-8");
	    System.out.println(heq);
	    try {
			Connection connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement pre=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE "+tab_string+"_descrp=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pre.setString(1, heq.replaceAll("http://localhost:8082/", ""));
			ResultSet re=pre.executeQuery();
			while(re.next()) {
				System.out.println("/////////");
			 	String src_third=re.getString(tab_string+"_img_link");
				String alt_third=re.getString(tab_string+"_descrp");
				Integer pr_third=re.getInt("price");
				Integer pr_coin_third=re.getInt("price_coin");
				System.out.println("");
				System.out.println(alt_third);
				
				if(re.isAfterLast()) {
					break;
				}
				System.out.println("/////////");
				System.out.println(alt_third);
				
					list.add(new Dis(src_third, alt_third, pr_third, pr_coin_third));
				
				
			 }
			 
//			model.addAttribute("img_source", list);
			pre.close();
			re.close();
		
			
	    }catch(SQLException w) {
			w.printStackTrace();
		}
	}
	
	
	
	
	public void func(Model model,String tab_name, String tab_string,String tab_key, String req,List<Dis> list,HttpServletRequest request) throws UnsupportedEncodingException {

		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	    String User="user";
	    String Pass="user";
	    String heq=URLDecoder.decode(req, "UTF-8");
	    System.out.println(heq);
	    try {
			Connection connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement pre=connection.prepareStatement("SELECT * FROM "+tab_name+" WHERE "+tab_string+"_descrp=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pre.setString(1, heq.replaceAll("http://localhost:8082/", ""));
			ResultSet re=pre.executeQuery();
			while(re.next()) {
				System.out.println("/////////");
			 	String src_third=re.getString(tab_key+"_img_link");
				String alt_third=re.getString(tab_string+"_descrp");
				Integer pr_third=re.getInt("price");
				Integer pr_coin_third=re.getInt("price_coin");
				 System.out.println("");
				 System.out.println(alt_third);
				if(re.isAfterLast()) {
					break;
				}
				
		
					list.add(new Dis(src_third, alt_third, pr_third, pr_coin_third));

				
			 }
			 
//			model.addAttribute("img_source", list);
			pre.close();
			re.close();

			
			
	    }catch(SQLException w) {
			w.printStackTrace();
		}
	}
	
	
	@GetMapping("/link")
	public String link(Model model,HttpServletRequest request) throws IOException, SQLException {
		List<Dis> list = new ArrayList<Dis>(); 
		model.addAttribute("title", "Manchester United");
		model.addAttribute("direct_name", "Manchester United");
	    String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	    String User="user";
	    String Pass="user";
	    String req = request.getHeader("referer");
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
			 

			 if(req.isBlank()) {
				 model.addAttribute("direct_name","The link is incorrect!");
			 }else {
				 func(model,"away_kits","away_kit","away",req,list,request);
				 func(model,"keeper_kits","keeper_kit","keeper",req,list,request);
				 func(model,"home_kits","home_kit","home",req,list,request);
				 func(model,"third_kits","third_kit","third",req,list,request);
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
//				 model.addAttribute("img_source", list);
				 model.addAttribute("img_source_src_arr", list.get(0).src_arr);
				 model.addAttribute("img_source_alt_arr", list.get(0).alt_arr);
				 model.addAttribute("img_source_pr_arr", list.get(0).pr_arr);
				 model.addAttribute("img_source_pr_coin_arr", list.get(0).pr_coin_arr);
//		pre.close();
//		re.close();
			 }
			connection.close();
		}else {
			System.out.println("The database is empty!");
		}
			
			

		return "basket";
		
  }
	
	
}
