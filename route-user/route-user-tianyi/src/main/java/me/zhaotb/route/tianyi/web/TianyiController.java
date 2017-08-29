package me.zhaotb.route.tianyi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TianyiController {

	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("after.jsp");
	}
}
