package com.cxynw.model.does;

import com.cxynw.model.enums.PermissionTypeEnum;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission")
@Entity(name = "Permission")
@ToString(callSuper = true)
@Where(clause = "is_deleted = 0")
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE permission SET is_deleted=1 WHERE permission_id=?")
public class Permission extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id",nullable = false,
            columnDefinition = "bigint unsigned comment '权限主键'")
    private BigInteger permissionId;


    @Column(name = "permission_name",length = 32,nullable = false,
            columnDefinition = "nvarchar(32) comment '权限名字'")
    private String permissionName;
    @Column(name = "description",length = 64,nullable = false,
            columnDefinition = "nvarchar(64) comment '权限描述'")
    private String description;
    @Column(name = "can_edit",nullable = false,
            columnDefinition = "bit(1) comment '是否能够编辑'")
    private Boolean canEdit;
    /**
     * @see PermissionTypeEnum
     */
    @Column(name = "type",nullable = false,
            columnDefinition = "int comment '权限的类型'")
    private Integer type;
    @Column(name = "can_view",nullable = false,
            columnDefinition = "bit(1) comment '是否能够被显示'")
    private Boolean canView;
    @Column(name = "can_delete",nullable = false,
            columnDefinition = "bit(1) comment '是否能被删除'")
    private Boolean canDelete;

    @Override
    protected void prePersist() {
        super.prePersist();
        if(canEdit == null){
            canEdit = true;
        }
        if(canView == null){
            canView = true;
        }
        if(canDelete == null){
            canDelete = true;
        }
    }
}
