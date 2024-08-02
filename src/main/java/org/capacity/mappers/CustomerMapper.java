package org.capacity.mappers;

import org.capacity.models.Customer;
import org.capacity.utilities.Constants;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {
    @Override
    public Customer map(ResultSet rs, StatementContext ctx) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt(Constants.ID_COLUMN));
        customer.setCustomerName(rs.getString(Constants.CUSTOMER_COLUMN));
        return customer;
    }
}
