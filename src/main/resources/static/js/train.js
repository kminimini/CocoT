/* train.js */
// 도시 번호로 기차역 보기
/*
function promptForCityCode() {
	var cityCode = prompt("Please enter the city code:", "");
	if (cityCode != null && cityCode != "") {
		window.location.href = "/train/stations?cityCode=" + cityCode;
	}
}
*/

// 출발 날짜 선택 후 검색 : ex) 2023-11-11 -> 20231111 (하이픈 제외하고 요청)
document.getElementById('trainInfoForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 기본 양식 제출 방지
	try {
	    var depPlaceId = getCityCodeFromInput('departureStationId', 'departureStationName');
	    var arrPlaceId = getCityCodeFromInput('arrivalStationId', 'arrivalStationName');
	    var depPlandTime = document.getElementById('depPlandTime').value;
	
		// 원래 날짜와 시간 기록
		console.log("Original 출발 날짜 및 시간 : ", depPlandTime);
		
	    // 날짜에서 하이픈 제거
	    depPlandTime = depPlandTime.replace(/-/g, '');
	
		// 수정된 날짜와 시간 기록
	    console.log("Modified 출발 날짜 및 시간: ", depPlandTime);
	    
	    // 요청 URL 생성
	    var requestUrl = '/train/trainInfo?depPlaceId=' + depPlaceId + '&arrPlaceId=' + arrPlaceId + '&depPlandTime=' + depPlandTime;
	
	    // 생성된 URL로 리디렉션
	    window.location.href = requestUrl;
	} catch (error) {
	        // 오류를 우아하게 처리하기
	        console.error("양식 제출 중 오류 발생:", error);
	        // 오류 페이지로 리디렉션
	        window.location.href = '/error.html';
	}
});

// 출발지 팝업을 열 때 함수를 호출
function openDepartureLocationPopup() {
	document.getElementById("departureLocationPopup").style.display = "block";

	// 컨텍스트에서 도시 코드를 사용할 수 있다고 가정합니다.
	var cityCodes = [
		{ code: "11", name: "서울특별시" },
		{ code: "21", name: "부산광역시" },
		{ code: "31", name: "경기도" },
		{ code: "12", name: "세종특별시" },
		{ code: "22", name: "대구광역시" },
		{ code: "23", name: "인천광역시" },
		{ code: "24", name: "곽주광역시" },
		{ code: "25", name: "대전광역시" },
		{ code: "26", name: "울산광역시" },
		{ code: "32", name: "강원도" },
		{ code: "33", name: "충청북도" },
		{ code: "34", name: "충청남도" },
		{ code: "35", name: "전라북도" },
		{ code: "36", name: "전라남도" },
		{ code: "37", name: "경상북도" },
		{ code: "38", name: "경상남도" },
	];

	populateCityCodesList(cityCodes);
}

// 도착지 팝업을 열 때 함수 호출
function openArrivalLocationPopup() {
	document.getElementById("departureLocationPopup").style.display = "block";
	
	var cityCodes = [
		{ code: "11", name: "서울특별시" },
		{ code: "21", name: "부산광역시" },
		{ code: "31", name: "경기도" },
		{ code: "12", name: "세종특별시" },
		{ code: "22", name: "대구광역시" },
		{ code: "23", name: "인천광역시" },
		{ code: "24", name: "곽주광역시" },
		{ code: "25", name: "대전광역시" },
		{ code: "26", name: "울산광역시" },
		{ code: "32", name: "강원도" },
		{ code: "33", name: "충청북도" },
		{ code: "34", name: "충청남도" },
		{ code: "35", name: "전라북도" },
		{ code: "36", name: "전라남도" },
		{ code: "37", name: "경상북도" },
		{ code: "38", name: "경상남도" },
	];
	
	populateCityCodesListForArrival(cityCodes);
}

// 출발지 팝업 닫기 기능
function closeDepartureLocationPopup() {
	document.getElementById("departureLocationPopup").style.display = "none";
}

