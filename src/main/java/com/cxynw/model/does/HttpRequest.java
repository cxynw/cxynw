package com.cxynw.model.does;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * 记录访问论坛的所有http请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "http_request")
@Entity(name = "HttpRequest")
@ToString(callSuper = true)
@Where(clause = "is_deleted = 0")
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE http_request SET is_deleted=1 WHERE http_request_id=?")
public class HttpRequest extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "http_request_id",nullable = false,
            columnDefinition = "bigint unsigned comment 'http请求的主键'")
    private BigInteger httpRequestId;

    @NotNull
    @Column(name = "request_method",nullable = false,
            columnDefinition = "varchar(16) comment '请求采用的方法'")
    private String requestMethod;

    @NotNull
    @Column(name = "protocol",nullable = false,
            columnDefinition = "varchar(16) comment '协议'")
    private String protocol;

    @NotNull
    @Column(name = "request_uri",nullable = false,
            columnDefinition = "varchar(128) comment 'request uri'")
    private String requestURI;

    @NotNull
    @Column(name = "content_length",nullable = false,
            columnDefinition = "bigint comment 'content length'")
    private Long contentLength;

    @Column(name = "content_type",nullable = true,
            columnDefinition = "varchar(128) comment 'content type'")
    private String contentType;

    @Column(name = "character_encoding",nullable = true,columnDefinition = "varchar(32) comment 'character encoding'")
    private String characterEncoding;

    @Column(name = "query_string",nullable = true,
            columnDefinition = "varchar(256) comment 'query string'")
    private String queryString;

    @Column(name = "user_agent",nullable = true,columnDefinition = "varchar(512)")
    private String userAgent;

    @Column(name = "referer",nullable = true,columnDefinition = "nvarchar(1024)")
    private String referer;

}
