package com.coco.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.coco.domain.TrainInfo;

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
    public String getStrtpntAlocFndTrainInfoRaw(String depPlaceId, String arrPlaceId, String depPlandTime) {
        try {
            String urlStr = ctyStrt + "?serviceKey=" + serviceKey + "&depPlaceId=" + depPlaceId + "&arrPlaceId=" + arrPlaceId + "&depPlandTime=" + depPlandTime + "&_type=json";
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

            return response.toString();
        } catch (IOException e) {
            logger.error("HTTP GET 요청을 보내는 동안 오류가 발생했습니다.: {}", e.getMessage());
            return null;
        }
    }

    /* TODO 출/도착지 기반 기차표 정보 -> 화면에 */
    @Override
    public List<TrainInfo> getStrtpntAlocFndTrainInfo(String depPlaceId, String arrPlaceId, String depPlandTime) throws IOException, JSONException {
        String response = getStrtpntAlocFndTrainInfoRaw(depPlaceId, arrPlaceId, depPlandTime);

        logger.debug("Raw API 응답: {}", response);
        
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray items = jsonResponse
        		.getJSONObject("response")
        		.getJSONObject("body")
        		.getJSONObject("items")
        		.getJSONArray("item");

        List<TrainInfo> trainInfos = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            logger.debug("처리 항목: {}", item.toString());
            
            TrainInfo trainInfo = new TrainInfo();
            trainInfo.setTrainNo(item.getInt("trainno"));
            trainInfo.setTrainType(item.getString("traingradename"));
            
            String depTime = item.getString("depplandtime");
            String arrTime = item.getString("arrplandtime");
            logger.debug("Original 출발 시간: {}, Original 도착 시간: {}", depTime, arrTime);

            trainInfo.setDepartureTime(formatDateTime(item.getString("depplandtime")));
            trainInfo.setArrivalTime(formatDateTime(item.getString("arrplandtime")));
            logger.debug("Formatted 출발 시간: {}, Formatted 도착 시간: {}", trainInfo.getDepartureTime(), trainInfo.getArrivalTime());

            trainInfo.setPrice(item.getInt("adultcharge"));
            trainInfos.add(trainInfo);
        }

        return trainInfos;
    }
    
    /* TODO 날짜형식 변환 '20231222051200' -> '2023-12-22 05:12' */
    private String formatDateTime(String dateTimeStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        inputFormat.setLenient(false);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date date = inputFormat.parse(dateTimeStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }

}
