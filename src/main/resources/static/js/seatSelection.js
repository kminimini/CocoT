/* seatSelection.js */

 $(document).ready(function() {
    // 좌석 선택을 위한 JavaScript 로직
    // 여기에 실제 좌석 정보를 동적으로 생성하는 로직을 추가하세요.

    // 예약 정보 설정
    $("#trainNo").text("열차 번호");
    $("#trainGrade").text("열차 등급");
    $("#depPlace").text("출발지");
    $("#depTime").text("출발 시간");
    $("#arrPlace").text("도착지");
    $("#arrTime").text("도착 시간");
    $("#adultCharge").text("요금");

    // 좌석 정보 동적 생성 예시
    var seatMap = $("#seatMap");
    for (var i = 1; i <= 5; i++) {
        var row = $("<div class='seat-row'></div>");
        for (var j = 1; j <= 4; j++) {
            var seat = $("<div class='seat' data-seat='" + i + "-" + j + "'>" + i + "-" + j + "</div>");
            row.append(seat);
        }
        seatMap.append(row);
    }

    // 좌석 클릭 이벤트
    $(".seat").on("click", function() {
        $(".seat").removeClass("selected");
        $(this).addClass("selected");
        $("#selectedSeat").val($(this).data("seat"));
    });
});