// 출발지 : 도시 코드 목록을 채우는 함수
function populateCityCodesList(cityCodes) {
	var cityCodeList = document.getElementById("cityCodeList");

	// 기존 목록 항목 지우기
	cityCodeList.innerHTML = "";

	// 도시 번호로 목록 채우기
	cityCodes.forEach(city => {
		var listItem = document.createElement("li");
		listItem.textContent = city.name;
		listItem.addEventListener("click", function() {
			// 클릭 이벤트 처리(필요에 따라 이 부분을 수정할 수 있음)
			console.log("출발지 : 선택한 도시 코드:", city.code, city.name);
			// 선택한 도시의 기차역 가져오기 및 표시하기
			fetchTrainStationsByCityCode(city.code, city.name);
			// 도시 코드를 선택한 후 팝업을 닫음.
			//closeDepartureLocationPopup();
		});
		cityCodeList.appendChild(listItem);
	});
}

// 도착지 : 도시 코드 목록을 채우는 함수
function populateCityCodesListForArrival(cityCodes) {
	var cityCodeList = document.getElementById("cityCodeList");
	cityCodeList.innerHTML = "";
	
	cityCodes.forEach(city => {
		var listItem = document.createElement("li");
		listItem.textContent = city.name;
		listItem.addEventListener("click", function() {
			console.log("도착지 : 선택한 도시 코드", city.code, city.name);
			fetchTrainStationsByCityCodeForArrival(city.code, city.name);
		});
		cityCodeList.appendChild(listItem);
	});
}

// 출발지 : 선택한 도시를 서버에 전달해서 도시의 기차역을 불러와 표시하는 기능
function fetchTrainStationsByCityCode(cityCode) {
    // 도시 코드별로 기차역을 가져오기
    var url = "/train/stations?cityCode=" + cityCode;

    // 가져오기 요청하기
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('출발지-네트워크 응답이 정상적이지 않음' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            // 구문 분석된 XML 데이터를 콘솔에 기록
            console.log("출발지-구문 분석된 JSON 데이터 : ", data);

            // 가져온 기차역 데이터로 UI 업데이트하기
            displayTrainStations(data);
        })
        .catch(error => {
            console.error("출발지-기차역 불러오기 오류:", error);
        });
}

// 도착지 : 선택한 도시를 서버에 전달해서 도시의 기차역을 불러와 표시하는 기능
function fetchTrainStationsByCityCodeForArrival(cityCode) {
	var url = "/train/stations?cityCode=" + cityCode;
	
	fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('도착지-네트워크 응답이 정상적이지 않음' + response.statusText);
            }
            return response.json();
        })
		.then(data => {
			console.log('도착지-구문 분석된 JSON 데이터 : ', data);
			displayTrainStationsForArrival(data);
		})
		.catch(error => {
			console.log('도착지-기차역 불러오기 오류 : ', error);
		})
}

// 출발지 : UI에 기차역을 표시하는 기능
function displayTrainStations(data) {
    var stations = data.response.body.items.item;

    if (!Array.isArray(stations)) {
        console.error("Expected an array of stations, received:", stations);
        return;
    }

    var stationList = document.getElementById("trainStationList");
    stationList.innerHTML = "";

    stations.forEach(station => {
        var listItem = document.createElement("li");
        listItem.textContent = station.nodename;
        listItem.onclick = function () {
            document.getElementById("departureStationId").value = station.nodeid;
            document.getElementById("departureStationName").value = station.nodename;
            closeDepartureLocationPopup();
        };
        stationList.appendChild(listItem);
    });

    var popup = document.getElementById("departureLocationPopup");
    popup.style.display = "block";
}

