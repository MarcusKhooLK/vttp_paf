package vttp.paf.day21.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.paf.day21.model.Game;
import vttp.paf.day21.services.GameService;

@Controller
@RequestMapping(path="game")
public class GameController {

    @Autowired
    private GameService gameSvc;
    
    @GetMapping("{gid}")
    public String getGameById(@PathVariable Integer gid, Model model) {
        
        Optional<Game> opt = gameSvc.getComments(gid);

        if(opt.isEmpty()) {
            model.addAttribute("errorMsg", "Game not found!");
            return "error";
        }

        Game g = opt.get();
        model.addAttribute("game", g);
        return "game";
    }
}
