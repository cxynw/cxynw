package com.cxynw.model.does;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file_mark")
@Entity(name = "FileMark")
@ToString(callSuper = true)
@Where(clause = "is_deleted = 0")
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE file_mark SET is_deleted=1 WHERE file_mark_id=?")
public class FileMark extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private User uploader;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_mark_id",nullable = false,
            columnDefinition = "bigint unsigned comment '文件标记主键'")
    private BigInteger fileMarkId;

    @Column(name = "file_type",nullable = false,
            columnDefinition = "int comment '文件类型'")
    private Integer fileType;
    @Column(name = "file_basename",length = 256 ,nullable = false,
            columnDefinition = "nvarchar(256) comment '文件的基本名字'")
    private String fileBasename;
    @Column(name = "extension",length = 256 ,nullable = false,
            columnDefinition = "nvarchar(256) comment '文件的扩展名'")
    private String extension;
    @Column(name = "download_times",nullable = false,
            columnDefinition = "bigint comment '下载次数'")
    private Long downloadTimes;
    @Column(name = "file_size",nullable = false,
            columnDefinition = "bigint comment '文件大小'")
    private Long fileSize;
    @Column(name = "sha256",nullable = false,length = 64,
            columnDefinition = "char(64) comment '文件的sha256值'")
    private String sha256;

    @Column(name = "download_password",length = 60,nullable = true,
            columnDefinition = "char(64) comment '文件的下载密码'")
    private String downloadPassword;

    @Override
    protected void prePersist() {
        super.prePersist();
        if(downloadTimes == null){
            this.downloadTimes = 0l;
        }
        if(extension == null){
            this.extension = "";
        }
    }
}
