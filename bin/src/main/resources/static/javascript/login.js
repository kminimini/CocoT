
function validateForm() {
	var email = document.getElementById('email');
	var password = document.getElementById('password');

	if (email.value == "") {
		alert('이메일을 입력해주세요.');
		email.focus();
		return false;

	} else if (email.value.indexOf("@") == -1 || email.value.indexOf(".") == -1 || email.value.length <= 5) {
		alert("잘못된 이메일 형식입니다.")
		email.focus();
		return false;
	}

	if (password.value == "") {
		alert('비밀번호를 입력해주세요.');
		password.focus();
		return false;
	}

/*// AJAX를 사용하여 서버로 데이터 전송
    $.ajax({
        type: "POST",
        url: "login",
        data: {
            "username": email.value,
            "password": password.value
        },
        success: function (res) {
            console.log(res);
            if (res === "success") {
                console.log("로그인 성공");
                alert("로그인 성공");
                // Delay the redirection slightly after showing the alert
                window.location.href = "/index";
            } else {
                console.log("로그인 실패");
                alert("이메일이나 비밀번호가 틀렸습니다.");
            }
        },
        error: function (xhr, status, error) {
            console.error(xhr.responseText);
        }
    });

    // 폼이 서버로 전송되지 않도록 false 반환
    return false;
}*/
}