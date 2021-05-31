package com.cxynw.model.does;

import com.cxynw.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@EqualsAndHashCode
@MappedSuperclass
public class BaseEntity {


    @Column(name = "create_time",nullable = false,
            columnDefinition = "datetime comment '创建时间'")
    private Date createTime;
    @Column(name = "update_time",nullable = false,
            columnDefinition = "datetime comment '更新时间'")
    private Date updateTime;
    @Column(name = "is_deleted",nullable = false,
            columnDefinition = "bit(1) comment '是否被逻辑删除'")
    private Boolean deleted;
    @Column(name = "can_edit",nullable = false,
            columnDefinition = "bit(1) comment '是否能被编辑'")
    private Boolean canEdit;

    @PrePersist
    protected void prePersist(){
        Date now = DateUtils.now();
        if(createTime == null){
            this.createTime = now;
        }
        if(updateTime == null){
            this.updateTime = now;
        }
        if(deleted == null){
            this.deleted = false; //没有被逻辑删除
        }
        if(canEdit == null){
            this.canEdit = true;
        }
    }

    @PreUpdate
    protected void preUpdate(){
        this.updateTime = DateUtils.now();
    }

    @PreRemove
    protected void preRemove(){
        this.updateTime = DateUtils.now();
    }

}
