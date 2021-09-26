package com.cxynw.model.vo;

import com.cxynw.model.does.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostItemPageVo extends BasePageVo<PostItemVo>{

    public PostItemPageVo(Page<Post> postPage){
        setHasNext(postPage.hasNext());
        setHasPrevious(postPage.hasPrevious());
        setMessage("post item");
        setContent(postConvert(postPage.getContent()));
        setNextPage(postPage.getNumber()+2);
        setPreviousPage(postPage.getNumber());
    }

    private List<PostItemVo> postConvert(List<Post> postList){
        return postList.stream().map((item)-> new PostItemVo(item)).collect(Collectors.toList());
    }


}
