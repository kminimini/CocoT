package com.coco.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TrainServiceImpl implements TrainService {

	@Value("${api.serviceKey}")
	private String serviceKey;
	
    @Value("${api.ctyCode}")
    private String ctyCode;
    
    @Value("${api.ctyAccTrain}")
    private String ctyAcc;

    @Value("${api.Vhcle}")
    private String vhcle;
    
    @Value("${api.ctyStrtAndTrain}")
    private String ctyStrt;
    
    /* TODO 도시 코드 가져오기 */
    @Override
    public String getCityCodes() {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ctyCode)
                .queryParam("serviceKey", serviceKey)
                .queryParam("_type", "json");
        System.out.println("도시코드 요청 URL : " + ctyCode + serviceKey);
        return restTemplate.getForObject(builder.toUriString(), String.class);
    }

    /* TODO 시/도별 기차역 목록조회 */
    @Override
    public String getTrainStationByCityCode(String cityCode) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ctyAcc)
                .queryParam("serviceKey", serviceKey)
                .queryParam("cityCode", cityCode)
                .queryParam("_type", "json");
        System.out.println("기차역 요청 URL : " + ctyAcc + serviceKey);
        return restTemplate.getForObject(builder.toUriString(), String.class);
    }
    
    /* TODO 차량 종류 목록 */
    @Override
    public String getVhcleKndList() throws IOException {
    	RestTemplate restTemplate = new RestTemplate();
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(vhcle)
    			.queryParam("serviceKey", serviceKey)
    			.queryParam("_type", "json");
    	System.out.println("차량종류목록 요청 URL : " + vhcle + serviceKey);
    	return restTemplate.getForObject(builder.toUriString(), String.class);
    }
    
    /* TODO 출/도착지 기반 열차정보 조회 */
    @Override
    public String getStrtpntAlocFndTrainInfo(String depPlaceId, String arrPlaceId, String depPlandTime) {
    	RestTemplate restTemplate = new RestTemplate();
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ctyStrt)
    			.queryParam("serviceKey", serviceKey)
    			.queryParam("depPlaceId", depPlaceId)
    			.queryParam("arrPlaceId", arrPlaceId)
    			.queryParam("depPlandTime", depPlandTime)
    			.queryParam("_type", "json");
    	System.out.println("열차정보 요청 URL : " + ctyStrt + serviceKey);
    	return restTemplate.getForObject(builder.toUriString(), String.class);
    }

}
