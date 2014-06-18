package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import next.model.Question;

public interface RowMapperForOneRecord {
	abstract Question mapRow(ResultSet rs) throws SQLException;
}
