package edu.nus.iss.sg.workshop26.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Post {
    private Integer postId;
    private String poster;
    private String comment;
    private String mediaType;
    private byte[] image;
    
    public Integer getPostId() {
        return postId;
    }
    public void setPostId(Integer postId) {
        this.postId = postId;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getMediaType() {
        return mediaType;
    }
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    public static Post create(ResultSet rs) throws SQLException {
        final Post post = new Post();
        post.setPostId(rs.getInt("post_id"));
        post.setComment(rs.getString("comment"));
        post.setMediaType(rs.getString("mediatype"));
        post.setPoster(rs.getString("poster"));
        post.setImage(rs.getBytes("photo"));
        return post;
    }
}
