// 도시 번호로 기차역 보기
function promptForCityCode() {
	var cityCode = prompt("Please enter the city code:", "");
	if (cityCode != null && cityCode != "") {
		window.location.href = "/train/stations?cityCode=" + cityCode;
	}
}
// 출발지 팝업을 열 때 이 함수를 호출.
function openDepartureLocationPopup() {
	var popup = document.getElementById("departureLocationPopup");
	popup.style.display = "block";

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

// 출발지 팝업 닫기 기능
function closeDepartureLocationPopup() {
	var popup = document.getElementById("departureLocationPopup");
	popup.style.display = "none";
}

// 도시 코드 목록을 채우는 함수
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
			console.log("선택한 도시 코드:", city.code);
			// 도시 코드를 선택한 후 팝업을 닫음.
			closeDepartureLocationPopup();
			// 선택한 도시의 기차역 가져오기 및 표시하기
			fetchTrainStationsByCityCode(city.code);
		});
		cityCodeList.appendChild(listItem);
	});
}

// 선택한 도시의 기차역을 불러와 표시하는 기능
function fetchTrainStationsByCityCode(cityCode) {
    // Replace the following URL with your actual URL for fetching train stations by city code
    var url = "/train/stations?cityCode=" + cityCode;

    // Make a fetch request
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 정상적이지 않음');
            }
            return response.json();  // 텍스트()로 변경하여 원시 응답 텍스트를 가져옵니다.
        })
        .then(data => {
            // Log the parsed JSON data to the console
            console.log("Parsed JSON data:", data);

            // Update the UI with the fetched train station data
            displayTrainStations(data);
        })
        .catch(error => {
            console.error("기차역 불러오기 오류:", error);
        });
}

// UI에 기차역을 표시하는 기능
function displayTrainStations(data) {
    // 팝업에 기차역을 표시하는 요소가 있다고 가정합니다.
    try {
		console.log("기차역 표시:", data);
		 
		// 가져온 기차역 데이터로 UI를 업데이트합니다.
		var stationList = document.getElementById("trainStationList");
        stationList.innerHTML = "";

        trainStations.forEach(station => {
            var listItem = document.createElement("li");
            listItem.textContent = station.nodename;
            stationList.appendChild(listItem);
        });
        // 팝업에 기차역 목록 표시
        var popup = document.getElementById("departureLocationPopup");
        popup.style.display = "block";
	} catch (error) {
		console.error("기차역 데이터 파싱 오류:", error);
	}
}