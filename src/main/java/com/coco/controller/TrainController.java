package com.coco.controller;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coco.service.TrainService;
import com.coco.service.TrainServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/train")
public class TrainController {
	private static final Logger logger = LoggerFactory.getLogger(TrainServiceImpl.class);
	@Autowired
	private TrainService trainService;
	
	// 도시 코드 가져오기
	@GetMapping("/cityCodes")
    public String listCityCodes(Model model) {
        try {
            String cityCodes = trainService.getCityCodes();
            model.addAttribute("cityCodes", cityCodes);
        } catch (Exception e) {
            model.addAttribute("error", "도시 코드 가져오기 오류: " + e.getMessage());
        }
        return "train/cityCodes";
    }
	
	// 시/도별 기차역 목록조회
	@GetMapping("/stations")
    public ResponseEntity<String> listStations(@RequestParam("cityCode") String cityCode, Model model) {
        try {
            String stations = trainService.getTrainStationByCityCode(cityCode);
            model.addAttribute("stations", stations);
            return ResponseEntity.ok(stations);
        } catch (Exception e) {
            model.addAttribute("error", "스테이션 가져오기 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("기차역 불러오기 오류");
        }
//        return "train/stations";
    }
	
	// 차량 종류 목록
	@GetMapping("/vhcleKndList")
	public String listVhcleKnd(Model model) {
		try {
			String vhcleKndList = trainService.getVhcleKndList();
			model.addAttribute("vhcleKndList", vhcleKndList);
		} catch (Exception e) {
			model.addAttribute("error", "차량 종류 목록 가져오기 오류: " + e.getMessage());
		}
		return "train/vhcleKndList";
	}
	
	// 출/도착지 기반 열차정보 조회
	@GetMapping("/trainInfo")
    public String getTrainInfo(
            @RequestParam(name = "depPlaceId") String depPlaceId,
            @RequestParam(name = "arrPlaceId") String arrPlaceId,
            @RequestParam(name = "depPlandTime") String depPlandTime,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "numOfRows", defaultValue = "10") int numOfRows,
            Model model) {

        try {
            String response = trainService.getStrtpntAlocFndTrainInfoRaw(depPlaceId, arrPlaceId, depPlandTime, pageNo, numOfRows);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> trainInfo = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});

            model.addAttribute("trainInfo", trainInfo);

            return "train/trainInfo";
        } catch (IOException e) {
            return "error";
        }
    }

	// '다음' 버튼 클릭에 대한 핸들러
//	@GetMapping("/trainInfo")
//    public String getNextTrainInfo(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo, Model model) {
//        // For demonstration purposes, let's assume you have a method in TrainService to get train info
//        TrainInfoPage trainInfoPage = trainService.getTrainInfoPage(pageNo);
//
//        model.addAttribute("trainInfo", trainInfoPage);
//
//        return "train/trainInfo";
//    }

}
