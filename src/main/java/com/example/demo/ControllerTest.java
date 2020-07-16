package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerTest {
	@RequestMapping("/MyFirstPage")
	public String greeting(@RequestParam(value="title", required=false, defaultValue="default") String title, Model model) {
		model.addAttribute("name", title);
		return "index";
	}
}
