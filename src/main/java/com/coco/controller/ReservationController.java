package com.coco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

	// 예약 페이지로 이동
    @GetMapping("/seatSelection")
    public String showSeatSelectionPage(
            @RequestParam(name = "trainNo") String trainNo,
            @RequestParam(name = "trainGrade") String trainGrade,
            @RequestParam(name = "depPlace") String depPlace,
            @RequestParam(name = "depTime") String depTime,
            @RequestParam(name = "arrPlace") String arrPlace,
            @RequestParam(name = "arrTime") String arrTime,
            @RequestParam(name = "adultCharge") String adultCharge,
            Model model
    ) {
        // 기차표 정보를 모델에 추가
        model.addAttribute("trainNo", trainNo);
        model.addAttribute("trainGrade", trainGrade);
        model.addAttribute("depPlace", depPlace);
        model.addAttribute("depTime", depTime);
        model.addAttribute("arrPlace", arrPlace);
        model.addAttribute("arrTime", arrTime);
        model.addAttribute("adultCharge", adultCharge);

        // seatSelection.html 페이지로 이동
        return "train/seatSelection";
    }
}
