package com.cxynw.model.does;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log")
@Entity(name = "Log")
@ToString(callSuper = true)
@Where(clause = "is_deleted = 0")
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE log SET is_deleted=1 WHERE log_id=?")
public class Log extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id",nullable = false,
            columnDefinition = "bigint unsigned comment '日志主键'")
    private BigInteger logId;

    @Column(name = "log_type",nullable = false,
            columnDefinition = "int comment '日志类型'")
    private Long logType;

    @Column(name = "log_content",nullable = false,length = 1024,
            columnDefinition = "nvarchar(1024) comment '日志的内容'")
    private String logContent;

}
