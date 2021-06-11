package com.cxynw.model.vo;

import com.cxynw.model.does.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.LazyInitializationException;

import java.util.Date;
import java.util.stream.Stream;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PostVO {

    private String[] groupName;
    private String likes;
    private String visits;
    private String postId;
    private String title;
    private String content;
    private String nickname;
    private String avatar;
    private Boolean hasPassword;
    private Boolean canEdit;
    private Boolean disallowComment;
    private Boolean isMe;

    // 评论数
    private Long numberOfComments;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date editTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    public PostVO(Post post,boolean isMe,Long numberOfComments) throws LazyInitializationException {
        this.isMe = isMe;
        this.numberOfComments = numberOfComments;

        Stream<String> stringStream = post.getPostGroups().stream().map((item -> item.getGroupName()));
        this.groupName = stringStream.toArray(String[]::new);
        this.likes = post.getLikes().toString();

        if(post.getVisits() < 1000){
            this.visits = post.getVisits().toString();
        }else{
            this.visits = post.getVisits()/1000+"k";
        }

        this.postId = post.getPostId().toString();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getPublisher().getNickname();
        if(post.getPublisher().getAvatarId() != null){
            this.avatar = post.getPublisher().getAvatarId().toString();
        }
        this.hasPassword = post.getPostPassword() != null;
        this.canEdit = post.getCanEdit();
        this.disallowComment = post.getDisallowComment();
        this.editTime = post.getEditTime();
        this.createTime = post.getCreateTime();
    }
}
