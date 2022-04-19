package edu.nus.iss.sg.workshop21.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class NorthWindRepo {
    
    @Autowired
    private JdbcTemplate template;

    public SqlRowSet getAllCustomers(Integer limit, Integer offset) {
        return template.queryForRowSet(Queries.SQL_SELECT_ALL_CUSTOMERS,
                                    limit, offset);
    }

    public SqlRowSet getCustomerById(Integer id) {
        return template.queryForRowSet(Queries.SQL_SELECT_CUSTOMER_BY_ID, id);
    }

    public SqlRowSet getOrdersByCustomerId(Integer id) {
        return template.queryForRowSet(Queries.SQL_SELECT_ORDERS_BY_CUSTOMER_ID, id);
    }
}
