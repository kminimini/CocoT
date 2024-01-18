package com.coco.service;

import java.io.IOException;
import java.util.Map;

import com.coco.domain.TrainInfo;

public interface TrainService {

	// 도시 코드 가져오기
	String getCityCodes() throws IOException;
	
	// 시/도별 기차역 목록조회
	String getTrainStationByCityCode(String cityCode, int pageNo, int numOfRows);
	
	// 차량 종류 목록
	String getVhcleKndList() throws IOException;
	
	// 출/도착지 기반 열차정보 조회
	String getStrtpntAlocFndTrainInfoRaw(String depPlaceId, String arrPlaceId, String depPlandTime, int pageNo, int numOfRows) throws IOException;
	
	// 이전, 다음 버튼표시 여부
	boolean isLastPage(String depPlaceId, String arrPlaceId, String depPlandTime, int pageNo, int numOfRows);
	
	// 마지막페이지 유무확인 조회용
	int getTotalPageCount(String depPlaceId, String arrPlaceId, String depPlandTime, int numOfRows);
	
	// JSON 응답을 TrainResponse 객체로 파싱
	TrainInfo.TrainResponse getTrainInfo(String depPlaceId, String arrPlaceId, String depPlandTime);
	
	// 조회한 열차정보 타입캐스팅 후 저장
	boolean hasTrainItems(TrainInfo.TrainResponse trainInfo);

	// 기차표 목록에서 기차표 선택 
	void saveReservation(String trainNo, String trainGrade, String depPlace, String depTime, String arrPlace, String arrTime, Long adultCharge) throws Exception;

	// 페이지 이동 동작을 위한 URL 준비
	String buildPageUrl(String depPlaceId, String arrPlaceId, String depPlandTime, int pageNo, int numOfRows);

	// 열차 정보를 데이터베이스에 저장
	void saveTrainInfoToDatabase(Map<String, Object> trainInfo);

	// 주문번호 만들기
	String createOrderNum();

}
