package edu.nus.iss.sg.workshop26.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.nus.iss.sg.workshop26.models.Post;
import edu.nus.iss.sg.workshop26.repository.PostRepository;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepo;
    
    @PostMapping(path="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView uploadPost(@RequestParam MultipartFile file, 
                                    @RequestPart String comment,
                                    @RequestPart String poster) {

        byte[] buff = new byte[0];

        try{
            buff = file.getBytes();
        }catch(IOException ex){
            ex.printStackTrace();
            return new ModelAndView("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Post post = new Post();
        post.setComment(comment);
        post.setImage(buff);
        post.setMediaType(file.getContentType());
        post.setPoster(poster);

        Integer updateCount = postRepo.uploadPost(post);

        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.CREATED);
        mav.setViewName("upload_success");
        mav.addObject("updateCount", updateCount);

        return mav;
    }

    @GetMapping(path="/post/{id}")
    public ModelAndView getPostById(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        Optional<Post> postOpt = postRepo.getPostById(id);

        if(postOpt.isEmpty()) {
            return new ModelAndView("error", HttpStatus.NOT_FOUND);
        }

        Post post = postOpt.get();

        mav.setViewName("show_post");
        mav.addObject("postObj", post);
        mav.setStatus(HttpStatus.OK);

        return mav;
    }
}
