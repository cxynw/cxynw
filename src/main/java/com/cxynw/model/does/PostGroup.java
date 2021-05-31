package com.cxynw.model.does;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_group")
@Entity(name = "PostGroup")
@ToString(callSuper = true)
@Where(clause = "is_deleted = 0")
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE post_group SET is_deleted=1 WHERE post_group_id=?")
public class PostGroup extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_group_id",nullable = false,
            columnDefinition = "bigint unsigned")
    private BigInteger postGroupId;

    @Column(name = "group_name",nullable = false)
    private String groupName;

    @ManyToOne(optional = true)
    private FileMark cover;

}
