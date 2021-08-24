package com.example.demo.Services;

import com.example.demo.Models.Post;
import com.example.demo.Repository.PostDAO;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.example.demo.Constants.FieldConstants.DATE_FORMAT;

@Service
public class PostService {

    @Autowired
    PostDAO postDAO;

    public void createPost(Post post) {
        LocalDate currentDate = LocalDate.parse(LocalDate.now().toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        post.setDateCreated(formatter.format(currentDate));
        postDAO.save(post);
    }

    public void updateAndSavePost(Post updatedPost, Post existingPost) {
        String updatedTitle = StringUtils.isNullOrEmpty(updatedPost.getTitle()) ? existingPost.getTitle() : updatedPost.getTitle();
        String updatedInformation = StringUtils.isNullOrEmpty(updatedPost.getInformation()) ? existingPost.getInformation() : updatedPost.getInformation();

        existingPost.setTitle(updatedTitle);
        existingPost.setInformation(updatedInformation);

        postDAO.save(existingPost);
    }

    public void updatePost(long postId, Post post) {
        Optional<Post> existingPost = postDAO.findById(postId);

        if(existingPost.isEmpty()) {
            throw new RuntimeException("Post does not exist...");
        }

        updateAndSavePost(post, existingPost.get());

        //make this a seperate function
//        String updatedTitle = StringUtils.isNullOrEmpty(post.getTitle()) ? existingPost.get().getTitle() : post.getTitle();
//        String updatedInformation = StringUtils.isNullOrEmpty(post.getInformation()) ? existingPost.get().getInformation() : post.getInformation();
//
//        existingPost.get().setTitle(updatedTitle);
//        existingPost.get().setInformation(updatedInformation);
//
//        postDAO.save(existingPost.get());
    }

    public void deletePost(long postId) {
        Optional<Post> existingPost = postDAO.findById(postId);

        if(existingPost.isEmpty()) {
            throw new RuntimeException("Post does not exist...");
        }

        postDAO.delete(existingPost.get());
    }
}
