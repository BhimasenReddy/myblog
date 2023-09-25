package com.myblog.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private Long id;
    @NotEmpty
    @Size(min = 3, message = "Post title should be at least 3 character")
    private String title;
    @NotEmpty
    @Size(min = 5, message = "Post description should be at least 5 character")
    private String description;
    @NotEmpty
    @Size(min = 6, message = "Post content should be at least 6 character")
    private String content;

}
