package com.cxynw.model.query;

import com.cxynw.model.does.PostGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostGroupQuery {

    @NotBlank(message = "贴吧名不能什么都没有",groups = {Create.class,Edit.class})
    private String name;

    public PostGroup convert(){
        PostGroup postGroup = new PostGroup();
        postGroup.setGroupName(name);
        return postGroup;
    }

}
