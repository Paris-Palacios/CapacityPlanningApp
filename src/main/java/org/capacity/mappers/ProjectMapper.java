package org.capacity.mappers;

import org.capacity.models.Project;
import org.capacity.utilities.Constants;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMapper implements RowMapper<Project> {
    @Override
    public Project map(ResultSet rs, StatementContext ctx) throws SQLException {
        Project project = new Project();
        project.setId(rs.getInt(Constants.ID_COLUMN));
        project.setProjectName(rs.getString(Constants.PROJECT_COLUMN));
        return project;
    }
}
