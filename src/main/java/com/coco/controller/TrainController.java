package com.coco.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coco.domain.City;
import com.coco.service.CityService;
@Controller
@RequestMapping("/ticket")
public class TrainController {

	private CityService cityService;
	
	@GetMapping("/search")
	public String search(Model model) {
		model.addAttribute("citys", cityService.getAllCityes());
		return "search";
	}
	
	@GetMapping("/regions")
    @ResponseBody
    public List<City> getRegionList() {
        return cityService.getAllCityes();
    }

}
