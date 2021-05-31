package com.cxynw.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class PostCommentVO {

    private boolean hasNext;
    private int nextPage;
    private boolean hasPrevious;
    private int previousPage;
    private CommentVO[] comments;

    @Data
    @AllArgsConstructor
    public static class CommentVO{

        private String nickname;
        private String avatar;
        private String content;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date publishDate;

    }

}
