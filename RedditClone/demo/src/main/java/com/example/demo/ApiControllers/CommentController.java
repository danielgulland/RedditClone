package com.example.demo.ApiControllers;

import com.example.demo.Models.Comment;
import com.example.demo.Repository.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentDAO commentDAO;

    @PostMapping("/createComment")
    public ResponseEntity createComment(@RequestBody Comment comment) {
        commentDAO.save(comment);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
