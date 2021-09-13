package com.cxynw.model.vo;

import com.cxynw.model.does.Post;
import com.cxynw.model.enums.PostTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Data
@Slf4j
public class PostItemVo {

    private BigInteger id;
    private String title;
    private String type;
    private Boolean canEdit;
    private Boolean canDelete;

    public PostItemVo(Post post){
        this.id = post.getPostId();
        this.title = post.getTitle();
        this.type = typeConvertString(post.getPostType());
        this.canEdit = post.getCanEdit();
        this.canDelete = Boolean.TRUE;
    }

    private String typeConvertString(Integer integer){
        String result = "其他";
        PostTypeEnum[] values = PostTypeEnum.values();
        for(PostTypeEnum postTypeEnum : values){
            if(postTypeEnum.getValue().equals(integer)){
                result = postTypeEnum.getName();
                break;
            }
        }
        log.trace("post type convert [{}] to string: [{}]",integer,result);
        return result;
    }

}
