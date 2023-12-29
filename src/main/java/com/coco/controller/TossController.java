package com.coco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/toss")
public class TossController {

	@GetMapping("/checkout.html")
	public String showCheckoutPage() {
		return "toss/checkout";
	}
}
