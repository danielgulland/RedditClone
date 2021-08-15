package com.example.demo.Services;

import com.example.demo.Models.Post;
import com.example.demo.Repository.PostDAO;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostDAO postDAO;

    public void createPost(Post post) {
        post.setDateCreated(new Date());
        postDAO.save(post);
    }

    public void updatePost(long postId, Post post) {
        Optional<Post> existingPost = postDAO.findByPostId(postId);

        if(existingPost.isEmpty()) {
            throw new RuntimeException("Post does not exist...");
        }

        String updatedTitle = StringUtils.isNullOrEmpty(post.getTitle()) ? existingPost.get().getTitle() : post.getTitle();
        String updatedInformation = StringUtils.isNullOrEmpty(post.getInformation()) ? existingPost.get().getInformation() : post.getInformation();

        existingPost.get().setTitle(updatedTitle);
        existingPost.get().setInformation(updatedInformation);

        postDAO.save(existingPost.get());
    }

    public void deletePost(long postId) {
        Optional<Post> existingPost = postDAO.findByPostId(postId);

        if(existingPost.isEmpty()) {
            throw new RuntimeException("Post does not exist...");
        }

        postDAO.delete(existingPost.get());
    }
}
