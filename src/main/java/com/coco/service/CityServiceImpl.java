package com.coco.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

import com.coco.domain.City;
import com.coco.repository.CityRepository;

public class CityServiceImpl implements CityService {
	
	private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);


	@Autowired
	private CityRepository cityRepository;
	
	@Value("${api.ctyCode}")
	private String ctyCode;
	
	@Value("${api.serviceKry}")
	private String serviceKey;
	
	@Override
	public List<City> getAllCityes() {
		logger.debug("데이터베이스에서 모든 도시 가져오기.");
		return cityRepository.findAll();
	}

	@Override
	@PostConstruct
    public void updateCityList() {
        try {
            String urlTemplate = UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/1613000/TrainInfoService/getCtyCodeList")
                    .queryParam("serviceKey", URLEncoder.encode(serviceKey, "UTF-8"))
                    .queryParam("_type", "xml")
                    .toUriString();

            URL url = new URL(urlTemplate);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            logger.info("API에 연결: {}", urlTemplate);

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                logger.info("API에 성공적으로 연결되었습니다. 응답 코드: {}", conn.getResponseCode());
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                logger.error("API에 연결하지 못했습니다. 응답 코드: {}", conn.getResponseCode());
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            String response = sb.toString();
            logger.debug("API 응답: {}", response);
            
            List<City> cities = parseCitiesFromXml(response);
            cityRepository.saveAll(cities);
            logger.info("도시 목록이 업데이트되었습니다..");
            
        } catch (Exception e) {
        	logger.error("도시 목록을 업데이트하는 동안 예외가 발생.", e);
            e.printStackTrace();
        }
    }

    private List<City> parseCitiesFromXml(String xml) {
        // TODO: XML 파싱 로직을 구현하여 XML 응답을 List<City>로 변환
    	logger.debug("도시 목록에 대한 XML 응답을 구문 분석.");
        throw new UnsupportedOperationException("XML 구문 분석이 아직 구현되지 않음.");
    }

}
