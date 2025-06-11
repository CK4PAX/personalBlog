package com.ck4pax.personalBlog.controllers;

import com.ck4pax.personalBlog.models.Post;
import com.ck4pax.personalBlog.services.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author unbroken
 */
@Controller
public class User {
    
    @Autowired
    private PostService service;
    
    @GetMapping("/home")
    public String showDashboard(Model model){
        List<Post> posts = service.getAllPost();
        model.addAttribute("posts", posts);
        return "home";
    }
    
    @GetMapping("/article/{id}")
    public String showPost(@PathVariable("id") String id,
            Model model){
        model.addAttribute("post", service.getPost(id));
        return "article";
    }
}
