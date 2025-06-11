package com.ck4pax.personalBlog.controllers;

import com.ck4pax.personalBlog.models.Post;
import com.ck4pax.personalBlog.services.PostService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Admin {

    @Autowired
    private PostService service;

    @ModelAttribute
    public void getRequest(HttpServletRequest request,
            Model model) {
        model.addAttribute("request", request);
    }

    @GetMapping("/admin")
    public String showDashboard(Model model) {
        model.addAttribute("process", "admin");
        model.addAttribute("posts", service.getAllPost());
        return "admin";
    }

    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable("id") String id
            , Model model) {
        Post post = service.getPost(id);
        if(!id.matches("\\d+") || post == null){
            return "error";
        }
            
        model.addAttribute("post",service.getPost(id));
        return "form";
    }

    @PostMapping("/edit/{id}")
    public String updatePost(@ModelAttribute Post post) {
        service.updatePost(post);
        return "form";
    }

    @GetMapping("/new")
    public String addPost(Model model) {
        model.addAttribute("post",new Post());
        return "form";
    }

    @PostMapping("/new")
    public String savePost(@ModelAttribute Post post
            , Model model) {
        service.savePost(post);
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable String id,Model model) {
        model.addAttribute("process", "delete");
        service.deletePost(id);
        return "admin";
    }
}