// 도착지 : UI에 기차역을 표시하는 기능
function displayTrainStationsForArrival(data) {
	var stations = data.response.body.items.item;
    if (!Array.isArray(stations)) {
        console.error("Expected an array of stations, received:", stations);
        return;
    }
    var stationList = document.getElementById("trainStationList");
    stationList.innerHTML = "";

    stations.forEach(station => {
        var listItem = document.createElement("li");
        listItem.textContent = station.nodename;
        listItem.onclick = function() {
			document.getElementById("arrivalStationId").value = station.nodeid;
			document.getElementById("arrivalStationName").value = station.nodename;
			closeDepartureLocationPopup();
		};
        stationList.appendChild(listItem);
    });
    var popup = document.getElementById("departureLocationPopup");
    popup.style.display = "block";
}
// 입력 필드에서 도시 코드를 가져오는 함수
function getCityCodeFromInput(idField, nameField) {
    var cityId = document.getElementById(idField).value;
    var cityName = document.getElementById(nameField).value;

    // 이미 사용 가능한 도시 ID가 있는 경우 이를 사용
    if (cityId) {
        return cityId;
    } else {
        // 그렇지 않은 경우 매핑에서 도시 코드를 조회.
        var mappedCode = cityCodeMapping[cityName];
        if (mappedCode) {
            return mappedCode;
        } else {
            // 도시 코드를 찾을 수 없는 경우 처리
            console.error("City code not found for city:", cityName);
            // 오류를 발생시켜 캐치 블록을 트리거
            throw new Error("City code not found");
        }
    }
}
// 도시 이름을 도시 코드에 매핑
var cityCodeMapping = {
	"": "서울특별시",
    "서울": "NAT010000",
    "상봉": "NAT020040",
    "서빙고": "NAT130036",
    "옥수": "NAT130070",
    "왕십리": "NAT130104",
    "청량리": "NAT130126",
    "광운대": "NAT130182",
    "용산": "NAT010032",
    "노량진": "NAT010058",
    "영등포": "NAT010091",
    "신도림": "NAT010106",
    "수서": "NATH30000",
    "": "부산광역시",
    "부산": "NAT014445",
    "화명": "NAT014244",
    "구포": "NAT014281",
    "사상": "NAT014331",
    "부전": "NAT750046",
    "동래": "NAT750106",
    "센텀": "NAT750161",
    "신해운대": "NAT750189",
    "송정": "NAT750254",
    "기장": "NAT750329",
    "좌천": "NAT750412",
    "": "경기도",
    "덕소": "NAT020178",
    "아신": "NAT020471",
    "양평": "NAT020524",
    "용문": "NAT020641",
    "지평": "NAT020677",
    "석불": "NAT020717",
    "일신": "NAT020760",
    "매곡": "NAT020803",
    "양동": "NAT020845",
    "삼산": "NAT020884",
    "부천": "NAT060121",
    "행신": "NAT110147",
    "능곡": "NAT110165",
    "일산": "NAT110249",
    "탄현": "NAT110265",
    "파주": "NAT110407",
    "문산": "NAT110460",
    "운천": "NAT110497",
    "임진강": "NAT110520",
    "도라산": "NAT110557",
    "의정부": "NAT130312",
    "동두천": "NAT130531",
    "소요산": "NAT130556",
    "초성리": "NAT130597",
    "한탄강": "NAT130627",
    "전곡": "NAT130652",
    "연천": "NAT130738",
    "신망리": "NAT130774",
    "대광리": "NAT130844",
    "신탄리": "NAT130888",
    "퇴계원": "NAT140098",
    "사릉": "NAT140133",
    "평내호평": "NAT140214",
    "마석": "NAT140277",
    "대성리": "NAT140362",
    "청평": "NAT140436",
    "가평": "NAT140576",
    "안양": "NAT010239",
    "수원": "NAT010415",
    "오산": "NAT010570",
    "서정리": "NAT010670",
    "평택": "NAT010754",
    "가남": "NAT280090",
    "부발": "NAT250428",
    "광명": "NATH10219",
    "동탄": "NATH30326",
    "지제": "NATH30536",
    "": "세종특별시",
    "부강": "NAT011403",
    "조치원": "NAT011298",
    "소정리": "NAT011079",
    "전의": "NAT011154",
    "": "대구광역시",
    "대구": "NAT013239",
    "동대구": "NAT013271",
    "서대구": "NAT013189",
    "": "인천광역시",
    "주안": "NAT060231",
    "인천공항Ｔ２": "NATC30058",
    "인천공항Ｔ１": "NATC10580",
    "검암": "NATC10325",
    "": "광주광역시",
    "광주송정": "NAT031857",
    "효천": "NAT882904",
    "서광주": "NAT882936",
    "광주": "NAT883012",
    "극락강": "NAT883086",
    "": "대전광역시",
    "대전": "NAT011668",
    "서대전": "NAT030057",
    "흑석리": "NAT030173",
    "신탄진": "NAT011524",
    "": "울산광역시",
    "북울산": "NAT750781",
    "울산": "NATH13717",
    "통도사": "NATH13717",
    "남창": "NAT750560",
    "덕하": "NAT750653",
    "태화강": "NAT750726",
    "효문": "NAT750760",
    "": "강원도",
    "동화": "NAT020986",
    "만종": "NAT021033",
    "반곡": "NAT021175",
    "신림": "NAT021357",
    "서원주": "NAT020864",
    "원주": "NAT020947",
    "백마고지": "NAT130944",
    "백양리": "NAT140681",
    "강촌": "NAT140701",
    "김유정": "NAT140787",
    "남춘천": "NAT140840",
    "춘천": "NAT140873",
    "동점": "NAT600830",
    "철암": "NAT600870",
    "도계": "NAT601122",
    "신기": "NAT601275",
    "동해": "NAT601485",
    "묵호": "NAT601545",
    "망상해변": "NAT601602",
    "망상": "NAT601605",
    "정동진": "NAT601774",
    "강릉": "NAT601936",
    "별어곡": "NAT610064",
    "선평": "NAT610137",
    "정선": "NAT610226",
    "나전": "NAT610326",
    "아우라지": "NAT610387",
    "횡성": "NATN10230",
    "둔내": "NATN10428",
    "평창": "NATN10625",
    "진부": "NATN10787",
    "추암": "NAT630064",
    "삼척해변": "NAT630078",
    "쌍룡": "NAT650177",
    "연당": "NAT650253",
    "영월": "NAT650341",
    "예미": "NAT650515",
    "함백": "NAT650567",
    "민둥산": "NAT650722",
    "사북": "NAT650782",
    "고한": "NAT650828",
    "추전": "NAT650918",
    "태백": "NAT650978",
    "동백산": "NAT651053",
    "": "충청북도",
    "옥천": "NAT011833",
    "이원": "NAT011916",
    "지탄": "NAT011972",
    "심천": "NAT012016",
    "각계": "NAT012054",
    "영동": "NAT012124",
    "황간": "NAT012270",
    "추풍령": "NAT012355",
    "봉양": "NAT021478",
    "제천": "NAT021549",
    "도담": "NAT021723",
    "단양": "NAT021784",
    "오송": "NAT050044",
    "청주": "NAT050114",
    "오근장": "NAT050215",
    "청주공항": "NAT050244",
    "증평": "NAT050366",
    "음성": "NAT050596",
    "주덕": "NAT050719",
    "충주": "NAT050827",
    "목행": "NAT050881",
    "삼탄": "NAT051006",
    "감곡장호원": "NAT280212",
    "앙성온천": "NAT280358",
    "장락": "NAT650050",
    "": "충청남도",
    "계룡": "NAT030254",
    "연산": "NAT030396",
    "논산": "NAT030508",
    "강경": "NAT030607",
    "아산": "NAT080045",
    "온양온천": "NAT080147",
    "신창": "NAT080216",
    "도고온천": "NAT080309",
    "신례원": "NAT080353",
    "예산": "NAT080402",
    "삽교": "NAT080492",
    "홍성": "NAT080622",
    "광천": "NAT080749",
    "청소": "NAT080827",
    "대천": "NAT080952",
    "판교": "NAT081240",
    "장항": "NAT081318",
    "서천": "NAT081343",
    "연무대": "NAT340090",
    "성환": "NAT010848",
    "천안": "NAT010971",
    "천안아산": "NATH10960",
    "공주": "NATH20438",
    "신판교": "NAT250007",
    "신웅천": "NAT081099",
    "": "전라북도",
    "용동": "NAT030667",
    "함열": "NAT030718",
    "익산": "NAT030879",
    "김제": "NAT031056",
    "신태인": "NAT031179",
    "정읍": "NAT031314",
    "삼례": "NAT040133",
    "동산": "NAT040173",
    "전주": "NAT040257",
    "신리": "NAT040352",
    "관촌": "NAT040496",
    "임실": "NAT040536",
    "오수": "NAT040667",
    "남원": "NAT040868",
    "군산": "NAT081388",
    "대야": "NAT320131",
    "": "전라남도",
    "백양사": "NAT031486",
    "장성": "NAT031638",
    "나주": "NAT031998",
    "다시": "NAT032099",
    "무안": "NAT032273",
    "몽탄": "NAT032313",
    "일로": "NAT032422",
    "임성리": "NAT032489",
    "목포": "NAT032563",
    "곡성": "NAT041072",
    "구례구": "NAT041285",
    "순천": "NAT041595",
    "율촌": "NAT041710",
    "여천": "NAT041866",
    "여수ＥＸＰＯ": "NAT041993",
    "여수": "NAT041993",
    "함평": "NAT032212",
    "진상": "NAT881538",
    "옥곡": "NAT881584",
    "광양": "NAT881708",
    "벌교": "NAT882034",
    "조성": "NAT882141",
    "예당": "NAT882194",
    "득량": "NAT882237",
    "보성": "NAT882328",
    "명봉": "NAT882416",
    "이양": "NAT882544",
    "능주": "NAT882666",
    "화순": "NAT882755",
    "남평": "NAT882847",
    "": "경상북도",
    "김천": "NAT012546",
    "아포": "NAT012700",
    "구미": "NAT012775",
    "사곡": "NAT012821",
    "약목": "NAT012903",
    "왜관": "NAT012968",
    "신동": "NAT013067",
    "경산": "NAT013395",
    "남성현": "NAT013542",
    "청도": "NAT013629",
    "풍기": "NAT022053",
    "영주": "NAT022188",
    "의성": "NAT022844",
    "탑리": "NAT022961",
    "화본": "NAT023127",
    "신녕": "NAT023279",
    "북영천": "NAT023424",
    "영천": "NAT023449",
    "아화": "NAT023601",
    "서경주": "NAT023821",
    "옥산": "NAT300200",
    "청리": "NAT300271",
    "상주": "NAT300360",
    "함창": "NAT300558",
    "점촌": "NAT300600",
    "용궁": "NAT300669",
    "개포": "NAT300733",
    "예천": "NAT300850",
    "봉화": "NAT600147",
    "봉성": "NAT600257",
    "춘양": "NAT600379",
    "임기": "NAT600476",
    "현동": "NAT600527",
    "분천": "NAT600593",
    "비동": "NAT600635",
    "양원": "NAT600655",
    "승부": "NAT600692",
    "석포": "NAT600768",
    "신나원": "NAT8B0082",
    "신안강": "NAT8B0190",
    "월포": "NAT8B0504",
    "장사": "NAT8B0595",
    "강구": "NAT8B0671",
    "영덕": "NAT8B0737",
    "하양": "NAT830200",
    "포항": "NAT8B0351",
    "김천구미": "NATH12383",
    "신경주": "NATH13421",
    "안동": "NAT022558",
    "입실": "NAT750933",
    "사방": "NAT751238",
    "안강": "NAT751296",
    "양자동": "NAT751325",
    "부조": "NAT751354",
    "효자": "NAT751418",
    "": "경상남도",
    "상동": "NAT013747",
    "밀양": "NAT013841",
    "삼랑진": "NAT013967",
    "원동": "NAT014058",
    "물금": "NAT014150",
    "한림정": "NAT880099",
    "진영": "NAT880177",
    "진례": "NAT880179",
    "창원중앙": "NAT880281",
    "창원": "NAT880310",
    "마산": "NAT880345",
    "중리": "NAT880408",
    "함안": "NAT880520",
    "군북": "NAT880608",
    "원북": "NAT880644",
    "평촌": "NAT880702",
    "반성": "NAT880766",
    "진성": "NAT880825",
    "진주": "NAT881014",
    "완사": "NAT881168",
    "북천": "NAT881269",
    "양보": "NAT881323",
    "횡천": "NAT881386",
    "하동": "NAT881460",
    "신창원": "NAT810048",
    "진해": "NAT810195",
};