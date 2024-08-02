package org.capacity.mappers;

import org.capacity.models.InitiativePlanning;
import org.capacity.utilities.Constants;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.capacity.utilities.Utils.convertToLocalDate;

public class InitiativePlanningMapper implements RowMapper<InitiativePlanning> {
    @Override
    public InitiativePlanning map(ResultSet rs, StatementContext ctx) throws SQLException {
        InitiativePlanning initiativePlanning = new InitiativePlanning();
        initiativePlanning.setId(rs.getInt(Constants.ID_COLUMN));
        initiativePlanning.setInitiativeId(rs.getInt(Constants.INITIATIVE_COLUMN));
        initiativePlanning.setDate(convertToLocalDate(rs.getDate(Constants.FECHA_COLUMN)));
        initiativePlanning.setCommited(rs.getFloat(Constants.COMMITED_COLUMN));
        initiativePlanning.setPlanned(rs.getFloat(Constants.PLANNED_COLUMN));
        initiativePlanning.setAvailable(rs.getFloat(Constants.AVAILABLE_COLUMN));
        initiativePlanning.setUSANeeded(rs.getFloat(Constants.USA_NEEDED_COLUMN));
        initiativePlanning.setOtherNeeded(rs.getFloat(Constants.OTHER_NEEDED_COLUMN));
        return initiativePlanning;
    }
}
