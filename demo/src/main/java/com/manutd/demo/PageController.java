package com.manutd.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {
	@RequestMapping("/help")
	public String help() {
	    return "help"; //help.html page name to open it
	}
	
	@RequestMapping("/affiliate")
	public String affiliate() {
	    return "affiliate"; //defect-details.html page name to open it
	}
	
	@RequestMapping("size_chart")
	public String size_chart() {
	    return "size_chart"; //defect-details.html page name to open it
	}
	
	@RequestMapping("track")
	public String track() {
	    return "track"; //defect-details.html page name to open it
	}
}
