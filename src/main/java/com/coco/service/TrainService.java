package com.coco.service;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.coco.domain.TrainInfo;

public interface TrainService {

	// 도시 코드 가져오기
	String getCityCodes() throws IOException;
	
	// 시/도별 기차역 목록조회
	String getTrainStationByCityCode(String cityCode);
	
	// 차량 종류 목록
	String getVhcleKndList() throws IOException;
	
	// 출/도착지 기반 열차정보 조회
	String getStrtpntAlocFndTrainInfoRaw(String depPlaceId, String arrPlaceId, String depPlandTime) throws IOException;
	
	// 출/도착지 기반 기차표 정보 -> 화면에 
	List<TrainInfo> getStrtpntAlocFndTrainInfo(String depPlaceId, String arrPlaceId, String depPlandTime) throws IOException, JSONException;
	
}
