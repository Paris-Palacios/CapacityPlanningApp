package org.capacity.mappers;

import org.capacity.models.CostCenter;
import org.capacity.utilities.Constants;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CostCenterMapper implements RowMapper<CostCenter> {
    @Override
    public CostCenter map(ResultSet rs, StatementContext ctx) throws SQLException {
        CostCenter costCenter = new CostCenter();
        costCenter.setId(rs.getInt(Constants.ID_COLUMN));
        costCenter.setCostCenterName(rs.getString(Constants.COST_CENTER_COLUMN));
        return costCenter;
    }
}
