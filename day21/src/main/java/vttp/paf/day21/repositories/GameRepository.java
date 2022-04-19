package vttp.paf.day21.repositories;

import static vttp.paf.day21.repositories.Queries.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.paf.day21.model.Comment;
import vttp.paf.day21.model.Game;

@Repository
public class GameRepository {
    
    @Autowired
    private JdbcTemplate template;

    public List<Comment> getCommentsByGid(Integer gid) {
        return getCommentsByGid(gid, Integer.MAX_VALUE, 0);
    }

    public List<Comment> getCommentsByGid(Integer gid, Integer limit) {
        return getCommentsByGid(gid, limit, 0);
    }

    public List<Comment> getCommentsByGid(Integer gid, Integer limit, Integer offset) {

        final SqlRowSet result = template.queryForRowSet(
            SQL_SELECT_COMMENT_BY_GID, gid, limit, offset
        );

        final List<Comment> comments = new ArrayList<>();
        while(result.next()) {
            comments.add(Comment.create(result));
        }

        return comments;
    }

    public Optional<Game> getGameByGid(Integer queryId) {

        final SqlRowSet result = template.queryForRowSet(
            // select * fro game where gid = <gid>
            SQL_SELECT_GAME_BY_GID, queryId
        );

        if(!result.next()) {
            return Optional.empty();
        }

        return Optional.of(Game.create(result));

    }
}
