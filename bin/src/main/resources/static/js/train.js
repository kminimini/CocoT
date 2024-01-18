// 출발역 검색 버튼 클릭 시 팝업 창 표시
document.getElementById("startStationButton").addEventListener("click", function() {
    document.getElementById("startStationPopup").style.display = "block";
    // 기차역 목록을 가져와서 팝업 창에 표시하는 로직 추가
    // 기차역 목록은 AJAX 또는 서버에서 가져올 수 있습니다.
    // 이 예제에서는 JavaScript 배열로 가정하고 동적으로 목록을 생성합니다.
    var startStations = ["역1", "역2", "역3"]; // 예시 기차역 목록
    var startStationList = document.getElementById("startStationList");
    startStationList.innerHTML = ""; // 목록 초기화
    startStations.sort(); // 기차역 목록 정렬
    startStations.forEach(function(station) {
        var li = document.createElement("li");
        li.textContent = station;
        startStationList.appendChild(li);
    });
});

// 도착역 검색 버튼 클릭 시 팝업 창 표시
document.getElementById("endStationButton").addEventListener("click", function() {
    document.getElementById("endStationPopup").style.display = "block";
    // 도착역 목록을 가져와서 팝업 창에 표시하는 로직 추가
    // 기차역 목록은 AJAX 또는 서버에서 가져올 수 있습니다.
    // 이 예제에서는 JavaScript 배열로 가정하고 동적으로 목록을 생성합니다.
    var endStations = ["역4", "역5", "역6"]; // 예시 기차역 목록
    var endStationList = document.getElementById("endStationList");
    endStationList.innerHTML = ""; // 목록 초기화
    endStations.sort(); // 기차역 목록 정렬
    endStations.forEach(function(station) {
        var li = document.createElement("li");
        li.textContent = station;
        endStationList.appendChild(li);
    });
});

// 기차역 목록에서 역을 선택할 때의 동작 추가 (출발역 및 도착역에 선택 역 채우기)
document.querySelectorAll("#startStationList li").forEach(function(li) {
    li.addEventListener("click", function() {
        var selectedStation = li.textContent;
        document.getElementById("startStationInput").value = selectedStation;
        document.getElementById("startStationPopup").style.display = "none";
    });
});

document.querySelectorAll("#endStationList li").forEach(function(li) {
    li.addEventListener("click", function() {
        var selectedStation = li.textContent;
        document.getElementById("endStationInput").value = selectedStation;
        document.getElementById("endStationPopup").style.display = "none";
    });
});