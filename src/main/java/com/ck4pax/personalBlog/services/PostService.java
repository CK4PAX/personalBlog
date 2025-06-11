package com.ck4pax.personalBlog.services;

import com.ck4pax.personalBlog.models.Post;
import com.ck4pax.personalBlog.utilities.Utilities;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author unbroken
 */
@Service
public class PostService {

    public void savePost(Post post) {
        try {
            Utilities.setId(post);
            Utilities.setDate(post);
            Utilities.savePost(post);
        } catch (IOException e) {
            System.out.println("Unable to save post");
        }
    }

    public void updatePost(Post post) {
        try {
            Utilities.savePost(post);
        } catch (IOException e) {
            System.out.println("Unable to update post");
        }
    }

    public List<Post> getAllPost() {
        List<Post> posts = null;
        try {
            posts = Utilities.getAllPosts(Utilities.directory,
                    Utilities.mapper);
        } catch (IOException e) {
            System.out.println("Unable to show all posts");
        }
        return posts;
    }

    public Post getPost(String id) {
        return Utilities.getPost(getAllPost(), id);
    }

    public void deletePost(String id) {
        try {
            Utilities.deletePost(Utilities.directory,
                     Utilities.mapper, id);
        } catch (Exception e) {
            System.out.println("Unable to delete post");
        }
    }
}
