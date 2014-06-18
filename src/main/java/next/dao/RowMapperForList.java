package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import next.model.Question;

public interface RowMapperForList {
	abstract void mapRow(ResultSet rs, List<Question> questions)
			throws SQLException;
}
