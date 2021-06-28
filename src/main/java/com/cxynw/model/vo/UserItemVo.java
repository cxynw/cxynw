package com.cxynw.model.vo;

import com.cxynw.model.does.Post;
import com.cxynw.model.does.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserItemVo extends BaseItemVo<UserItemVo.Item>{

    public static UserItemVo generate(Page<User> userPage) {
        UserItemVo vo = new UserItemVo();
        vo.setCurrentPage(userPage.getNumber() + 1);
        vo.setHasPrevious(userPage.hasPrevious());
        vo.setHasNext(userPage.hasNext());

        UserItemVo.Item[] items = new UserItemVo.Item[userPage.getContent().size()];
        List<User> content = userPage.getContent();
        for (int i = 0; i < items.length; i++) {
            Item item = new Item();
            User user = content.get(i);
            item.setUsername(user.getUsername());
            item.setEmail(user.getEmail());
            item.setNickname(user.getNickname());
            item.setUserId(user.getUserId().toString());
            items[i] = item;
        }
        vo.setItems(items);
        return vo;
    }

    @Data
    static class Item{
        private String username;
        private String email;
        private String nickname;
        private String userId;
    }

}
