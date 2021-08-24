package com.example.demo.ApiControllers;

import com.example.demo.Models.Comment;
import com.example.demo.Services.CommentService;
import com.example.demo.Validation.ValidationError;
import com.example.demo.Validation.Validator;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.Constants.FieldConstants.COMMENT;
import static com.example.demo.Constants.FieldConstants.CONTENT;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    Validator validator;

    @PostMapping("/createComment/{postId}")
    public ResponseEntity createComment(@RequestBody Comment comment, @PathVariable long postId) {

        if(validator.chain(postId <= 0, ValidationError.BAD_REQUEST, COMMENT)
                .checkForNoErrors(StringUtils.isNullOrEmpty(comment.getContent()), ValidationError.MISSING_VALUE, CONTENT)) {
            commentService.createComment(comment, postId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return validator.getResponseEntity();
    }

    @PutMapping("updateComment/{commentId}")
    public ResponseEntity updateComment(@RequestBody Comment comment, @PathVariable long commentId) {

        if(validator.chain(commentId <= 0, ValidationError.BAD_REQUEST, COMMENT)
                .checkForNoErrors(StringUtils.isNullOrEmpty(comment.getContent()), ValidationError.MISSING_VALUE, CONTENT)) {
            commentService.updateComment(comment, commentId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return validator.getResponseEntity();
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable long commentId) {

        if(validator.checkForNoErrors(commentId <= 0, ValidationError.BAD_REQUEST, COMMENT)) {
            commentService.deleteComment(commentId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return validator.getResponseEntity();
    }
}
