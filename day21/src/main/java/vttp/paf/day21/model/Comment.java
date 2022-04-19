package vttp.paf.day21.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {
    private String cId;
    private String user;
    private Integer rating;
    private String cText;
    private Integer gId;

    public String getcId() {
        return cId;
    }
    public void setcId(String cId) {
        this.cId = cId;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getcText() {
        return cText;
    }
    public void setcText(String cText) {
        this.cText = cText;
    }
    public Integer getgId() {
        return gId;
    }
    public void setgId(Integer gId) {
        this.gId = gId;
    }

    public static Comment create(SqlRowSet result) {
        Comment comment = new Comment();
        
        comment.setcId(result.getString("c_id"));
        comment.setUser(result.getString("user"));
        comment.setRating(result.getInt("rating"));
        comment.setcText(result.getString("c_text"));
        comment.setgId(result.getInt("gid"));

        return comment;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("cid", getcId())
            .add("user", getUser())
            .add("rating", getRating())
            .add("ctext", getcText())
            .add("gid", getgId())
            .build();
    }

    @Override
    public String toString() {
        return "\ncid: %s\nuser: %s\nrating: %d\nctext: %s\ngid: %d\n".formatted(getcId(), getUser(), getRating(), getcText(), getgId());
    }
}
