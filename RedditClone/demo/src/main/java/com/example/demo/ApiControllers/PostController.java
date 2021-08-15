package com.example.demo.ApiControllers;

import com.example.demo.Models.Post;
import com.example.demo.Services.PostService;
import com.example.demo.Validation.ValidationError;
import com.example.demo.Validation.Validator;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.Constants.FieldConstants.*;


@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private Validator validator;

    @Autowired
    private PostService postService;

    @PostMapping("/createPost")
    public ResponseEntity createPost(@RequestBody Post post) {

        if(validator.chain(StringUtils.isNullOrEmpty(post.getTitle()),ValidationError.MISSING_VALUE, TITLE)
                .chain(post.getPoints() <= 0, ValidationError.BAD_REQUEST, POINTS)
                .checkForNoErrors(StringUtils.isNullOrEmpty(post.getInformation()), ValidationError.MISSING_VALUE, INFORMATION)) {
            postService.createPost(post);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return validator.getResponseEntity();
    }

    @PutMapping("/updatePost/{postId}")
    public ResponseEntity updatePost(@PathVariable long postId, @RequestBody Post post) {

        if(validator.checkForNoErrors(postId <= 0, ValidationError.BAD_REQUEST, POST_ID)) {
            postService.updatePost(postId, post);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return validator.getResponseEntity();
    }

    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity deletePost(@PathVariable long postId) {

        if(validator.checkForNoErrors(postId <= 0, ValidationError.BAD_REQUEST, POST_ID)) {
            postService.deletePost(postId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return validator.getResponseEntity();
    }
}
