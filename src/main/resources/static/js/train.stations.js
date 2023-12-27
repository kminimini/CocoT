// 전역 변수
/*
let currentPage = 1; // 현재 페이지
let totalPages = 1; // 전체 페이지 수

// 페이지가 로드될 때 실행되는 함수
document.addEventListener("DOMContentLoaded", function() {
    // 초기 페이지 로드 시 첫 번째 페이지의 기차역 목록을 가져옴
    fetchStationsByPage(currentPage);
});

// 페이지별 기차역 목록을 가져오는 함수
function fetchStationsByPage(page) {
    // 페이지 번호 갱신
    currentPage = page;

    // AJAX 요청 수행
    fetch(`/stations?cityCode=${selectedCityCode}&pageNo=${page}&numOfRows=10`)
        .then(response => {
            // HTTP 응답 상태 확인
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            // JSON 형태로 변환된 응답을 반환
            return response.json();
        })
        .then(stationListData => {
            // 기차역 목록을 업데이트
            updateTrainStationList(stationListData);

            // 페이징 정보 갱신
            updatePagination();

            // 서버로부터 데이터를 성공적으로 받아왔을 때 로그 출력
            console.log(`Successfully fetched train stations for page ${page}`);
        })
        .catch(error => {
            // 서버와의 통신 중 오류가 발생했을 때 에러 로그 출력
            console.error('Error during fetchStationsByPage:', error);
        });
}

// 실제로는 서버에서 데이터를 받아오도록 수정
function getStationListData(page) {
    // 이 함수는 더 이상 사용하지 않음
    // 대신 fetchStationsByPage 함수를 통해 서버에서 데이터를 받아오도록 함
    console.warn('Deprecated function: getStationListData');
}

// 기차역 목록을 업데이트하는 함수
function updateTrainStationList(data) {
    let trainStationList = document.getElementById("trainStationList");
    trainStationList.innerHTML = ""; // 목록 초기화

    // 기차역 목록을 순회하며 목록에 추가
    for (let station of data.stations) {
        let listItem = document.createElement("li");
        listItem.textContent = station.name;
        trainStationList.appendChild(listItem);
    }
}

// 페이징 정보를 업데이트하는 함수
function updatePagination() {
    // 페이징 관련 요소들 가져오기
    let currentPageElement = document.getElementById("currentPage");
    let firstPageButton = document.getElementById("firstPageButton");
    let previousButton = document.getElementById("previousButton");
    let nextButton = document.getElementById("nextButton");
    let lastPageButton = document.getElementById("lastPageButton");

    // 현재 페이지 표시 업데이트
    currentPageElement.textContent = "페이지: " + currentPage;

    // 페이지 번호에 따라 버튼 활성화/비활성화 처리
    if (currentPage === 1) {
        firstPageButton.classList.add("disabled");
        previousButton.classList.add("disabled");
    } else {
        firstPageButton.classList.remove("disabled");
        previousButton.classList.remove("disabled");
    }

    if (currentPage === totalPages) {
        nextButton.classList.add("disabled");
        lastPageButton.classList.add("disabled");
    } else {
        nextButton.classList.remove("disabled");
        lastPageButton.classList.remove("disabled");
    }
}

// 다음 페이지로 이동
function goToNextPage() {
    if (currentPage < totalPages) {
        fetchStationsByPage(currentPage + 1);
    }
}

// 이전 페이지로 이동
function goToPreviousPage() {
    if (currentPage > 1) {
        fetchStationsByPage(currentPage - 1);
    }
}

// 처음 페이지로 이동
function goToFirstPage() {
    if (currentPage > 1) {
        fetchStationsByPage(1);
    }
}

// 마지막 페이지로 이동
function goToLastPage() {
    if (currentPage < totalPages) {
        fetchStationsByPage(totalPages);
    }
}
*/