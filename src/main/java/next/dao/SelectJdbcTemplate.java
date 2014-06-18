package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.Question;
import next.support.db.ConnectionManager;

public abstract class SelectJdbcTemplate {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public SelectJdbcTemplate(Connection con) {
		this.con = con;
	}

	public List<Question> findAll(String sql, RowMapperForList rm) throws SQLException {
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			List<Question> questions = new ArrayList<Question>();
			while (rs.next()) {
				rm.mapRow(rs, questions);
			}

			return questions;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}
	
	public Question findById(String sql, RowMapperForOneRecord rm) throws SQLException {
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql);
			setValues(pstmt);

			rs = pstmt.executeQuery();

			Question question = null;
			if (rs.next()) {
				question = rm.mapRow(rs);
			}

			return question;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}
	
	abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
