package com.cxynw.model.vo2;

import com.cxynw.model.does.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserItemVo extends BaseItemVo<UserItemVo.Item>{

    public static UserItemVo generate(Page<User> userPage) {
        UserItemVo vo = new UserItemVo();
        vo.setCurrentPage(userPage.getNumber() + 1);
        vo.setHasPrevious(userPage.hasPrevious());
        vo.setHasNext(userPage.hasNext());
        vo.setPageSize(userPage.getSize());


        UserItemVo.Item[] items = new UserItemVo.Item[userPage.getContent().size()];
        List<User> content = userPage.getContent();
        for (int i = 0; i < items.length; i++) {
            Item item = new Item();
            User user = content.get(i);
            item.setUserUsername(user.getUsername());
            item.setUserEmail(user.getEmail());
            item.setUserNickname(user.getNickname());
            item.setUserId(user.getUserId().toString());
            item.setUserCreateTime(user.getCreateTime());
            items[i] = item;
        }
        vo.setItems(items);
        return vo;
    }

    @Data
    static class Item{
        private String userUsername;
        private String userEmail;
        private String userNickname;
        private String userId;
        @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
        private Date userCreateTime;
    }

}
