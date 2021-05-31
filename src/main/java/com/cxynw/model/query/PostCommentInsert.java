package com.cxynw.model.query;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class PostCommentInsert {

    @NotNull(message = "文章不能为空")
    private BigInteger postId;
    @NotBlank(message = "评论内容不能为空")
    private String comment;
    private BigInteger parentCommentId;

    public String getComment(){
        String clean = Jsoup.clean(comment, Whitelist.basic());
        return clean;
    }

}
