package edu.nus.iss.sg.workshop27.repo;

public class Queries {
    public static final String SQL_INSERT_PO = "insert into po (name, email) values (?, ?);";
    public static final String SQL_INSERT_LINE_ITEM = "insert into lineitem (quantity, order_id, description, unitprice) values (?, ?, ?, ?);";
    public static final String SQL_PO_TOTAL = "select count(*) from po;";
}
