/*function joinForm() {
	var id = document.getElementById('id');
	var email = document.getElementById('email');
	var password = document.getElementById('password');
	var cpassword = document.getElementById('cpassword');
	var name = document.getElementById('name');
	var ename = document.getElementById('ename');
	var rrnumber = document.getElementById('rrnumber');
	var address = document.getElementById('address');
	var detailaddress = document.getElementById('detailAddress');
	var phone = document.getElementById('phone');


	// 아이디 영어 소문자, 숫자 포함 확인
	if (!id.value) {
		alert("아이디를 입력하세요.");
		id.focus();
		return false;
	}

	//메일주소 확인
	if (email.value.length == 0) {
		alert("메일주소를 입력하세요.")
		email.focus();
		return false;
	}

	else if (email.value.indexOf("@") == -1 || email.value.indexOf(".") == -1 || email.value.length <= 5) {
		alert("잘못된 이메일 형식입니다.")
		email.focus();
		return false;
	}

	//비밀번호 확인
	if (password.value.length < 8 || password.value.length > 16) {
		alert("비밀번호를 글자 8글자 이상 16글자 이하로 입력하세요.")
		password.focus();
		return false;
	}
	//비밀번호 영어 대소문자 확인
	else if (!checkEngNumber(password.value)) {
		alert("영문 대소문자, 숫자를 포함해서 입력하세요.")
		password.focus();
		return false;
	}
	//비밀번호와 아이디 비교
	else if (password.value == id.value) {
		alert("아이디와 동일한 비밀번호를 사용할 수 없습니다.")
		password.focus();
		return false;
	}


	//비밀번호 확인
	if (cpassword.value !== password.value) {
		alert("비밀번호와 동일하지 않습니다.")
		cpassword.focus();
		return false;
	}

	//이름 확인 = 한글과 영어만 가능하도록
	if (name.value == "") {
		alert("이름을 입력하세요.")
		name.focus();
		return false;
	}

	else if (!containsOnlyKorean(name.value)) {
		alert("한글만 입력하세요.")
		name.focus();
		return false;
	}

	if (ename.value == "") {
		alert("영문 이름을 입력하세요.")
		ename.focus();
		return false;
	}

	else if (!checkEng(ename.value)) {
		alert("영어 대문자만 입력하세요.")
		ename.focus();
		return false;
	}


	if (rrnumber.value == "") {
		alert('생년월일 8자리를 입력해주세요.');
		rrnumber.focus();
		return false;
	}

	else if (rrnumber.value.length < 8 || password.value.length > 18) {
		alert("생년월일 8자리를 입력하세요")
		rrnumber.focus();
		return false;
	}

	else if (!rrnumber.checkValidity()) {
		alert("생년월일을 날짜에 맞게 입력하세요");
		rrnumber.focus();
		return false;
	}


	if (address.value == "") {
		alert('주소를 입력해주세요.');
		address.focus();
		return false;
	}

	if (detailaddress.value == "") {
		alert('상세주소를 입력해주세요.');
		detailaddress.focus();
		return false;
	}

	if (phone.value == "") {
		alert('전화번호를 입력해주세요.');
		phone.focus();
		return false;
	}
	return true;
}

console.log("가입하기 버튼 클릭 시 joinForm 함수 호출");

// 영어, 한글인지(가~힣) 확인
function checkKorEng(value) {
	var regex = /^[a-zA-Z가-힣]+$/;
	return regex.test(value);
}

function checkEng(value) {
	var regex = /^[A-Z]+$/;
	return regex.test(value);
}



function containsOnlyEngKorNum(value) {
	// 정규표현식을 사용하여 영어 대소문자, 숫자, 한글로만 구성되어 있는지 확인
	var regex = /^[a-zA-Z0-9가-힣]+$/;
	return regex.test(value);
}

function containsOnlyKorean(value) {
	// 정규표현식을 사용하여 한국어만 포함되어 있는지 확인
	var regex = /^[가-힣]+$/;
	return regex.test(value);
}




//전화번호 자동 - 추가
(function() {
	'use strict';

	window.addEventListener('load', function() {
		var forms = document.getElementsByClassName('needs-validation');

		Array.prototype.filter.call(forms, function(form) {
			form.addEventListener('submit', function(event) {
				if (form.checkValidity() === false) {
					event.preventDefault();
					event.stopPropagation();
				}

				form.classList.add('was-validated');
			}, false);
		});

		// 전화번호 입력 필드에 자동으로 하이픈 추가
		var phoneInput = document.getElementById('phone');
		phoneInput.addEventListener('input', function(event) {
			var inputValue = event.target.value;
			inputValue = inputValue.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
			if (inputValue.length >= 3 && inputValue.length <= 7) {
				inputValue = inputValue.replace(/(\d{3})(\d{1,4})/, '$1-$2');
			} else if (inputValue.length > 7) {
				inputValue = inputValue.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
			}
			event.target.value = inputValue;
		});
	}, false);
}());

//생년월일 . 자동 생성	
function rrnumber(value) {
	// 숫자만 추출
	var birthday = value.replace(/[^\d]/g, '');

	// 패턴에 맞게 변환 (예: 19991212 -> 1999.12.12)
	var rrnumber = birthday.replace(/(\d{4})(\d{2})(\d{2})/, '$1.$2.$3');

	return rrnumber;
}

function formatBirthdate(inputElement) {
	var inputValue = inputElement.value.replace(/[^\d]/g, ''); // 숫자 이외의 문자 제거
	var formattedValue = rrnumber(inputValue);
	inputElement.value = formattedValue;
}

// 숫자 입력 시 생년월일에 자동으로 '.' 삽입
document.getElementById('rrnumber').addEventListener('input', function(event) {
	formatBirthdate(event.target);
});


*/