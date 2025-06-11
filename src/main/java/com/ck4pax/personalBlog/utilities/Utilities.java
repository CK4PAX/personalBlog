package com.ck4pax.personalBlog.utilities;

import com.ck4pax.personalBlog.models.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Utilities {

    public final static Path path = Path.of("src/main/resources/data/");
    public final static ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    public final static File directory = new File(path.toString());
    private final static String firstId = "1";
    public final static int increment = 1;

    public static void createDir() throws IOException {
        try {
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException e) {
        }
    }

    public static Path savePost(Post post) throws IOException {
        String filename = String.format(
                "%s/post_%s.json", path.toString(), post.getId());
        mapper.writeValue(new File(filename), post);
        return Path.of(filename);
    }

    public static List<Post> getAllPosts(File dir, ObjectMapper mapper)
            throws IOException {
        List<Post> posts = new ArrayList<>();

        for (File file : dir.listFiles()) {
            posts.add(mapper.readValue(file, Post.class));
        }
        return posts;
    }

    public static List<Post> sortPosts(List<Post> posts) {
        posts.sort((a, b) -> Integer.valueOf(a.getId())
                - Integer.valueOf(b.getId()));
        return posts;
    }

    public static String getNewId(List<Post> posts)
            throws IOException {
        int newID = Integer.valueOf(sortPosts(posts)
                .getLast().getId()) + increment;
        return String.valueOf(newID);
    }

    public static Post setId(Post post) throws IOException {
        List<Post> posts = getAllPosts(directory, mapper);
        if(!posts.isEmpty()){
            post.setId(getNewId(posts));
        }else{
            post.setId(firstId);
        }
        return post;
    }

    public static Post setDate(Post post){
        post.setDate(LocalDate.now());
        return post;
    }
    
    public static Post getPost(List<Post> posts, String id){
        for (Post post : posts) {
                if(post.getId().equals(id))
                    return post;
        }
        return null;
    }
    
    public static int deletePost(File dir,
            ObjectMapper mapper, String id) throws IOException{
        
        for (File file : dir.listFiles()) {
            Post p = mapper.readValue(file, Post.class);
            if(p.getId().equals(id)){
                file.delete();
                return 0;
            }
        }
        return -1;
    }
}
