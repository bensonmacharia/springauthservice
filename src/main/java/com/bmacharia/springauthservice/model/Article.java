package com.bmacharia.springauthservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    @Size(max = 20)
    private String title;
    @NotBlank
    @Size(max = 200)
    private String body;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User addedBy;

    public Article(String title, String body, User addedBy) {
        this.title = title;
        this.body = body;
        this.addedBy = addedBy;
    }
}
