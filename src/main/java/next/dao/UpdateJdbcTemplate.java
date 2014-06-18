package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import next.model.Question;
import next.support.db.ConnectionManager;

public abstract class UpdateJdbcTemplate {
	Connection con = null;
	
	public UpdateJdbcTemplate(Connection con) {
		this.con = con;
	}

	public void update(Question question, String sql) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql);
			setValues(pstmt);

			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}
	}

	public void insert(Question question, String sql) throws SQLException {
		PreparedStatement pstmt = null;
		
		pstmt = con.prepareStatement(sql);
		setValues(pstmt);

		pstmt.executeUpdate();
		if (pstmt != null) {
			pstmt.close();
		}

		if (con != null) {
			con.close();
		}
	}
	
	abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
