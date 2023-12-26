/* train.js */
// 도시 번호로 기차역 보기
function promptForCityCode() {
	var cityCode = prompt("Please enter the city code:", "");
	if (cityCode != null && cityCode != "") {
		window.location.href = "/train/stations?cityCode=" + cityCode;
	}
}
/*
	var currentPage = 1;
    var totalPages = 1;

    function fetchStationsByPage(page) {
        // 페이지 번호 갱신
        currentPage = page;

        // 동적으로 도시 코드 설정 (사용자 입력이나 다른 동적 요소에 따라 설정)
        var cityCode = prompt("도시 코드를 입력하세요:", "DEFAULT_CITY_CODE");
        if (!cityCode) {
            alert("도시 코드가 필요합니다.");
            return;
        }

        // 서버에 페이지별로 기차역 목록을 요청
        var url = `/train/stations?cityCode=${cityCode}&pageNo=${page}&numOfRows=10`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                // 기차역 목록 업데이트
                displayTrainStations(data);

                // 페이징 정보 업데이트
                totalPages = data.response.body.totalCount;
                document.getElementById("currentPage").innerText = `Page ${currentPage} of ${totalPages}`;
            })
            .catch(error => {
                console.error("기차역 목록 가져오기 오류:", error);
            });
    }
    */
var currentPage = 1;
var totalPages = 1;
var selectedCityCode = "";  // Variable to store the selected city code

function fetchStationsByPage(page) {
    // Update page number
    currentPage = page;

    // Request a list of train stations by page from the server
    var url = `/train/stations?cityCode=${selectedCityCode}&pageNo=${page}&numOfRows=10`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            // Update train station list
            displayTrainStations(data);

            // Update paging information
            totalPages = data.response.body.totalCount;
            document.getElementById("currentPage").innerText = `Page ${currentPage} of ${totalPages}`;
        })
        .catch(error => {
            console.error("Error retrieving train station list:", error);
        });
}

// 선택한 도시 코드를 설정하는 기능
function setSelectedCityCode(cityCode) {
    selectedCityCode = cityCode;
}
// 출발 날짜 선택 후 검색 : ex) 2023-11-11 -> 20231111 (하이픈 제외하고 요청)
document.getElementById('trainInfoForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 기본 양식 제출 방지

    var depPlaceId = document.getElementById('departureStationId').value;
    var arrPlaceId = document.getElementById('arrivalStationId').value;
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
    // 중첩된 데이터 구조에서 스테이션 배열 추출하기
    var stations = data.response.body.items.item;

    // 스테이션이 배열인지 확인
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
			document.getElementById("departureStationId").value = station.nodeid;
			document.getElementById("departureStationName").value = station.nodename;
			closeDepartureLocationPopup();
		};
        stationList.appendChild(listItem);
    });

    // 기차역 목록이 포함된 팝업 표시
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
