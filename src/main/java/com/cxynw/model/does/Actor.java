package com.cxynw.model.does;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Actor")
@Table(name = "actor")
@ToString(callSuper = true)
@Where(clause = "is_deleted = 0")
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE actor_id SET is_deleted=1 WHERE actor_id=?")
public class Actor extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id",nullable = false,
            columnDefinition = "bigint unsigned comment '角色主键'")
    private BigInteger actorId;

    @Column(name = "role_name",length = 32,nullable = false,
            columnDefinition = "nvarchar(32) comment '角色名'")
    private String roleName;
    @Column(name = "role_description",length = 64,nullable = false
            ,columnDefinition = "nvarchar(64) comment '角色的描述'")
    private String roleDescription;


    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Permission> permissions;

}
