package org.capacity.repository;

import org.capacity.mappers.*;
import org.capacity.models.*;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface RepositoryDAO {
    @SqlQuery("select id, [office id] as office_id, Offices.Office, [cost center id] as cost_center_id, [Cost Centers].[Cost Center] ,[role id] as role_id, Roles.Role, " +
            "[customer id] as customer_id, Customers.Customer ,[project id] as project_id, Projects.Project ,[begin date], [end date] from [initiatives] " +
            "INNER JOIN Offices ON [office id] = Offices.Id INNER JOIN [Cost Centers] ON [cost center id] = [Cost Centers].Id INNER JOIN Roles ON [role id] = Roles.Id " +
            "INNER JOIN Customers ON [customer id] = Customers.Id INNER JOIN Projects ON [project id] = Projects.Id")
    @RegisterRowMapper(InitiativesMapper.class)
    List<Initiative> getAllInitiatives();

    @SqlQuery("select * from customers")
    @RegisterRowMapper(CustomerMapper.class)
    List<Customer> getAllCustomers();

    @SqlQuery("select * from offices")
    @RegisterRowMapper(OfficeMapper.class)
    List<Office> getAllOffices();

    @SqlQuery("select * from [cost centers]")
    @RegisterRowMapper(CostCenterMapper.class)
    List<CostCenter> getAllCostCenters();

    @SqlQuery("select * from [projects]")
    @RegisterRowMapper(ProjectMapper.class)
    List<Project> getAllProjects();

    @SqlQuery("SELECT Resources.Id, Resources.Resource, Resources.Office as office_id, Offices.Office, Resources.[Cost Center] as cost_center_id, " +
            "[Cost Centers].[Cost Center], Resources.Active, Resources.Position as position_id, Positions.Position " +
            "FROM Resources INNER JOIN Offices ON Resources.Office = Offices.Id " +
            "INNER JOIN [Cost Centers] ON Resources.[Cost Center] = [Cost Centers].Id " +
            "INNER JOIN Positions ON Resources.Position = Positions.Id")
    @RegisterRowMapper(ResourcesMapper.class)
    List<Resource> getAllResources();

    @SqlQuery("select * from [roles]")
    @RegisterRowMapper(RoleMapper.class)
    List<Role> getAllRoles();

    @SqlQuery("select * from [positions]")
    @RegisterRowMapper(PositionMapper.class)
    List<Position> getAllPositions();

    @SqlQuery("select * from [resource planning] WHERE [resourceid] = :resourceId")
    @RegisterRowMapper(ResourcePlanningMapper.class)
    List<ResourcePlanning> getResourcePlanningById(@Bind("resourceId") int resourceId);

    @SqlQuery("select * from [initiative planning] WHERE [initiative] = :initiative")
    @RegisterRowMapper(InitiativePlanningMapper.class)
    List<InitiativePlanning> getInitiativePlanningById(@Bind("initiative") int initiativeId);

    @SqlUpdate("UPDATE [Resource Planning] SET [Resource Planning].Commited = :commited, [Resource Planning].Planned = :planned WHERE [Resource Planning].Id = :id;")
    void updateResourcePlanningById(@Bind("id") int ResourcePlanningId ,@Bind("commited") float ResourcePlanningCommited, @Bind("planned") float ResourcePlanningPlanned);

    @SqlUpdate("INSERT INTO [Resource Planning] (ResourceId, Fecha, Commited, Planned, Initiative) VALUES (:resourceId, :date, :commited, :planned, :initiative)")
    void insertResourcePlanning(@Bind("resourceId") int ResourceId , @Bind("date") LocalDate date, @Bind("commited") float ResourcePlanningCommited ,
                                @Bind("planned") float ResourcePlanningPlanned, @Bind("initiative") float initiative);

}
