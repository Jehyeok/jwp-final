package next.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.Controller;

public class JsonAPIController implements Controller {
	private QuestionDao questionDao = new QuestionDao();
	private List<Question> questions;
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		questions = questionDao.findAll();
		
		// set Gson
		GsonBuilder gb = new GsonBuilder();
		gb.serializeNulls();
		Gson gson = gb.create();
				
		// change to JSON
		String questionsAsJson = gson.toJson(questions);
		
		PrintWriter out = response.getWriter();
		out.print(questionsAsJson);
		return "not forward";
	}

}
