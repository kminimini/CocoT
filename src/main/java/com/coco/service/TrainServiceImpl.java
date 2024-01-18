package com.coco.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.coco.domain.TrainInfo;

@Service
public class TrainServiceImpl implements TrainService {
	
	private static final Logger logger = LoggerFactory.getLogger(TrainServiceImpl.class);
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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
            // URL을 정리하여 의도하지 않은 문자 제거하기
            urlStr = urlStr.replaceAll("[^\\x20-\\x7e]", "");
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
    public String getTrainStationByCityCode(String cityCode, int pageNo, int numOfRows) {
        try {
            String urlStr = ctyAcc + "?serviceKey=" + serviceKey + "&cityCode=" + cityCode + "&_type=json" +
                    "&pageNo=" + pageNo +
                    "&numOfRows=" + numOfRows;
            // URL을 정리하여 의도하지 않은 문자 제거하기
            urlStr = urlStr.replaceAll("[^\\x20-\\x7e]", "");
            URL url = new URL(urlStr);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            logger.info("HTTP 응답 코드: {}", responseCode);

            // 응답이 성공(200)인 경우에만 데이터 읽기
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                connection.disconnect();

                // 응답 데이터 확인
                logger.info("도시 코드에 대한 응답 수신: {}", response.toString());

                return response.toString();
            } else {
                // 응답이 성공하지 않으면 오류 메시지 출력
                logger.error("HTTP 응답이 성공하지 않았습니다. 응답 코드: {}", responseCode);
                return null;
            }
        } catch (IOException e) {
            // HTTP 요청 또는 응답 처리 중 오류가 발생한 경우
            logger.error("HTTP GET 요청을 보내거나 응답을 처리하는 동안 오류가 발생했습니다.: {}", e.getMessage());
            return null;
        }
    }
    
    /* TODO 차량 종류 목록 */
    @Override
    public String getVhcleKndList() {
        try {
            String urlStr = vhcle + "?serviceKey=" + serviceKey + "&_type=json";
            // URL을 정리하여 의도하지 않은 문자 제거하기
            urlStr = urlStr.replaceAll("[^\\x20-\\x7e]", "");
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

            // URL을 정리하여 의도하지 않은 문자 제거하기
            urlStr = urlStr.replaceAll("[^\\x20-\\x7e]", "");
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
    
    /* TODO 다음날 조회하기 버튼 (마지막페이지 유무 확인)를 위한 메서드 */
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

    /* TODO 출발지, 도착지, 출발일을 기반으로 기차 정보 조회 */
    @Override
    public TrainInfo.TrainResponse getTrainInfo(String depPlaceId, String arrPlaceId, String depPlandTime) {
        try {
            // 적절한 API 또는 서비스를 호출하여 열차 정보 얻기
            String responseJson = callTrainInfoApi(depPlaceId, arrPlaceId, depPlandTime);

            // JSON 응답을 구문 분석하고 TrainResponse 객체를 반환
            return parseTrainInfoResponse(responseJson);
        } catch (Exception e) {
            logger.error("열차 정보 가져오기 오류: {}", e.getMessage());
            return null;
        }
    }

    /* TODO API 호출을 통한 기차 정보 조회 */
    private String callTrainInfoApi(String depPlaceId, String arrPlaceId, String depPlandTime) throws IOException {
        String urlStr = ctyStrt + "?serviceKey=" + serviceKey +
                        "&depPlaceId=" + depPlaceId +
                        "&arrPlaceId=" + arrPlaceId +
                        "&depPlandTime=" + depPlandTime +
                        "&numOfRows=10" +
                        "&pageNo=1" +
                        "&_type=json";

        // URL 정리
        urlStr = urlStr.replaceAll("[^\\x20-\\x7e]", "");
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

        return response.toString();
    }

    /* TODO JSON 응답을 TrainResponse 객체로 파싱 */
    private TrainInfo.TrainResponse parseTrainInfoResponse(String responseJson) {
        try {
            // JSON 응답 구문 분석
            JSONObject jsonResponse = new JSONObject(responseJson);
            JSONObject responseHeader = jsonResponse.getJSONObject("response").getJSONObject("header");

            // 응답이 성공을 나타내는지 확인
            if ("00".equals(responseHeader.getString("resultCode"))) {
                // 관련 필드를 구문 분석하고 TrainResponse 객체를 생성
                TrainInfo.TrainResponse trainResponse = new TrainInfo.TrainResponse();

                TrainInfo.ResponseBody responseBody = new TrainInfo.ResponseBody();
                TrainInfo.Items items = new TrainInfo.Items();
                List<TrainInfo.TrainItem> trainItems = new ArrayList<>();

                // 항목 배열 구문 분석
                JSONArray itemsArray = jsonResponse.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject itemJson = itemsArray.getJSONObject(i);
                    // itemJson에서 필드 추출 및 TrainItem 개체 만들기
                    TrainInfo.TrainItem trainItem = new TrainInfo.TrainItem();
                    trainItem.setTrainno(itemJson.getInt("trainno"));
                    trainItem.setTraingradename(itemJson.getString("traingradename"));
                    trainItem.setDepplacename(itemJson.getString("depplacename"));
                    trainItem.setDepplandtime(itemJson.getLong("depplandtime"));
                    trainItem.setArrplacename(itemJson.getString("arrplacename"));
                    trainItem.setArrplandtime(itemJson.getLong("arrplandtime"));
                    trainItem.setAdultcharge(itemJson.getInt("adultcharge"));
                    
                    // 목록에 기차 항목 추가
                    trainItems.add(trainItem);
                }

                // 응답 본문에 항목 목록 설정
                items.setItem(trainItems);
                responseBody.setItems(items);
                trainResponse.setResponse(responseBody);

                return trainResponse;
            } else {
                logger.error("열차 정보 API 응답이 실패를 나타냅니다.: {}", responseHeader.getString("resultMsg"));
                return null;
            }
        } catch (JSONException e) {
            logger.error("열차 정보 파싱 오류 응답: {}", e.getMessage());
            return null;
        }
    }

    /* TODO 조회한 열차정보 타입캐스팅 후 저장 */
    @Override
    public boolean hasTrainItems(TrainInfo.TrainResponse trainInfo) {
        if (trainInfo != null && trainInfo.hasTrainItems()) {
            try {
                TrainInfo.ResponseBody responseBody = trainInfo.getResponse();
                TrainInfo.Items items = responseBody != null ? responseBody.getItems() : null;

                boolean result = responseBody != null && responseBody.hasTrainItems() && items != null && items.hasItem();

                if (result) {
                    logger.debug("기차 아이템이 있습니다.");
                } else {
                    logger.debug("기차 아이템이 없습니다..");
                }

                return result;
            } catch (ClassCastException e) {
                logger.error("hasTrainItems 메서드에서 객체를 캐스팅하는 동안 오류가 발생!!!", e);
                return false;
            }
        }
        return false;
    }

    /* TODO 기차표 목록에서 기차표 선택 */
    public void saveReservation(String trainNo, String trainGrade, String depPlace, String depTime, String arrPlace, String arrTime, Long adultCharge) throws Exception {
        try {
            // 예약 세부 정보를 오라클 데이터베이스에 저장하는 로직 구현하기
            String sql = "INSERT INTO reservation (train_no, train_grade, dep_place, dep_time, arr_place, arr_time, adult_charge) VALUES (?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.update(sql, trainNo, trainGrade, depPlace, depTime, arrPlace, arrTime, adultCharge);
        } catch (Exception e) {
            throw new Exception("Error saving reservation details to the database", e);
        }
    }
      
    /* TODO 페이지 이동 동작을 위한 URL 준비 */
    @Override
    public String buildPageUrl(String depPlaceId, String arrPlaceId, String depPlandTime, int pageNo, int numOfRows) {
    	return UriComponentsBuilder.fromPath("/train/trainInfo")
    	        .queryParam("depPlaceId", depPlaceId)
    	        .queryParam("arrPlaceId", arrPlaceId)
    	        .queryParam("depPlandTime", depPlandTime)
    	        .queryParam("pageNo", pageNo)
    	        .queryParam("numOfRows", numOfRows)
    	        .toUriString();
    }

	/* TODO 열차 정보를 데이터베이스에 저장 */
    @Override
	public void saveTrainInfoToDatabase(Map<String, Object> trainInfo) {
    	try {
    		// Delete all records from the train table
            String deleteSql = "DELETE FROM train";
            jdbcTemplate.update(deleteSql);
            logger.info("기차표 초기화...");
            
            Map<String, Object> response = (Map<String, Object>) trainInfo.get("response");
            Map<String, Object> body = (Map<String, Object>) response.get("body");
            Map<String, Object> items = (Map<String, Object>) body.get("items");
            List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");

         // 각 필드에 대한 null 체크 추가
            for (Map<String, Object> item : itemList) {
                Long trainNo = (item.get("trainno") != null) ? ((Number) item.get("trainno")).longValue() : null;

                // LocalDateTime으로 변경
                LocalDateTime departureTime = parseDateTime(item.get("depplandtime"));
                LocalDateTime arrplandTime = parseDateTime(item.get("arrplandtime"));

                String depplacename = (String) item.get("depplacename");
                String arrplacename = (String) item.get("arrplacename");
                String traingradename = (String) item.get("traingradename");
                Long adultcharge = (item.get("adultcharge") != null) ? ((Number) item.get("adultcharge")).longValue() : null;
            
                // 주문번호 생성
                String orderId = createOrderNum();

                // 데이터베이스에 저장하기 전에 로그하여 값 확인
                logger.info("데이터베이스에 저장하기 전 값 - TrainNo: {}, DepartureTime: {}, ArrplandTime: {}, DepPlaceName: {}, ArrPlaceName: {}, TrainGradeName: {}, AdultCharge: {}, OrderId: {}", trainNo, departureTime.format(formatter), arrplandTime.format(formatter), depplacename, arrplacename, traingradename, adultcharge, orderId);

                // SQL 쿼리
                String sql = "INSERT INTO train (train_no, depplandtime, arrplandtime, depplacename, arrplacename, traingradename, amount, order_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

             // JdbcTemplate을 사용하여 데이터베이스에 삽입
                jdbcTemplate.update(sql, trainNo, departureTime, arrplandTime, depplacename, arrplacename, traingradename, adultcharge, orderId);
                // 성공적으로 저장되었을 때 로그 출력
                logger.info("데이터베이스에 저장된 열차 정보. 열차 번호: {}, 출발 시간: {}, 도착 시간: {}, 출발 장소: {}, 도착 장소: {}, 열차 등급: {}, 성인 요금: {}, OrderId: {}", trainNo, departureTime.format(formatter), arrplandTime.format(formatter), depplacename, arrplacename, traingradename, adultcharge, orderId);
            }
        } catch (Exception e) {
            // 저장 중 오류가 발생했을 때 로그 출력
            logger.error("열차 정보를 데이터베이스에 저장하는 데 오류가 발생했습니다.: {}", e.getMessage());
        }
    }
    
    // 기차표 정보의 항목 배열 내 날짜, 시간 형식 지정 
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
    
    // yyyyMMddHHmmss -> yyyy-MM-dd HH:mm
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

            // URL을 정리하여 의도하지 않은 문자 제거하기
            urlStr = urlStr.replaceAll("[^\\x20-\\x7e]", "");
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

            // 총 페이지 수와 페이지당 행 수를 기준으로 총 페이지 수를 계산
            int numOfPages = (int) Math.ceil((double) totalCount / numOfRows);

            return numOfPages;
        } catch (IOException | JSONException e) {
            logger.error("총 페이지 수를 가져오는 데 오류가 발생했습니다.: {}", e.getMessage());
            return 0;
        }
    }

    // 주문번호 만들기
    @Override
    public String createOrderNum() {
        UUID uuid = UUID.randomUUID();
        String orderNum = uuid.toString().substring(0, 21);
        return orderNum;
    }

    // 날짜 형식 변경
    private LocalDateTime parseDateTime(Object dateTime) {
        if (dateTime instanceof String) {
            String dateString = (String) dateTime;
            
            try {
                // 첫 번째 형식으로 날짜를 구문 분석
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
                return LocalDateTime.parse(dateString, formatter1);
            } catch (DateTimeParseException e1) {
                try {
                    // 첫 번째 형식이 실패하면 두 번째 형식
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    return LocalDateTime.parse(dateString, formatter2);
                } catch (DateTimeParseException e2) {
                	// 둘다 실패하면 null
                    return null;
                }
            }
        } else if (dateTime instanceof Number) {
            // 이미 밀리초 단위로 로컬 날짜/시간으로 변환합니다.
            return LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(((Number) dateTime).longValue()), java.time.ZoneId.of("UTC"));
        }
        return null;
    }
}
