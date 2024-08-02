package org.capacity.mappers;

import org.capacity.models.Office;
import org.capacity.utilities.Constants;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfficeMapper implements RowMapper<Office> {
    @Override
    public Office map(ResultSet rs, StatementContext ctx) throws SQLException {
        Office office = new Office();
        office.setId(rs.getInt(Constants.ID_COLUMN));
        office.setOfficeName(rs.getString(Constants.OFFICE_COLUMN));
        return office;
    }
}
