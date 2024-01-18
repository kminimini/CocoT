// Origin: 입력 중 자동 완성 및 검색 기능
function searchTrainStations(input) {
    // 사용자가 입력한 값 가져오기
    var inputValue = input.value.toUpperCase();

    // 도시 또는 기차역 코드를 얻기 위해 백엔드 요청하기
    fetch(`/train/search?term=${inputValue}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Backend request failed');
            }
            return response.json();
        })
        .then(data => {
            // 백엔드에서 수신한 데이터 처리
            console.log('Backend response:', data);

            // 할 일: 백엔드 응답을 처리하는 로직 구현하기
        })
        .catch(error => {
            console.error('Backend request error:', error);
        });

    // 기차역 목록 요소 가져오기
    var stationList = document.getElementById("trainStationList");

    // 기차역 목록 가져오기 및 반복하기
    var stations = stationList.getElementsByTagName("li");
    for (var i = 0; i < stations.length; i++) {
        var stationName = stations[i].textContent.toUpperCase();
        // 입력한 값과 기차역 이름을 비교하여 일치하는지 확인합니다.
        if (stationName.includes(inputValue)) {
            // 일치하는 항목이 있으면 해당 기차역을 표시합니다.
            stations[i].style.display = "";
        } else {
            stations[i].style.display = "none";
        }
    }
}

// Station: 입력 상자의 텍스트가 변경될 때마다 searchTrainStations 함수를 호출합니다.
document.getElementById("departureStationName").addEventListener("input", function() {
    searchTrainStations(this);
});