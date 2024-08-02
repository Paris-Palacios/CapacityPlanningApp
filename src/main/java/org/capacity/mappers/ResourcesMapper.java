package org.capacity.mappers;

import org.capacity.models.CostCenter;
import org.capacity.models.Office;
import org.capacity.models.Position;
import org.capacity.models.Resource;
import org.capacity.utilities.Constants;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResourcesMapper implements RowMapper<Resource> {
    @Override
    public Resource map(ResultSet rs, StatementContext ctx) throws SQLException {
        Resource resource = new Resource();
        resource.setId(rs.getInt(Constants.ID_COLUMN));
        resource.setResourceName(rs.getString(Constants.RESOURCE_NAME_COLUMN));
        resource.setOffice(Office.builder()
                        .id(rs.getInt(Constants.OFFICE_ID_COLUMN))
                        .officeName(rs.getString(Constants.OFFICE_COLUMN)).build());
        resource.setCostCenter(CostCenter.builder()
                .id(rs.getInt(Constants.COST_CENTER_ID_COLUMN))
                .costCenterName(rs.getString(Constants.COST_CENTER_COLUMN)).build());
        resource.setPosition(Position.builder()
                .id(rs.getInt(Constants.POSITION_ID_COLUMN))
                .positionName(rs.getString(Constants.POSITION_COLUMN)).build());
        resource.setActive(rs.getBoolean(Constants.ACTIVE_COLUMN));
        return resource;
    }
}
