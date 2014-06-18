var formList = document.querySelectorAll('.answerWrite input[type=submit]');
for ( var j=0 ; j < formList.length ; j++) {
	formList[j].addEventListener('click', writeAnswers, false);
}

function writeAnswers(e) {
	 e.preventDefault();
	 
	 var answerForm = e.currentTarget.form;
	 var url = "/api/addanswer.next";
	 var params = "questionId=" + answerForm[0].value + "&writer=" + answerForm[1].value + "&contents=" + answerForm[2].value;

	 var request = new XMLHttpRequest();
	 request.open("POST", url, true);
	 request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	 
	 request.onreadystatechange = function(obj) {
		 if(request.readyState == 4 && request.status == 200) {
			 // 응답 데이터 받아옴
			 var responseText = obj.target.responseText;
			 var parsedResponseText = JSON.parse(responseText);
			 
			 var commentsDiv = document.querySelector('.comments');
			 var comments = document.querySelectorAll('.comment');
			 var commentDiv = document.createElement('div');
			 commentDiv.className = "comment";
			 
			 commentDiv.innerHTML = '<div class="comment-metadata">' +
             						'<span class="comment-author">' + parsedResponseText.writer + ',' + '</span>' +
             						'<span class="comment-date">' + parsedResponseText.createdDate + '</span>' +
             						'</div>' +
             						'<div class="comment-content">' + '<div class="about">내용 : </div>' + 
             						parsedResponseText.contents +
             						'</div>';
             						
             commentsDiv.insertBefore(commentDiv, comments[0]);
             
             // 댓글 수 업데이트
             var countOfComment = parseInt(commentsDiv.querySelector('h3').innerText.split(':')[1], 10);
             commentsDiv.querySelector('h3').innerText = "댓글 수 : " + ++countOfComment;
		 }
	 }
	 
	 request.send(params);
}
