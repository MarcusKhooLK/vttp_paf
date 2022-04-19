package vttp.paf.day21;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp.paf.day21.model.Comment;
import vttp.paf.day21.model.Game;
import vttp.paf.day21.repositories.GameRepository;

@SpringBootTest
class Day21ApplicationTests {

    @Autowired
    private GameRepository gameRepo;

    @Test
    void shouldReturnAGame() {
        Optional<Game> opt = gameRepo.getGameByGid(10);
        assertTrue(opt.isPresent(), "gid = 10");
    }

    @Test
    void shouldReturnEmpty() {
        Optional<Game> opt = gameRepo.getGameByGid(10000);
        assertFalse(opt.isPresent(), "gid = 10000");
    }

    @Test
    void shouldReturnSameNumberOfComments() {
        List<Comment> comments = gameRepo.getCommentsByGid(10);
        assertTrue(comments.size() == 42);
    }

    @Test
    void shouldReturnEmptyComment() {
        List<Comment> comments = gameRepo.getCommentsByGid(10000, 10, 0);
        assertFalse(!comments.isEmpty());
    }

}
