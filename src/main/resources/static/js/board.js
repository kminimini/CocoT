/**
 * 
 */
function toggleEditForm(formId) {
	console.log("Toggle edit form with ID:", formId);

	var editForm = document.getElementById(formId);

	// 선택된 댓글의 수정 폼을 제외한 모든 수정 폼을 숨김
	var editForms = document.querySelectorAll('.edit-form');
	editForms.forEach(function(form) {
		form.style.display = 'none';
	});

	// 선택된 댓글의 수정 폼을 나타냄
	if (editForm) {
		editForm.style.display = 'block';
	}
}

function toggleSubReplyForm(subReplyFormId) {
	console.log("Toggle sub-reply form with ID:", subReplyFormId);

	var subReplyForm = document.getElementById(subReplyFormId);

	// 선택된 대댓글 등록 폼을 토글
	var subReplyForms = document.querySelectorAll('.sub-reply-form');
	subReplyForms.forEach(function(form) {
		form.style.display = 'none';
	});

	// 해당 댓글에 속한 다른 대댓글 등록 폼을 숨김
	if (subReplyForm) {
		subReplyForm.style.display = 'block';
	}
}
function submitOnEnter(event, textarea) {
	if (event.key === 'Enter' && !event.shiftKey) {
		event.preventDefault();
		textarea.form.submit();
		return false;
	}
	return true;
}