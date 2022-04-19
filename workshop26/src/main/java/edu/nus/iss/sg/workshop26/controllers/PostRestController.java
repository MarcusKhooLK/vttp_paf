package edu.nus.iss.sg.workshop26.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.nus.iss.sg.workshop26.models.Post;
import edu.nus.iss.sg.workshop26.repository.PostRepository;

@RestController
public class PostRestController {
    
    @Autowired
    private PostRepository postRepo;

    @GetMapping(path="/post/{postId}/image")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Integer postId) {
        Optional<Post> postOpt = postRepo.getPostById(postId);

        if(postOpt.isEmpty())
            return ResponseEntity.notFound().build();

        Post post = postOpt.get();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", post.getMediaType());
        headers.add("Cache-Control", "max-age=604800");
        
        return ResponseEntity.ok()
                            .headers(headers)
                            .body(post.getImage());
    }
}
