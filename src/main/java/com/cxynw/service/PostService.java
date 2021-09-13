package com.cxynw.service;


import com.cxynw.model.does.Post;
import com.cxynw.model.does.PostGroup;
import com.cxynw.model.does.User;
import com.cxynw.model.enums.PostTypeEnum;
import com.cxynw.model.param.PostParam;
import com.cxynw.model.response.BaseSuccessResponse;
import com.cxynw.model.vo2.PostItemVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.util.Optional;

public interface PostService {

    /**
     * 创建一个帖子
     * @param postParam
     * @return
     */
    BaseSuccessResponse create(PostParam postParam);

    @Deprecated(since = "函数设计不合理")
    Page<Post> findByPublisher(PageRequest pageRequest);

    @Deprecated(since = "函数设计不合理")
    Page<Post> page(PageRequest pageRequest);

    /**
     * 查询帖子列表
     * @param postTypeEnum null 不区分帖子类型，非null 区分帖子类型
     * @param pageRequest
     * @return
     */
    PostItemVo pagePostItemVo(PostTypeEnum postTypeEnum,PostGroup postGroup, PageRequest pageRequest);

    @Deprecated(since = "方法设计不合理")
    Page<Post> pageByGroup(PageRequest pageRequest, PostGroup group);

    Optional<Post> findById(BigInteger id);

    BaseSuccessResponse deleteById(BigInteger id);

    BaseSuccessResponse edit(PostParam postParam);

    PostItemVo searchByKeywords(String keywords,PostTypeEnum postTypeEnum,PageRequest pageRequest);

    Page<Post> searchByKeywords(String keywords,PostTypeEnum postTypeEnum,User user,PageRequest pageRequest);

    int addVisitsById(BigInteger id);

}
