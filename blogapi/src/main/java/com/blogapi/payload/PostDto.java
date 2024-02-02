package com.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;

    @NotEmpty
    @Size(min=2, message = "Title should be at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min=4, message="Description should be at least 4 characters ")
    private String description;
    @NotEmpty(message = "content is empty")
    private String content;
}
