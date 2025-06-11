package com.ck4pax.personalBlog.utilities;

import com.ck4pax.personalBlog.models.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

/**
 *
 * @author unbroken
 */
public class UtilitiesTest {

    private List<Post> posts;
    private File[] files;

    public UtilitiesTest() {
        this.posts = initPost();
        this.files = initFiles(this.posts);
    }

    public List<Post> initPost() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("3", LocalDate.now(),
                "how to write well code", "Follow SOLID principle"));
        posts.add(new Post("1", LocalDate.now(),
                "how to jump higher", "Do pliometric exercises"));
        posts.add(new Post("2", LocalDate.now(),
                "how to learn spring", "Do projects"));
        return posts;
    }

    public File[] initFiles(List<Post> posts) {
        File[] files = new File[posts.size()];

        for (int i = 0; i < posts.size(); i++) {
            files[i] = new File(
                    String.format("post_%s.json",
                             posts.get(i).getId()));
        }
        return files;
    }

    @Test
    public void testSaveFile() throws IOException {
        Post post = new Post();
        post.setTitle("how to learn spring");
        post.setContent("The first step is "
                + "to build a project mvc Spring...");

        Path path = Utilities.savePost(post);
        DirectoryStream<Path> stream = Files
                .newDirectoryStream(Utilities.path);
        boolean exist = false;

        for (Path p : stream) {
            if (p.compareTo(path) == 0) {
                exist = true;
                Files.delete(path);
                break;
            }
        }
        assertTrue(exist);
    }

    @Test
    public void testGetAllPostsMethod() throws IOException {

        File directory = Mockito.mock(File.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(directory.listFiles()).thenReturn(this.files);

        for (int i = 0; i < this.posts.size(); i++) {
            Mockito.when(mapper.readValue(this.files[i], Post.class))
                    .thenReturn(this.posts.get(i));
        }

        List<Post> test = Utilities.getAllPosts(directory, mapper);
        assertTrue(this.posts.equals(test));
    }

    @Test
    public void testSortPostsMethod() {
        Utilities.sortPosts(this.posts);
        assertTrue(Integer.valueOf(this.posts.getFirst().getId())
                <= Integer.valueOf(this.posts.getLast().getId()));
    }

    @Test
    public void testGetNewIdMethod() throws IOException {
        List<Post> test = Utilities.sortPosts(new ArrayList<Post>(this.posts));
        String newId = String.valueOf(
                Integer.valueOf(test.getLast().getId())
                + Utilities.increment);
        System.out.println(newId);
        assertEquals(newId,Utilities.getNewId(
                new ArrayList<>(this.posts)));
    }

    @Test
    public void testGetPostMethod() throws IOException {
        assertNotNull(Utilities.getPost(this.posts,
                this.posts.getFirst().getId()));
    }

    @Test
    public void testDeleteMethod() throws IOException {
        File dir = Mockito.mock(File.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(dir.listFiles()).thenReturn(this.files);
        for (int i = 0; i < this.files.length; i++) {
            Mockito.when(mapper.readValue(this.files[i], Post.class))
                    .thenReturn(this.posts.get(i));
        }

        assertEquals(0, Utilities.deletePost(dir, mapper,
                this.posts.getFirst().getId()));
    }

}
