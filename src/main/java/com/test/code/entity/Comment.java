package com.test.code.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.code.dto.CommentDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    @JsonIgnore
    @ManyToOne
    private Book book;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    public Comment(CommentDto commentDto) {
        this.content = commentDto.getComment();
        this.email = commentDto.getEmail();
    }
}
