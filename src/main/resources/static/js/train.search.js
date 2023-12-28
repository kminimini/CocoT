// Origin: 입력 중 자동 완성 및 검색 기능
function searchTrainStations(input) {
    // 사용자가 입력한 값 가져오기
    var inputValue = input.value.toUpperCase();

    // Make a backend request to obtain the city or train station code
    fetch(`/train/search?term=${inputValue}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Backend request failed');
            }
            return response.json();
        })
        .then(data => {
            // Process the data received from the backend
            console.log('Backend response:', data);

            // TODO: Implement logic to handle the backend response
        })
        .catch(error => {
            console.error('Backend request error:', error);
        });

    // Get train station list elements
    var stationList = document.getElementById("trainStationList");

    // Get the list of train stations and iterate over them
    var stations = stationList.getElementsByTagName("li");
    for (var i = 0; i < stations.length; i++) {
        var stationName = stations[i].textContent.toUpperCase();
        // Compare the entered value with the train station name to check if they match
        if (stationName.includes(inputValue)) {
            // If there is a match, display the corresponding train station
            stations[i].style.display = "";
        } else {
            // If there is no match, hide the train station
            stations[i].style.display = "none";
        }
    }
}

// Station: Call the searchTrainStations function whenever the text in the input box changes.
document.getElementById("departureStationName").addEventListener("input", function() {
    searchTrainStations(this);
});