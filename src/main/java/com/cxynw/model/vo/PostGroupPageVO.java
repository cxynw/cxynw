package com.cxynw.model.vo;

import com.cxynw.model.does.PostGroup;
import com.cxynw.utils.EntityUtils;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@ToString(callSuper = true)
public class PostGroupPageVO extends BasePageVO<List<PostGroupVO>>{

    public PostGroupPageVO(Page<PostGroup> page){
        setStatusCode(200);
        setMessage("success");
        setNextPage(page.getNumber()+2);
        setPreviousPage(page.getNumber());
        setHasNext(page.hasNext());
        setHasPrevious(page.hasPrevious());
        setData(EntityUtils.convertToPostGroupVO(page));
    }

}
