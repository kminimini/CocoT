package com.coco.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TrainServiceImpl implements TrainService {
	
	private static final Logger logger = LoggerFactory.getLogger(TrainServiceImpl.class);
	
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
    	try {
            // 서비스 키를 사용하여 URL을 작성합니다.
            String urlStr = ctyCode + "?serviceKey=" + serviceKey + "&_type=json";
            URL url = new URL(urlStr);

            // URL에 대한 연결을 엽니다.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 응답 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 연결 닫기
            connection.disconnect();

            // 응답 기록하기
            logger.info("도시 코드에 대한 응답 수신: {}", response.toString());

            return response.toString();
        } catch (IOException e) {
            // 모든 예외 처리
            logger.error("HTTP GET 요청 전송 중 오류 발생: {}", e.getMessage());
            return null;
        }
    }

    /* TODO 시/도별 기차역 목록조회 */
    @Override
    public String getTrainStationByCityCode(String cityCode) {
        try {
            String urlStr = ctyAcc + "?serviceKey=" + serviceKey + "&cityCode=" + cityCode + "&_type=json";
            
            URL url = new URL(urlStr);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();

            logger.info("도시 코드에 대한 응답 수신: {}", response.toString());

            return response.toString();
        } catch (IOException e) {
            logger.error("HTTP GET 요청을 보내는 동안 오류가 발생: {}", e.getMessage());
            return null;
        }
    }

    // TODO 시/도별 기차역 목록 페이징 조회
    @Override
    public Map<String, Object> getTrainStationByCityCodeWithPage(String cityCode, int pageNo, int numOfRows) {
        try {
            // Adjust the URL to include pagination parameters
            String urlStr = ctyAcc + "?serviceKey=" + serviceKey + "&cityCode=" + cityCode + "&numOfRows=" + numOfRows + "&pageNo=" + pageNo + "&_type=json";
            URL url = new URL(urlStr);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();

            logger.info("도시 코드에 대한 페이징 응답 수신: {}", response.toString());

            // Parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> stationInfo = objectMapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {});

            return stationInfo;
        } catch (IOException e) {
            logger.error("HTTP GET 요청을 보내는 동안 오류가 발생: {}", e.getMessage());
            return null;
        }
    }
    
    /* TODO 차량 종류 목록 */
    @Override
    public String getVhcleKndList() {
        try {
            String urlStr = vhcle + "?serviceKey=" + serviceKey + "&_type=json";
            URL url = new URL(urlStr);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();

            logger.info("차량 종류 목록에 대한 응답 수신: {}", response.toString());

            return response.toString();
        } catch (IOException e) {
            logger.error("HTTP GET 요청을 보내는 동안 오류가 발생했습니다.: {}", e.getMessage());
            return null;
        }
    }

    /* TODO 출/도착지 기반 열차정보 조회 */
    @Override
    public String getStrtpntAlocFndTrainInfoRaw(String depPlaceId, String arrPlaceId, String depPlandTime, int pageNo, int numOfRows) {
        try {
            String urlStr = ctyStrt + "?serviceKey=" + serviceKey +
                            "&depPlaceId=" + depPlaceId +
                            "&arrPlaceId=" + arrPlaceId +
                            "&depPlandTime=" + depPlandTime +
                            "&numOfRows=" + numOfRows +
                            "&pageNo=" + pageNo +
                            "&_type=json";
    	        URL url = new URL(urlStr);

    	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    	        connection.setRequestMethod("GET");

    	        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    	        StringBuilder response = new StringBuilder();
    	        String line;
    	        while ((line = reader.readLine()) != null) {
    	            response.append(line);
    	        }
    	        reader.close();

    	        connection.disconnect();

    	        logger.info("열차 정보에 대한 응답 수신: {}", response.toString());

    	        String formattedResponse = formatDateTimeInResponse(response.toString());

    	        return formattedResponse;
    	    } catch (IOException e) {
    	        logger.error("HTTP GET 요청을 보내는 동안 오류가 발생했습니다.: {}", e.getMessage());
    	        return null;
    	    }
    }
    
    /* TODO 기차표 정보의 항목 배열 내 날짜, 시간 형식 지정 */
    private String formatDateTimeInResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject header = jsonResponse.getJSONObject("response").getJSONObject("header");

            // 응답이 성공했는지 확인
            if ("00".equals(header.getString("resultCode"))) {
                JSONArray items = jsonResponse.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");

                // 각 항목의 날짜 및 시간 서식 지정
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    String depPlandTime = item.optString("depplandtime");
                    String arrPlandTime = item.optString("arrplandtime");

                    item.put("depplandtime", formatDateTimeString(depPlandTime));
                    item.put("arrplandtime", formatDateTimeString(arrPlandTime));
                }

                // 업데이트된 JSON 문자열을 반환
                return jsonResponse.toString();
            } else {
                return response;
            }
        } catch (JSONException e) {
            return response;
        }
    }
    
    /* TODO yyyyMMddHHmmss -> yyyy-MM-dd HH:mm */
    private String formatDateTimeString(String dateTimeStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date date = inputFormat.parse(dateTimeStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return dateTimeStr;
        }
    }

    /* TODO 이전, 다음 버튼표시 여부 */
	@Override
	public boolean isLastPage(String depPlaceId, String arrPlaceId, String depPlandTime, int pageNo, int numOfRows) {
		try {
            // 첫 페이지의 열차 정보를 가져와 총 개수를 구하고,
            String firstPageResponse = getStrtpntAlocFndTrainInfoRaw(depPlaceId, arrPlaceId, depPlandTime, 1, numOfRows);
            JSONObject firstPageJson = new JSONObject(firstPageResponse);

            // 응답에서 총 개수 추출하고,
            int totalCount = firstPageJson.getJSONObject("response").getJSONObject("body").getInt("totalCount");

            // 총 페이지 수와 페이지당 행 수를 기준으로 총 페이지 수를 계산한다.
            int totalPages = (int) Math.ceil((double) totalCount / numOfRows);

            // 현재 페이지가 마지막 페이지인지 확인
            return pageNo >= totalPages;
        } catch (JSONException e) {
            // 임시로 예외처리는 false
            return false;
        }
	}

	// 마지막페이지 유무확인 조회용
	@Override
	public int getTotalPageCount(String depPlaceId, String arrPlaceId, String depPlandTime, int numOfRows) {
	    try {
	        String urlStr = ctyStrt + "?serviceKey=" + serviceKey +
	                        "&depPlaceId=" + depPlaceId +
	                        "&arrPlaceId=" + arrPlaceId +
	                        "&depPlandTime=" + depPlandTime +
	                        "&numOfRows=" + numOfRows +
	                        "&pageNo=1" + 
	                        "&_type=json";

	        URL url = new URL(urlStr);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");

	        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder response = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            response.append(line);
	        }
	        reader.close();

	        connection.disconnect();

	        JSONObject jsonResponse = new JSONObject(response.toString());
	        JSONObject totalCountInfo = jsonResponse.getJSONObject("response").getJSONObject("body");

	        int totalCount = totalCountInfo.getInt("totalCount");
	        int numOfPages = (int) Math.ceil((double) totalCount / numOfRows);

	        return numOfPages;
	    } catch (IOException | JSONException e) {
	        logger.error("Error getting total page count: {}", e.getMessage());
	        return 0; 
	    }
	}
	
}
