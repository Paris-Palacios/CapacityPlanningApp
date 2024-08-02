package org.capacity.mappers;

import org.capacity.models.Role;
import org.capacity.utilities.Constants;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {
    @Override
    public Role map(ResultSet rs, StatementContext ctx) throws SQLException {
        Role role = new Role();
        role.setId(rs.getInt(Constants.ID_COLUMN));
        role.setRoleName(rs.getString(Constants.ROLE_COLUMN));
        return role;
    }
}
