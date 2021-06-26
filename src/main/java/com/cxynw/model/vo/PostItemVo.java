package com.cxynw.model.vo;

import com.cxynw.model.does.Post;
import com.cxynw.model.does.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @see com.cxynw.model.does.Post
 * @see com.cxynw.model.does.User
 */
@Data
public class PostItemVo {

    private Integer currentPage;
    private Boolean hasPrevious;
    private Integer previousPage; // 这个是自动设置的
    private Boolean hasNext;
    private Integer nextPage; // 这个是自动设置的
    private Item[] items;

    public void setHasPrevious(Boolean hasPrevious){
        this.hasPrevious = hasPrevious;
        if(hasPrevious){
            this.previousPage = currentPage -1;
        }
    }

    public void setHasNext(Boolean hasNext){
        this.hasNext = hasNext;
        if(hasNext){
            this.nextPage = currentPage+1;
        }
    }

    public static PostItemVo generate(Page<Post> postPage, Optional<User> currentUser){
        PostItemVo vo = new PostItemVo();
        vo.setCurrentPage(postPage.getNumber()+1);
        vo.setHasPrevious(postPage.hasPrevious());
        vo.setHasNext(postPage.hasNext());

        Item[] items = new Item[postPage.getContent().size()];
        List<Post> content = postPage.getContent();
        for (int i=0;i<items.length;i++) {
            Post post = content.get(i);
            Item item = new Item();
            item.setPostId(post.getPostId());
            item.setPostTitle(post.getTitle());
            item.setUserNickname(post.getPublisher().getNickname());
            item.setPostCreateTime(post.getCreateTime());
            item.setPostVisits(post.getVisits());
            if(currentUser.isPresent()){
                item.setIsOwner(post.getPublisher().getUserId().equals(currentUser.get().getUserId()));
            }else{
                item.setIsOwner(false);
            }
            item.setUserAvatarSrc(post.getPublisher().getAvatarId());
            item.setPostEditSrc();
            item.setPostDetailSrc();
            items[i] = item;
        }
        vo.setItems(items);

        return vo;
    }

    @Data
    static class Item{
        private String postId;
        private String postTitle;
        private String userNickname;
        @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
        private Date postCreateTime;
        private String postVisits; //帖子的访问量
        private Boolean isOwner; //是否是帖子的所有者
        private String userAvatarSrc;
        private String postEditSrc; //帖子的编辑地址
        private String postDetailSrc;


        public void setPostDetailSrc(){
            Assert.notNull(postId,"设置帖子详情地址前必须设置帖子ID");
            this.postDetailSrc = String.format("/detail/%s.html",postId);
        }

        public void setPostEditSrc(){
            Assert.notNull(postId,"设置帖子编辑地址前必须设置帖子ID");
            this.postEditSrc = String.format("/editor/edit/%s.html",postId);
        }

        public void setPostId(BigInteger postId){
            this.postId = postId.toString();
        }

        public void setPostVisits(Long visits){
            if(visits >= 1000){
                this.postVisits = String.format("%d.%dk",visits/1000,visits%1000/100);
            }else{
                this.postVisits = visits.toString();
            }
        }

        public void setUserAvatarSrc(BigInteger avatarId){
            if(avatarId == null){
                Assert.notNull(userNickname,"用户名必须在设置用户头像之前设置");
                this.userAvatarSrc = String.format("/avatar/generate/%s",userNickname);
            }else{
                this.userAvatarSrc = String.format("/avatar/%s",avatarId);
            }
        }
    }

}