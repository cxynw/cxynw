package com.cxynw.model.does;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@Table(name = "post_attachment")
@Entity(name = "PostAttachment")
@ToString(callSuper = true)
@Where(clause = "is_deleted = 0")
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE post_attachment SET is_deleted=1 WHERE post_attachment_id=?")
public class PostAttachment extends BaseEntity{

    public PostAttachment(BigInteger fileMarkId, BigInteger postId) {
        this.fileMarkId = fileMarkId;
        this.postId = postId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_attachment_id",nullable = false,
            columnDefinition = "bigint unsigned comment '贴子的附件'")
    private BigInteger postAttachmentId;

    /**
     * @see FileMark
     */
    @Column(name = "file_mark_id",columnDefinition = "bigint unsigned",nullable = false)
    private BigInteger fileMarkId;

    /**
     * @see Post
     */
    @Column(name = "post_id",columnDefinition = "bigint unsigned",nullable = false)
    private BigInteger postId;

}
