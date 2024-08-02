package org.capacity.mappers;

import org.capacity.models.ResourcePlanning;
import org.capacity.utilities.Constants;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.capacity.utilities.Utils.convertToLocalDate;

public class ResourcePlanningMapper implements RowMapper<ResourcePlanning> {
    @Override
    public ResourcePlanning map(ResultSet rs, StatementContext ctx) throws SQLException {
        ResourcePlanning resourcePlanning = new ResourcePlanning();
        resourcePlanning.setId(rs.getInt(Constants.ID_COLUMN));
        resourcePlanning.setResourceId(rs.getInt(Constants.RESOURCE_ID_COLUMN));
        resourcePlanning.setDate(convertToLocalDate(rs.getDate(Constants.FECHA_COLUMN)));
        resourcePlanning.setInitiativeId(rs.getInt(Constants.INITIATIVE_COLUMN));
        resourcePlanning.setCommited(rs.getFloat(Constants.COMMITED_COLUMN));
        resourcePlanning.setPlanned(rs.getFloat(Constants.PLANNED_COLUMN));
        resourcePlanning.setAvailable(rs.getFloat(Constants.AVAILABLE_COLUMN));
        return resourcePlanning;
    }
}
