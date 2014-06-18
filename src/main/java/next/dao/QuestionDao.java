package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import next.model.Question;
import next.support.db.ConnectionManager;

public class QuestionDao {

	public void insert(final Question question) throws SQLException {
		Connection con = null;
		con = ConnectionManager.getConnection();
		
		UpdateJdbcTemplate template = new UpdateJdbcTemplate(con) {

			@Override
			void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, question.getWriter());
				pstmt.setString(2, question.getTitle());
				pstmt.setString(3, question.getContents());
				pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
				pstmt.setInt(5, question.getCountOfComment());
			}
		};
		
		String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfComment) VALUES (?, ?, ?, ?, ?)";
		template.insert(question, sql);
	}
	
	public void update(final Question question) throws SQLException {
		Connection con = null;
		con = ConnectionManager.getConnection();
		
		UpdateJdbcTemplate template = new UpdateJdbcTemplate(con){

			@Override
			void setValues(PreparedStatement pstmt)
					throws SQLException {
				pstmt.setInt(1, question.getCountOfComment());
				pstmt.setTimestamp(2, new Timestamp(question.getTimeFromCreateDate()));
				pstmt.setLong(3, question.getQuestionId());
			}
		};
		String sql = "UPDATE QUESTIONS SET countOfComment = ?, createdDate = ? WHERE questionId = ?";
		template.update(question, sql);
	}

	public List<Question> findAll() throws SQLException {
		Connection con = null;
		con = ConnectionManager.getConnection();
		RowMapperForList rm = new RowMapperForList() {

			@Override
			public void mapRow(ResultSet rs, List<Question> questions)
					throws SQLException {
				Question question;
				question = new Question(rs.getLong("questionId"),
						rs.getString("writer"), rs.getString("title"), null,
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfComment"));
				questions.add(question);
			}
		};
		
		SelectJdbcTemplate template = new SelectJdbcTemplate(con) {
			
			@Override
			void setValues(PreparedStatement pstmt) throws SQLException {}
		};
		String sql = "SELECT questionId, writer, title, createdDate, countOfComment FROM QUESTIONS "
				+ "order by questionId desc";
		return template.findAll(sql, rm);
	}

	public Question findById(final long questionId) throws SQLException {
		Connection con = null;
		con = ConnectionManager.getConnection();
		
		RowMapperForOneRecord rm = new RowMapperForOneRecord() {
			
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				Question question;
				question = new Question(rs.getLong("questionId"),
						rs.getString("writer"), rs.getString("title"),
						rs.getString("contents"),
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfComment"));
				return question;
			}
		};
		
		SelectJdbcTemplate template = new SelectJdbcTemplate(con) {
			
			@Override
			void setValues(PreparedStatement pstmt)
					throws SQLException {
				pstmt.setLong(1, questionId);
			}

		};
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfComment FROM QUESTIONS "
				+ "WHERE questionId = ?";
		return template.findById(sql, rm);
	}
}
