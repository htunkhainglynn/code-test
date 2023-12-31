package com.test.code.vo;

import com.test.code.entity.Comment;
import lombok.Data;

@Data
public class CommentVo {
    private String content;
    private String email;

    public CommentVo(Comment comment) {
        this.email = comment.getEmail();
        this.content = comment.getContent();
    }
}
