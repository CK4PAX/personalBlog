package com.ck4pax.personalBlog.models;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    
    private String id;
    private LocalDate date;
    private String title;
    private String content;
    @Override
    public String toString(){
        
        return String.format("ID: %s\n"
                + "title: %s\n"
                + "date: %s\n"
                + "content: %s", id,title,date.toString()
                ,content.substring(0, 50));
    }
}
