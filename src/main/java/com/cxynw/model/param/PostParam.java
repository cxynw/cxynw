package com.cxynw.model.param;

import com.cxynw.model.dto.base.InputConverter;
import com.cxynw.model.does.Post;
import com.cxynw.model.query.Edit;
import com.cxynw.model.query.Create;
import com.cxynw.security.MyWhiteList;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;


@Data
public class PostParam implements InputConverter<Post> {

    @NotBlank(message = "标题不能为空",groups = {Create.class, Edit.class})
    @Size(max = 512,message = "标题长度不能超过{max}",groups = {Create.class,Edit.class})
    private String title;
    @NotBlank(message = "内容不能为空",groups = {Create.class,Edit.class})
    private String content;

    @NotNull(message = "文章类型不能为空",groups = {Create.class,Edit.class})
    private Integer postType;
    @NotNull(message = "发布分组不能为空",groups = {Create.class})
    private BigInteger groupId;

    private BigInteger postId;
    private String attachmentPassword;
    private MultipartFile attachment;

    @Override
    public Post convertTo() {
        Post post = new Post();
        post.setTitle(getTitle());
        post.setContent(getContent());
        post.setPostType(getPostType());
        post.setPostId(getPostId());
        return post;
    }

    public String getTitle(){
        return Jsoup.clean(title, Whitelist.none()); //防止xss注入
    }

    public String getContent(){
        Cleaner cleaner = new Cleaner(MyWhiteList.relaxed().addEnforcedAttribute("a", "rel", "nofollow"));
        Document clean = cleaner.clean(Jsoup.parseBodyFragment(content));
        return clean.body().html();
    }

}
