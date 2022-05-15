package com.bmacharia.springauthservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleQuery {
    private Long id;
    private String title;
    private String body;
    private String addedBy;
}
