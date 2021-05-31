package com.cxynw.model.does;

import com.cxynw.model.enums.StateEnum;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",indexes = {
        @Index(name = "avatarId", columnList = "avatar_file_mark_id")
})
@Entity(name = "User")
@ToString(callSuper = true)
@Where(clause = "is_deleted = 0")
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE users SET is_deleted=1 WHERE user_id=?")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false,
            columnDefinition = "bigint unsigned comment '账户主键'")
    private BigInteger userId;


    @Column(name = "username",length = 64,nullable = false,unique = true,
            columnDefinition = "varchar(64) comment '用户名'")
    private String username;
    @Column(name = "email",length = 128,nullable = false,
            columnDefinition = "nvarchar(128) comment '账户的邮箱'")
    private String email;
    @Column(name = "nickname",nullable = false,length = 64,
            columnDefinition = "nvarchar(64) comment '昵称'")
    private String nickname;
    @Column(name = "password",length = 60,nullable = false,
            columnDefinition = "char(60) comment '密码'")
    private String password;
    @Column(name = "status",nullable = false,
            columnDefinition = "int comment '账号状态'")
    private Integer status;


    @Column(name = "description",nullable = true,
            columnDefinition = "nvarchar(1024) comment '个人介绍'")
    private String description;
    @Column(name = "age",nullable = true,
            columnDefinition = "tinyint unsigned comment '年龄'")
    private Short age;
    @Column(name = "gender",nullable = true,
            columnDefinition = "tinyint unsigned comment '性别'")
    private Short gender;

    @Column(name = "avatar_file_mark_id",nullable = true)
    private BigInteger avatarId;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Actor> actors;

    @Override
    protected void prePersist() {
        super.prePersist();
        if(status == null){
            this.status = StateEnum.NORMAL.getValue();
        }
    }
}
