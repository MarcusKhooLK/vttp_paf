package vttp.paf.day21.model;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Game {
    private Integer gId;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer usersRated;
    private String url;
    private String image;
    private List<Comment> comments;
    
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public Integer getgId() {
        return gId;
    }
    public void setgId(Integer gId) {
        this.gId = gId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getRanking() {
        return ranking;
    }
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    public Integer getUsersRated() {
        return usersRated;
    }
    public void setUsersRated(Integer usersRated) {
        this.usersRated = usersRated;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


    public static Game create(SqlRowSet result) {
        Game game = new Game();
        
        game.setgId(result.getInt("gid"));
        game.setName(result.getString("name"));
        game.setYear(result.getInt("year"));
        game.setRanking(result.getInt("ranking"));
        game.setUsersRated(result.getInt("users_rated"));
        game.setUrl(result.getString("url"));
        game.setImage(result.getString("image"));

        return game;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("gid", getgId())
            .add("name", getName())
            .add("year", getYear())
            .add("ranking", getRanking())
            .add("usersRated", getUsersRated())
            .add("url", getUrl())
            .add("image", getImage())
            .build();
    }
}
