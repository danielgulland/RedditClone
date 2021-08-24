package com.example.demo.Services;

import com.example.demo.Models.Comment;
import com.example.demo.Models.Post;
import com.example.demo.Repository.CommentDAO;
import com.example.demo.Repository.PostDAO;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.example.demo.Constants.FieldConstants.DATE_FORMAT;

@Service
public class CommentService {

    @Autowired
    CommentDAO commentDAO;

    @Autowired
    PostDAO postDAO;

    public void createAndSaveComment(Comment comment, Post post) {
        LocalDate currentDate = LocalDate.parse(LocalDate.now().toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        Comment createComment = new Comment(comment.getContent(), formatter.format(currentDate), post);
        commentDAO.save(createComment);
    }

    public void updateAndSaveComment(Comment updatedComment, Comment existingComment) {
        String updatedContent = StringUtils.isNullOrEmpty(updatedComment.getContent()) ? existingComment.getContent() : updatedComment.getContent();
        existingComment.setContent(updatedContent);
        commentDAO.save(existingComment);
    }

    public void createComment(Comment comment, long postId) {
        Optional<Post> existingPost = postDAO.findById(postId);

        if(existingPost.isEmpty()) {
            throw new RuntimeException("Post does not exists to comment on...");
        }

        createAndSaveComment(comment, existingPost.get());
    }

    public void updateComment(Comment comment, long commentId) {
        Optional<Comment> existingComment = commentDAO.findById(commentId);

        if(existingComment.isEmpty()) {
            throw new RuntimeException("Comment does not exist...");
        }

        updateAndSaveComment(comment, existingComment.get());
    }

    public void deleteComment(long commentId) {
        Optional<Comment> existingComment = commentDAO.findById(commentId);

        if(existingComment.isEmpty()) {
            throw new RuntimeException("Comment does not exist...");
        }

        commentDAO.delete(existingComment.get());
    }
}
