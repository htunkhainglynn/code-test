package com.test.code.api;

import com.test.code.dto.CommentDto;
import com.test.code.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@Api(value = "Comment Management")
public class CommentApi {

    private final CommentService commentService;

    public CommentApi(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/book/{bookId}")
    @Operation(summary = "Create a new comment", description = "Need a valid email")
    public void createComment(@PathVariable Integer bookId, @RequestBody CommentDto commentDto) {
        commentService.createComment(bookId, commentDto);
    }
}
