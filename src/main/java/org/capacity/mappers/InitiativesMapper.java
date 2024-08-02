package org.capacity.mappers;

import org.capacity.models.*;
import org.capacity.utilities.Constants;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.capacity.utilities.Utils.convertToLocalDate;

public class InitiativesMapper implements RowMapper<Initiative> {

    @Override
    public Initiative map(ResultSet rs, StatementContext ctx) throws SQLException {
        Initiative initiatives = new Initiative();
        initiatives.setId(rs.getInt(Constants.ID_COLUMN));
        initiatives.setOffice(Office.builder()
                .id(rs.getInt(Constants.OFFICE_ID_COLUMN))
                .officeName(rs.getString(Constants.OFFICE_COLUMN))
                .build());
        initiatives.setCustomer(Customer.builder()
                .id(rs.getInt(Constants.CUSTOMER_ID_COLUMN))
                .customerName(rs.getString(Constants.CUSTOMER_COLUMN))
                .build());
        initiatives.setCostCenter(CostCenter.builder()
                .id(rs.getInt(Constants.COST_CENTER_ID_COLUMN))
                .costCenterName(rs.getString(Constants.COST_CENTER_COLUMN))
                .build());
        initiatives.setRole(Role.builder()
                .id(rs.getInt(Constants.ROLE_ID_COLUMN))
                .roleName(rs.getString(Constants.ROLE_COLUMN))
                .build());
        initiatives.setProject(Project.builder()
                .id(rs.getInt(Constants.PROJECT_ID_COLUMN))
                .projectName(rs.getString(Constants.PROJECT_COLUMN))
                .build());
        initiatives.setBeginDate(convertToLocalDate(rs.getDate(Constants.BEGIN_DATE_COLUMN)));
        initiatives.setEndDate(convertToLocalDate(rs.getDate(Constants.END_DATE_COLUMN)));

        return initiatives;
    }
}
