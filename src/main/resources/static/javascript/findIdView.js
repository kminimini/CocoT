// 폼 제출 시 findId 함수 호출
document.querySelector('form').addEventListener('submit', function(event) {
	event.preventDefault();

	// 입력된 이름과 전화번호 가져오기
	var name = document.getElementById("name").value;
	var phone = document.getElementById("phone").value;

	// 이름 확인 - 한글과 영어만 가능하도록
	if (name === "") {
		alert("이름을 입력하세요.");
		document.getElementById("name").focus();
		return false;
	} else if (!containsOnlyKorean(name)) {
		alert("한글만 입력하세요.");
		document.getElementById("name").focus();
		return false;
	}

	if (phone === "") {
		alert('전화번호를 입력해주세요.');
		document.getElementById("phone").focus();
		return false;
	}

	// findId 함수 호출
	findId(name, phone);
});

function containsOnlyKorean(value) {
	// 정규표현식을 사용하여 한국어만 포함되어 있는지 확인
	var regex = /^[가-힣]+$/;
	return regex.test(value);
}

function findId(name, phone) {
	// AJAX를 이용해 서버에 요청 보내기
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "/findIdView", true);
	xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

	// 요청 데이터 설정
	var requestData = {
		name: name,
		phone: phone
	};

	// 요청 완료 시의 콜백 함수
	xhr.onload = function() {
		if (xhr.status === 200) {
			var responseData = JSON.parse(xhr.responseText);

			if (responseData.success) {
				// 계정이 일치하는 경우
				alert("아이디는: " + responseData.email + " 입니다.");
			} else {
				// 계정이 일치하지 않는 경우
				alert("입력한 정보와 일치하는 계정을 찾을 수 없습니다. 다시 확인해주세요.");
			}
		} else {
			alert("서버 오류가 발생했습니다.");
		}
	};

	// 요청 전송
	xhr.send(JSON.stringify(requestData));
}