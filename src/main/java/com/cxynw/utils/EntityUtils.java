package com.cxynw.utils;

import com.cxynw.model.does.*;
import com.cxynw.model.vo.PostGroupVO;
import com.cxynw.model.vo.PostItemVo;
import com.cxynw.model.vo.PostVO;
import org.springframework.data.domain.Page;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class EntityUtils {

    public static List<PostGroupVO> convertToPostGroupVO(Page<PostGroup> page){
        return page.getContent().stream().map((item)->{
            PostGroupVO vo = new PostGroupVO(
                    item.getPostGroupId().toString(),
                    item.getGroupName(),
                    item.getCreateTime()
            );
            return vo;
        }).collect(Collectors.toList());
    }

    public static Page<PostVO> convertToPagePostVO(Page<Post> page, Optional<User> currentUser){
        return page.map((item)->{
            boolean isMe = false;
            if(currentUser.isPresent()){
                isMe = item.getPublisher().getUserId() == currentUser.get().getUserId();
            }
            PostVO vo = new PostVO(item,isMe);
            return vo;
        });
    }

    public static User generateUserById(BigInteger id){
        User user = new User();
        user.setUserId(id);
        return user;
    }

    public static Post generatePostById(BigInteger id){
        Post post = new Post();
        post.setPostId(id);
        return post;
    }

    public static PostComment generatePostCommentById(BigInteger id){
        PostComment comment = new PostComment();
        comment.setPostCommentId(id);
        return comment;
    }

    public static Set<PostGroup> convertToSetPostGroup(PostGroup... postGroups){
        Set<PostGroup> groupSize = new LinkedHashSet<>(postGroups.length);
        Arrays.stream(postGroups).forEach((item)->{
            groupSize.add(item);
        });
        return groupSize;
    }

    public static Set<Actor> convertToSetRoles(Actor... actors){
        Set<Actor> actorSet = new LinkedHashSet<>(actors.length);
        Arrays.stream(actors).forEach((item)->{
            actorSet.add(item);
        });
        return actorSet;
    }

    public static Set<Actor> convertToSetRoles(Actor actor){
        Set<Actor> actorSet = new LinkedHashSet<>(1);
        actorSet.add(actor);
        return actorSet;
    }

}
