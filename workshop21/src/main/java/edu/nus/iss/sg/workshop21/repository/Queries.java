package edu.nus.iss.sg.workshop21.repository;

public class Queries {
    public static final String SQL_SELECT_ALL_CUSTOMERS = "select * from customers limit ? offset ?;";
    public static final String SQL_SELECT_CUSTOMER_BY_ID = "select * from customers where id = ?;";
    public static final String SQL_SELECT_ORDERS_BY_CUSTOMER_ID = "select * from orders where customer_id = ?";
}
