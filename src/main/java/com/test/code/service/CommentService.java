package com.test.code.service;

import com.test.code.dto.CommentDto;

public interface CommentService {

    void createComment(Integer bookId, CommentDto comment);
}
