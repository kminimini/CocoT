package com.coco.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.coco.domain.TrainInfo;
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
	
	// 시/도별 기차역 목록조회 (with pagination)
	@GetMapping("/stations")
	public ResponseEntity<String> listStationsWithPagination(
	        @RequestParam("cityCode") String cityCode,
	        @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
	        @RequestParam(name = "numOfRows", defaultValue = "60") int numOfRows,
	        Model model) {
	    try {
	        String stations = trainService.getTrainStationByCityCode(cityCode, pageNo, numOfRows);

	        if (stations != null) {
	            // 결과를 Thymeleaf 템플릿에 전달해야 하는 경우 모델에 추가
	            model.addAttribute("stations", stations);
	            // 페이지가 지정된 스테이션 정보를 응답 엔티티로 반환.
	            return ResponseEntity.ok(stations);
	        } else {
	            // 스테이션 정보를 가져오는 데 문제가 발생한 경우 처리
	            model.addAttribute("error", "스테이션 가져오기 오류");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    } catch (Exception e) {
	        model.addAttribute("error", "스테이션 가져오기 오류: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
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
        	logger.info("열차 정보 요청 수신.");
        	String response = trainService.getStrtpntAlocFndTrainInfoRaw(depPlaceId, arrPlaceId, depPlandTime, pageNo, numOfRows);
        	
        	// 서비스에 열차 정보 얻기
        	TrainInfo.TrainResponse trainInfos = trainService.getTrainInfo(depPlaceId, arrPlaceId, depPlandTime);

            // 마지막페이지 유무확인 조회용
        	int totalPageCount = trainService.getTotalPageCount(depPlaceId, arrPlaceId, depPlandTime, numOfRows);
        	
        	int previousPageNo = Math.max(1, pageNo - 1);
        	int nextPageNo = Math.min(totalPageCount, pageNo + 1);
            
            // 다음날 조회하기 버튼 (마지막페이지 유무 확인)
            boolean lastPage = pageNo >= totalPageCount;
            
            // 다음날 조회 설정
            LocalDate nextDay;
            try {
            	nextDay = LocalDate.parse(depPlandTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            } catch (DateTimeParseException e) {
            	nextDay = LocalDate.parse(depPlandTime, DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
            
            nextDay = nextDay.plusDays(1);
            String nextDayFormatted = nextDay.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                        
            // 첫 페이지로 이동
            String firstPageUrl = buildPageUrl(depPlaceId, arrPlaceId, depPlandTime, 1, numOfRows);
            
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> trainInfo = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});
                        
            model.addAttribute("trainInfo", trainInfo);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("previousPageUrl", buildPageUrl(depPlaceId, arrPlaceId, depPlandTime, previousPageNo, numOfRows));
            model.addAttribute("nextPageUrl", buildPageUrl(depPlaceId, arrPlaceId, depPlandTime, nextPageNo, numOfRows));
            
            // 이전, 다음 버튼 표시 여부를 결정하는 플래그 설정
            model.addAttribute("isFirstPage", pageNo == 1);
            model.addAttribute("isLastPage", lastPage);
            
            // 마지막 페이지에서 다음날 조회하기 버튼 띄우기
            model.addAttribute("lastPage", lastPage);
            
            // 다음날 조회
            model.addAttribute("checkNextDayUrl", buildPageUrl(depPlaceId, arrPlaceId, nextDayFormatted, 1, numOfRows));
            
            // 마지막페이지로 이동
            model.addAttribute("lastPageUrl", buildPageUrl(depPlaceId, arrPlaceId, depPlandTime, totalPageCount, numOfRows));
            
            // 첫 페이지로 이동
            model.addAttribute("firstPageUrl", firstPageUrl);
            
            // 응답에 기차표가 있는지 확인
            if (!trainService.hasTrainItems(trainInfos)) {
                // 기차표가 없는 경우 오류 페이지로 리디렉션
                return "redirect:/train/error";
            }
            
            return "train/trainInfo";
        } catch (IOException e) {
        	model.addAttribute("error", "열차 정보 불러오기 오류: " + e.getMessage());
            return "error";
        }
    }

	// 페이지 이동 동작을 위한 URL 준비
    private String buildPageUrl(String depPlaceId, String arrPlaceId, String depPlandTime, int pageNo, int numOfRows) {
    	return UriComponentsBuilder.fromPath("/train/trainInfo")
    	        .queryParam("depPlaceId", depPlaceId)
    	        .queryParam("arrPlaceId", arrPlaceId)
    	        .queryParam("depPlandTime", depPlandTime)
    	        .queryParam("pageNo", pageNo)
    	        .queryParam("numOfRows", numOfRows)
    	        .toUriString();
    }
    
    // 오류 페이지
    @GetMapping("/error")
    public String showErrorPage() {
        return "train/error";
    }
}
