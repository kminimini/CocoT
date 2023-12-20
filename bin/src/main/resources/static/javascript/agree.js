function checkBoxes() {
	var agree1 = document.getElementById("agree1");
	var agree2 = document.getElementById("agree2");
	var agree3 = document.getElementById("agree3");
	var checkAll = document.getElementById("checkAll");
	
        agree1.checked = checkAll.checked;
        agree2.checked = checkAll.checked;
        agree3.checked = checkAll.checked;
}

function validateForm() {
    var agree1 = document.getElementById("agree1").checked;
    var agree2 = document.getElementById("agree2").checked;
    var agree3 = document.getElementById("agree3").checked;

    if (!agree1 || !agree2) {
        alert("약관에 동의해야 가입할 수 있습니다.");
        return false;
    }

    // 여기에 추가적인 검증 로직이 있다면 추가하세요.
	window.location.href = "/system/join";
    return true;
}

  
    
    
    
   