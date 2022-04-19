package edu.nus.iss.sg.workshop26.repository;

public class Queries {
    public static final String SQL_INSERT_POST="insert into post (photo, comment, poster, mediatype) values (?, ?, ?, ?);";
    public static final String SQL_SELECT_POST_BY_ID="select * from post where post_id = ?;";
}
