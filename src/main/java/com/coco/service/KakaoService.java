//package com.coco.service;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.HashMap;
//
//import javax.servlet.http.HttpSession;
//
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import com.coco.domain.Member;
//import com.coco.dto.KakaoDTO;
//import com.coco.repository.MemberRepository;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class KakaoService {
//
//	private final MemberRepository memberRepository;
//	private final HttpSession httpSession;
//	
//    @Value("${kakao.client.id}")
//    private String KAKAO_CLIENT_ID;
//
//    @Value("${kakao.redirect.url}")
//    private String KAKAO_REDIRECT_URL;
//    
//    @Value("${kakao.logoutRedirect.url}")
//    private String KAKAO_LOGOUTREDIRECT_URL;
//    
//
//    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
//    private final static String KAKAO_API_URI = "https://kapi.kakao.com";
//
//    public String getKakaoLogin() {
//        return KAKAO_AUTH_URI + "/oauth/authorize"
//                + "?client_id=" + KAKAO_CLIENT_ID
//                + "&redirect_uri=" + KAKAO_REDIRECT_URL
//                + "&response_type=code"
//                + "&prompt=login"; // 이 부분을 추가하여 매번 로그인하도록 유도
//    }
//    
//	public String getAccessToken (String auth_code) {
//		String access_token = "";
//		String refresh_token = "";
//		String reqURL = "https://kauth.kakao.com/oauth/token";
//		try {
//			URL url = new URL(reqURL);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			            
//			//    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
//			conn.setRequestMethod("POST");
//			conn.setDoOutput(true);
//			            
//			//    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//			StringBuilder sb = new StringBuilder();
//			sb.append("grant_type=authorization_code");
//			sb.append("&client_id=" + KAKAO_CLIENT_ID);
//			sb.append("&redirect_uri=" + KAKAO_REDIRECT_URL);
//			sb.append("&code=" + auth_code);
//			bw.write(sb.toString());
//			bw.flush();
//			            
//			//    결과 코드가 200이라면 성공
//			int responseCode = conn.getResponseCode();
//			System.out.println("responseCode : " + responseCode);
//			 
//			//요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
//			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String line = "";
//			String result = "";
//			            
//			while ((line = br.readLine()) != null) {
//				result += line;
//			}
//			System.out.println("response body : " + result);
//			            
//			//Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
//			JsonParser parser = new JsonParser();
//			JsonElement element = parser.parse(result);
//			            
//			access_token = element.getAsJsonObject().get("access_token").getAsString();
//			refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();
//			            
//			System.out.println("access_token : " + access_token);
//			System.out.println("refresh_token : " + refresh_token);
//			            
//			br.close();
//			bw.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//		} 
//		
//		return access_token;
//	}
//	
//	public HashMap<String, Object> getUserInfo (String access_token) {
//		HashMap<String, Object> userInfo = new HashMap<>();
//	    String reqURL = "https://kapi.kakao.com/v2/user/me";
//	    try{
//	    	System.out.println("getUserInfo() : access_token=" + access_token);
//	    	URL url = new URL(reqURL);
//	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	        conn.setRequestMethod("GET");
//	        conn.setRequestProperty("Authorization", "Bearer " + access_token);
//	        int responseCode = conn.getResponseCode();       
//	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8")); 
//	        String line = "";
//	        String result = "";    
//	        while ((line = br.readLine()) != null) {
//	        	result += line;
//	        }
//	        JsonParser parser = new JsonParser();
//	        JsonElement element = parser.parse(result);
//	        System.out.println("element="+ element.toString());
//	        String id = element.getAsJsonObject().get("id").getAsString();
//	        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();    
//	        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
//	        
//	        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
//	        String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
//	        
//	        userInfo.put("id", id);
//	        userInfo.put("email", email);
//	        userInfo.put("nickname", nickname);
//	        br.close();
//	            
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	        
//	    return userInfo;
//	}
//
//	
//   
//
//    public String getKakaoInfo(String code) throws Exception {
//        if (code == null) throw new Exception("Failed get authorization code");
//
//        String accessToken = "";
//        String refreshToken = "";
//        String reqURL = "https://kauth.kakao.com/oauth/token";
//        try {
//            HttpHeaders headers = new HttpHeaders();
//	        headers.add("Content-type", "application/x-www-form-urlencoded");
//
//	        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//	        params.add("grant_type"   , "authorization_code");
//	        params.add("client_id"    , KAKAO_CLIENT_ID);
//	        params.add("code"         , code);
//	        params.add("redirect_uri" , KAKAO_REDIRECT_URL);
//
//	        RestTemplate restTemplate = new RestTemplate();
//	        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
//
//	        ResponseEntity<String> response = restTemplate.exchange(
//	        		KAKAO_AUTH_URI + "/oauth/token",
//	                HttpMethod.POST,
//	                httpEntity,
//	                String.class
//	        );
//
//	        JSONParser jsonParser = new JSONParser();
//	        JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
//
//            accessToken  = (String) jsonObj.get("access_token");
//            refreshToken = (String) jsonObj.get("refresh_token");
//            
//            System.out.println("엑세스 토큰 : " + accessToken);
//            System.out.println("리프레시 토큰 : " + refreshToken);
//            
//            return accessToken;
//        } catch (Exception e) {
//            throw new Exception("API call failed");
//        }
//        
//    }
//
//    public KakaoDTO saveUserInfo(String accessToken) throws Exception {
//        KakaoDTO kakaoDTO = getUserInfoWithToken(accessToken);
//
//        // 회원가입 여부 확인
//        if (!memberRepository.existsById(kakaoDTO.getId())) {
//            // 사용자 정보를 DB에 저장
//            Member kakaomember = new Member();
//            kakaomember.setId(kakaoDTO.getId());
//            kakaomember.setEmail(kakaoDTO.getEmail());
//            kakaomember.setName(kakaoDTO.getNickname());
//
//            memberRepository.save(kakaomember);
//        }
//
//        return kakaoDTO;
//    }
//
//
//    private KakaoDTO getUserInfoWithToken(String accessToken) throws Exception {
//        //HttpHeader 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //HttpHeader 담기
//        RestTemplate rt = new RestTemplate();
//        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = rt.exchange(
//                KAKAO_API_URI + "/v2/user/me",
//                HttpMethod.POST,
//                httpEntity,
//                String.class
//        );
//
//        //Response 데이터 파싱
//        JSONParser jsonParser = new JSONParser();
//        JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
//        JSONObject account = (JSONObject) jsonObj.get("kakao_account");
//        JSONObject profile = (JSONObject) account.get("profile");
//
//        String email = String.valueOf(account.get("email"));
//        String nickname = String.valueOf(profile.get("nickname"));
//
//        
//        System.out.println("name : " + profile.get("nickname"));
//        System.out.println("email : " + account.get("email"));
//        
//        return KakaoDTO.builder()
//                    .email(email)
//                    .nickname(nickname).build();
//    }
//    
//    public void logout(String access_Token) {
//        // 카카오 로그아웃 API 호출
//        String kakaoLogoutUrl = KAKAO_API_URI + "/v1/user/logout";
//        
//        try {
//	        URL url = new URL(kakaoLogoutUrl);
//	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	        conn.setRequestMethod("POST");
//	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
//	        
//	        int responseCode = conn.getResponseCode();
//	        System.out.println("responseCode : " + responseCode);
//	        
//	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//	        
//	        String result = "";
//	        String line = "";
//	        
//	        while ((line = br.readLine()) != null) {
//	            result += line;
//	        }
//	        System.out.println(result);
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//
//    }
//    
//    
//    public void unlink(String access_Token) {
//	    String reqURL = KAKAO_API_URI + "/v1/user/unlink";
//	    
//	    try {
//	        URL url = new URL(reqURL);
//	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	        conn.setRequestMethod("POST");
//	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
//	        
//	        int responseCode = conn.getResponseCode();
//	        System.out.println("responseCode : " + responseCode);
//	        
//	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//	        
//	        String result = "";
//	        String line = "";
//	        
//	        while ((line = br.readLine()) != null) {
//	            result += line;
//	        }
//	        System.out.println(result);
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    } 	
//    }
//    
//}