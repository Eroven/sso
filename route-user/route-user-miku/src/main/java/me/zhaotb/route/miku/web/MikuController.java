package me.zhaotb.route.miku.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MikuController {
	
	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("after.jsp");
	}
	
	
}
