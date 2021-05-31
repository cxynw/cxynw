package com.cxynw.model.does;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PostComment")
@Table(name = "post_comment")
@ToString(callSuper = true)
@Where(clause = "is_deleted = 0")
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE post_comment SET is_deleted=1 WHERE post_comment_id=?")
public class PostComment extends BaseEntity{

    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id",nullable = false,updatable = false)
    private User sender;
    @ManyToOne(optional = false)
    @JoinColumn(name="post_id", nullable=false,updatable = false)
    private Post post;
    @Column(name = "comment_content",nullable = false,
            columnDefinition = "mediumtext")
    private String commentContent;


    public PostComment(User sender,Post post,String content){
        this.sender = sender;
        this.post = post;
        this.commentContent = content;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_id",nullable = false,
            columnDefinition = "bigint unsigned comment '贴子评论主键'")
    private BigInteger postCommentId;
    @Column(name = "likes",nullable = false,
            columnDefinition = "bigint comment '点赞'")
    private Long likes;
    @ManyToOne(optional = true)
    @JoinColumn(name = "parent_comment_id",nullable = true,updatable = false)
    private PostComment parentComment;

    @Override
    protected void prePersist() {
        super.prePersist();
        if(likes == null){
            this.likes = 0l;
        }
    }
}
