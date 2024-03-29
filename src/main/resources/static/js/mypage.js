function togglePasswordForm() {
	var passwordForm = document.getElementById('passwordForm');
	passwordForm.style.display = passwordForm.style.display === 'none' ? 'block' : 'none';
}

function validatePasswordForm() {
	var currentPassword = document.getElementById('currentPassword').value;
	var newPassword = document.getElementById('newPassword').value;
	var confirmPassword = document.getElementById('confirmPassword').value;

	// 콘솔에 입력값 출력
	console.log("currentPassword:", currentPassword);
	console.log("newPassword:", newPassword);
	console.log("confirmPassword:", confirmPassword);

	if (currentPassword == "") {
		alert("현재 비밀번호를 입력해 주세요!.");
		document.getElementById('currentPassword').focus();
		return false;
	} else if (newPassword == "") {
		alert("새 비밀번호를 입력해 주세요!.");
		document.getElementById('newPassword').focus();
		return false;
	} else if (newPassword != confirmPassword) {
		alert("새로운 비밀번호가 맞지 않습니다. 다시 입력해 주세요!");
		return false;
	} else {
		var confirmChange = confirm("비밀번호를 변경하시겠습니까?");

		if (confirmChange) {
			$.ajax({
				type: 'POST',
				url: '/myPage/changePassword',
				data: {
					currentPassword: currentPassword,
					newPassword: newPassword
				},
				success: function(response) {
					console.log("서버 응답:", response);

					if (response === 'success') {
						alert("비밀번호가 변경되었습니다. 로그아웃됩니다.");

						$.ajax({
							type: 'POST',
							url: '/system/logout', // 기본적으로 Spring Security에서 사용하는 로그아웃 경로
							success: function() {
								// 로그아웃 성공 시 로그인 페이지로 리디렉션
								window.location.replace('/system/login');
							},
							error: function() {
								alert('로그아웃 중 오류가 발생했습니다.');
								// 오류 발생 시 기본적으로 로그인 페이지로 이동
								window.location.replace('/system/login');
							}
						});
					} else {
						alert('현재 비밀번호가 일치하지 않습니다.');
						document.getElementById('currentPassword').focus();
					}
				},
				error: function() {
					alert('서버 오류가 발생했습니다.');
				}
			});
		} else {
			alert('비밀번호 변경이 취소되었습니다.');
			// 변경 취소 시 폼을 감추도록 수정
			document.getElementById('passwordForm').style.display = 'none';
		}

		return false;
	}
}

function toggleWithdrawPassword() {
	var withdrawPasswordForm = document.getElementById('withdrawPasswordForm');
	withdrawPasswordForm.style.display = 'block';
}

function confirmWithdraw() {
	var withdrawPassword = document.getElementById('withdrawPassword').value;
	var currentPassword = 'your_current_password_value_here'; // 이 부분에 현재 비밀번호 값을 추가

	var confirmWithdraw = confirm('정말 탈퇴하시겠습니까?');

	if (confirmWithdraw) {
		$.ajax({
			type: 'POST',
			url: '/myPage/deleteAccount',
			data: {
				withdrawPassword: withdrawPassword,
				currentPassword: currentPassword, // 이 부분을 추가
			},
			success: function(response) {
				console.log("서버 응답:", response);

				if (response == 'success') {
					// 탈퇴 성공 시, 로그인 페이지로 이동
					location.reload(true);
				} else {
					// 현재 비밀번호가 유효하지 않은 경우
					alert('비밀번호가 틀렸습니다');
					document.getElementById('withdrawPassword').focus();
				}
			},
			error: function(xhr, status, error) {
				console.error('AJAX 오류:', status, error);
				alert('서버 오류가 발생했습니다.');
			}
		});
	} else {
		alert('탈퇴가 취소되었습니다.');
	}
}