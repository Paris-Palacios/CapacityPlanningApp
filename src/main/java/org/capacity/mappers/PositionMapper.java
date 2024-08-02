package org.capacity.mappers;

import org.capacity.models.Position;
import org.capacity.utilities.Constants;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionMapper implements RowMapper<Position> {
    @Override
    public Position map(ResultSet rs, StatementContext ctx) throws SQLException {
        Position position = new Position();
        position.setId(rs.getInt(Constants.ID_COLUMN));
        position.setPositionName(rs.getString(Constants.POSITION_COLUMN));
        return position;
    }
}
