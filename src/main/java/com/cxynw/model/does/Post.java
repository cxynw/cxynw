package com.cxynw.model.does;

import com.cxynw.model.enums.StateEnum;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

/**
 * 网站的内容实体
 *
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
@Entity(name = "Post")
@ToString(callSuper = true,exclude = "postGroups")
@Where(clause = "is_deleted = 0")
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE post SET is_deleted=1 WHERE post_id=?")
public class Post extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private User publisher;

    @ManyToMany
    private Set<PostGroup> postGroups;

    public Post(String title, String content, Integer postType){
        this.title = title;
        this.content = content;
        this.postType = postType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id",nullable = false,
            columnDefinition = "bigint unsigned comment '贴子主键'")
    private BigInteger postId;

    @Column(name = "title",length = 512,nullable = false,
            columnDefinition = "nvarchar(512) comment '贴子标题'")
    private String title;
    @Column(name = "content",nullable = false,
            columnDefinition = "mediumtext comment '贴子内容'")
    private String content;
    @Column(name = "post_type",nullable = false,
            columnDefinition = "int comment '贴子类型'")
    /**
     * @see com.cxynw.model.enums.PostTypeEnum
     */
    private Integer postType;
    @Column(name = "post_status",nullable = false,
            columnDefinition = "int comment '贴子的状态'")
    private Integer postStatus;
    @Column(name = "can_search",nullable = false,
            columnDefinition = "bit(1) comment '是否能够被搜索'")
    private Boolean canSearch;
    @Column(name = "can_comment",nullable = false,
            columnDefinition = "bit(1) comment '是否能被评论'")
    private Boolean canComment;
    @Column(name = "visits",nullable = false,columnDefinition = "bigint comment '访问次数'")
    private Long visits;
    @Column(name = "disallow_comment",nullable = false,
            columnDefinition = "bit(1) comment '是否禁止评论'")
    private Boolean disallowComment;
    @Column(name = "likes",nullable = false,
            columnDefinition = "bigint comment '点赞'")
    private Long likes;
    @Column(name = "word_count",nullable = false,
            columnDefinition = "bigint comment '文章字符统计'")
    private Long wordCount;

//    @ManyToOne
//    private FileMark attachment;// 附件
//    @Column(name = "thumbnail", nullable = true,
//            columnDefinition = "bigint unsigned comment '缩略图'")
    // 这个因为不符合第二范式，已被移除

    private BigInteger thumbnail;
    @Column(name = "post_password",nullable = true,length = 60,
            columnDefinition = "char(60) comment '贴子密码'")
    private String postPassword;
    @Column(name = "edit_time",nullable = true,
            columnDefinition = "datetime comment '编辑时间'")
    private Date editTime;


    @Override
    protected void prePersist() {
        super.prePersist();

        if(postStatus == null){
            this.postStatus = StateEnum.NORMAL.getValue();
        }
        if(canSearch == null){
            this.canSearch = true;
        }
        if(canComment == null){
            this.canComment = true;
        }
        if(visits == null || visits < 0l){
            this.visits = 0l;
        }
        if(disallowComment == null){
            this.disallowComment = false;
        }
        if(likes == null || likes < 0l){
            this.likes = 0l;
        }
        if(wordCount == null || wordCount < 0l){
            this.wordCount = 0l;
        }
    }

}
