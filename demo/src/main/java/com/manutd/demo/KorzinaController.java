package com.manutd.demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.manutd.demo.SLinkController.Dis;

@Controller
public class KorzinaController {
	
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
	
	
	public class Fis{
	    /**
		 * 
		 */
		String alt_arr;
	    Integer pr_arr;  
	    Integer pr_coin_arr;
		String size_arr;
		Integer quan_arr;
		String src_arr;  
		
		
	    public Fis(String alt_arr, Integer pr_arr, Integer pr_coin_arr, String size_arr, Integer quan_arr,
				String src_arr) {
			super();
			this.alt_arr = alt_arr;
			this.pr_arr = pr_arr;
			this.pr_coin_arr = pr_coin_arr;
			this.size_arr = size_arr;
			this.quan_arr = quan_arr;
			this.src_arr = src_arr;
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
		public String getSize_arr() {
			return size_arr;
		}
		public void setSize_arr(String size_arr) {
			this.size_arr = size_arr;
		}
		public Integer getQuan_arr() {
			return quan_arr;
		}
		public void setQuan_arr(Integer quan_arr) {
			this.quan_arr = quan_arr;
		}
	
	};
	
	
	public void func(Model model,String tab_name, String tab_string, String req,List<Dis> list) throws UnsupportedEncodingException {

		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	    String User="user";
	    String Pass="user";
	    String heq=URLDecoder.decode(req, "UTF-8");
	    System.out.println(heq.replaceAll("http://localhost:8082/", ""));
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
	    System.out.println(heq.replaceAll("http://localhost:8082/", ""));
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
	
	
	@GetMapping("/korz")
	public String korz(Model model,HttpServletRequest request,HttpSession session) throws SQLException, UnsupportedEncodingException{
		String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	    String User="user";
	    String Pass="user";
	    List<Dis> list = new ArrayList<Dis>();
	    List<Fis> dist = new ArrayList<Fis>(); 
		String size = request.getParameter("radio");
		String quan = request.getParameter("quantity");
		String ses_id=request.getRequestedSessionId();
		System.out.println(size);
		System.out.println(quan);
		System.out.println();
		System.out.println(session.getId());
		boolean is_here=false;
		boolean already_here=false;
		String req = request.getHeader("referer");
		String hq=URLDecoder.decode(req, "UTF-8");
		System.out.println(req.replaceAll("http://localhost:8082/", ""));
		 try {
				Connection connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement pre=connection.prepareStatement("SELECT * FROM basket",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet re=pre.executeQuery();
				
				while(re.next()) {
					
					String id=re.getString("order_id");
					String alt=re.getString("order_descrp");
					if(id.equals(ses_id)) {
						//The user is here!
						is_here=true;
						if(hq.replaceAll("http://localhost:8082/", "").equals(alt)) {
							already_here=true;
						}
						
					}
				}
				connection.close();
				pre.close();
				re.close();
			}catch(SQLException w) {
				w.printStackTrace();	
			}
		 
		 	System.out.println(is_here);
		 	System.out.println();
		 	System.out.println(already_here);
			try {
				Class.forName("org.postgresql.Driver");
			}catch(ClassNotFoundException e) {
				System.out.println("Postgresql not found");
				e.printStackTrace();
//				return "players";
			}
			
			if(is_here==true) {
				Connection connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement pre=connection.prepareStatement("SELECT * FROM basket WHERE order_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				pre.setString(1, ses_id);
				ResultSet re=pre.executeQuery();
				
				if(req.isBlank()) {
					//THE req is empty!!!	
				}else {
					
					if(already_here==false) {	
						
						 func(model,"keeper_kits","keeper_kit","keeper",req,list,request);
						 func(model,"home_kits","home_kit","home",req,list,request);
						 func(model,"away_kits","away_kit","away",req,list,request);
						 func(model,"third_kits","third_kit","third",req,list,request);
						 func(model,"man_headwear","man_headwear",req,list);
						 func(model,"man_headwear","man_headwear",req,list);
						 func(model,"man_jackets","man_jackets",req,list);
						 func(model,"women_tops","women_top",req,list);
						 func(model,"women_headwear","women_headwear",req,list);
						 func(model,"women_scarves","women_scarve",req,list);
						 func(model,"kids_football_kits","kids_football_kit",req,list);
						 func(model,"kids_jackets","kids_jacket",req,list);
						 func(model,"kids_tracksuits","kids_tracksuit",req,list);
						 
						 PreparedStatement pred=connection.prepareStatement("INSERT INTO basket(order_id,order_img_link,price,price_coin,order_size,order_quantity,order_descrp,life_time) VALUES((?),(?),(?),(?),(?),(?),(?),now()+interval '15 second')");
							
							System.out.println(ses_id.toString());
								pred.setString(1, ses_id.toString());
								pred.setString(2, list.get(0).src_arr);
								pred.setInt(3, list.get(0).pr_arr);
								pred.setInt(4, list.get(0).pr_coin_arr);
								pred.setString(5, size);
								pred.setInt(6, Integer.parseInt(quan));
								pred.setString(7, list.get(0).alt_arr);
	//							pred.setString(8, "");
								pred.executeUpdate();
								dist.add(new Fis(list.get(0).alt_arr, list.get(0).pr_arr,list.get(0).pr_coin_arr,size,Integer.parseInt(quan),list.get(0).src_arr));
						}else {
							///sorry this thing is here already
						}
					}
				
				while(re.next()) {
					String src_arr=re.getString("order_img_link");
					int pr_arr=re.getInt("price");
					int pr_coin_arr=re.getInt("price_coin");
					String size_arr =re.getString("order_size");
					int order_quantity=re.getInt("order_quantity");
					String alt_arr=re.getString("order_descrp");
					
					
					
					dist.add(new Fis(alt_arr, pr_arr, pr_coin_arr, size_arr, order_quantity, src_arr));
				}
				
				PreparedStatement prepare=connection.prepareStatement("DELETE FROM basket WHERE life_time<current_time - INTERVAL '15 minutes'");
				prepare.executeUpdate();
				
				model.addAttribute("img_source", dist);
				
				
				connection.close();
				pre.close();
				re.close();
				prepare.close();
				
			}else {
				 func(model,"keeper_kits","keeper_kit","keeper",req,list,request);
				 func(model,"home_kits","home_kit","home",req,list,request);
				 func(model,"away_kits","away_kit","away",req,list,request);
				 func(model,"third_kits","third_kit","third",req,list,request);
				 func(model,"man_headwear","man_headwear",req,list);
				 func(model,"man_headwear","man_headwear",req,list);
				 func(model,"man_jackets","man_jackets",req,list);
				 func(model,"women_tops","women_top",req,list);
				 func(model,"women_headwear","women_headwear",req,list);
				 func(model,"women_scarves","women_scarve",req,list);
				 func(model,"kids_football_kits","kids_football_kit",req,list);
				 func(model,"kids_jackets","kids_jacket",req,list);
				 func(model,"kids_tracksuits","kids_tracksuit",req,list);
				 
//				System.out.println(list.get(0).alt_arr);	
				////////////////CHECK
				
				
				
				 
				Connection connection = DriverManager.getConnection(DB_URL,User,Pass);
				PreparedStatement pre=connection.prepareStatement("INSERT INTO basket(order_id,order_img_link,price,price_coin,order_size,order_quantity,order_descrp,life_time) VALUES((?),(?),(?),(?),(?),(?),(?),now()+interval '15 second')");
				
				System.out.println(ses_id.toString());
					pre.setString(1, ses_id.toString());
					pre.setString(2, list.get(0).src_arr);
					pre.setInt(3, list.get(0).pr_arr);
					pre.setInt(4, list.get(0).pr_coin_arr);
					pre.setString(5, size);
					pre.setInt(6, Integer.parseInt(quan));
					pre.setString(7, list.get(0).alt_arr);
//					pre.setString(8, "");
					pre.executeUpdate();
					dist.add(new Fis(list.get(0).alt_arr, list.get(0).pr_arr,list.get(0).pr_coin_arr,size,Integer.parseInt(quan),list.get(0).src_arr));
					

					PreparedStatement prepare=connection.prepareStatement("DELETE FROM basket WHERE life_time<current_time - INTERVAL '15 minutes'");
					prepare.executeUpdate();
					
//					System.out.println(dist.size());
					
					model.addAttribute("img_source", dist);
					
				connection.close();
				pre.close();
			
				prepare.close();
				
			}
		
		
		return "/korzina";
	}
	
	@GetMapping("/Delete_basket")
	public String Delete_basket(Model model,HttpServletRequest request) throws IOException, SQLException {
		model.addAttribute("title", "Manchester United");
		model.addAttribute("direct_name", "Manchester United");
	    List<Fis> dist = new ArrayList<Fis>(); 
	    String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	    String User="user";
	    String Pass="user";
	    String req = request.getHeader("referer");
	    String heq=URLDecoder.decode(req, "UTF-8");
		String ses_id=request.getRequestedSessionId();
		System.out.println("Testing connection to Postgresql JDBC");
			try {
				Class.forName("org.postgresql.Driver");
			}catch(ClassNotFoundException e) {
				System.out.println("Postgresql not found");
				e.printStackTrace();
//				return "players";
			}
			
			Connection connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement pred=connection.prepareStatement("DELETE FROM basket WHERE order_descrp=(?) AND order_id=(?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pred.setString(1, heq.replaceAll("http://localhost:8082/", ""));
			pred.setString(2, ses_id);
			pred.execute();
			connection.close();
			
			Connection con= DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement led=con.prepareStatement("SELECT * FROM basket",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			ResultSet re=led.executeQuery();
		
			System.out.println("DELETE THE THING");
			re.beforeFirst();
			while(re.next()) {
				String src_arr=re.getString("order_img_link");
				int pr_arr=re.getInt("price");
				int pr_coin_arr=re.getInt("price_coin");
				String size_arr =re.getString("order_size");
				int order_quantity=re.getInt("order_quantity");
				String alt_arr=re.getString("order_descrp");
				System.out.println();
				System.out.println(alt_arr);
				
				
				dist.add(new Fis(alt_arr, pr_arr, pr_coin_arr, size_arr, order_quantity, src_arr));
			}
			
			PreparedStatement prepare=con.prepareStatement("DELETE FROM basket WHERE life_time<current_time - INTERVAL '15 minutes'");
			prepare.executeUpdate();
			
			if(dist.size()==0) {
				model.addAttribute("direct", "Basket is empty!");
			}else {
				model.addAttribute("img_source", dist);
			}
			
			
		con.close();
		led.close();
		re.close();
		pred.close();
		prepare.close();
//		rst.close()	
			
			return "korzina";
			
			
			
			
	}

	
	@GetMapping("/Show_basket")
	public String Show_basket(Model model,HttpServletRequest request) throws IOException, SQLException {
		model.addAttribute("title", "Manchester United");
		model.addAttribute("direct_name", "Manchester United");
	    List<Fis> dist = new ArrayList<Fis>(); 
	    String DB_URL="jdbc:postgresql://localhost:5432/manchester";
	    String User="user";
	    String Pass="user";
	    String req = request.getHeader("referer");
	    String heq=URLDecoder.decode(req, "UTF-8");
		System.out.println("Testing connection to Postgresql JDBC");
			try {
				Class.forName("org.postgresql.Driver");
			}catch(ClassNotFoundException e) {
				System.out.println("Postgresql not found");
				e.printStackTrace();
//				return "players";
			}
			
			Connection connection = DriverManager.getConnection(DB_URL,User,Pass);
			PreparedStatement pre=connection.prepareStatement("SELECT * FROM basket",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			ResultSet re=pre.executeQuery();
		
			while(re.next()) {
				String src_arr=re.getString("order_img_link");
				int pr_arr=re.getInt("price");
				int pr_coin_arr=re.getInt("price_coin");
				String size_arr =re.getString("order_size");
				int order_quantity=re.getInt("order_quantity");
				String alt_arr=re.getString("order_descrp");
				
				
				
				dist.add(new Fis(alt_arr, pr_arr, pr_coin_arr, size_arr, order_quantity, src_arr));
			}
			
			PreparedStatement prepare=connection.prepareStatement("DELETE FROM basket WHERE life_time<current_time - INTERVAL '15 minutes'");
			prepare.executeUpdate();
			
			if(dist.size()==0) {
				model.addAttribute("direct", "Basket is empty!");
			}else {
				model.addAttribute("img_source", dist);
			}
			
			
		connection.close();
		pre.close();
		re.close();
		prepare.close();
//		rst.close()	
			
		
			return "korzina";
			
			
			
			
	}
	
	
	
}
