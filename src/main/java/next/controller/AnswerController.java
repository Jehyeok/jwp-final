package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import core.mvc.Controller;

public class AnswerController implements Controller {
	private AnswerDao answerDao = new AnswerDao();
	private QuestionDao questionDao = new QuestionDao();
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String writer = request.getParameter("writer");
		String contents = request.getParameter("contents");
		long questionId = Long.parseLong(request.getParameter("questionId"));
		
		// 답변 추가
		Answer answer = new Answer(writer, contents, questionId);
		answerDao.insert(answer);
		
		// 댓글 수 업데이트
		Question question = questionDao.findById(questionId);
		question.incCountOfComment();
		questionDao.update(question);
		
		// set Gson
		GsonBuilder gb = new GsonBuilder();
		gb.serializeNulls();
		Gson gson = gb.create();
		
		// change to JSON
		String answerAsJson = gson.toJson(answer);
		
		response.getWriter().write(answerAsJson);
		
		return "ajax";
	}
	
}
