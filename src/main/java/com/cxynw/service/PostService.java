package com.cxynw.service;


import com.cxynw.model.does.Post;
import com.cxynw.model.does.PostGroup;
import com.cxynw.model.param.PostParam;
import com.cxynw.model.response.BaseSuccessResponse;
import com.cxynw.model.vo.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.util.Optional;

public interface PostService {

    BaseSuccessResponse create(PostParam postParam);

    Page<Post> findByPublisher(PageRequest pageRequest);

    Page<Post> page(PageRequest pageRequest);

    Page<Post> pageByGroup(PageRequest pageRequest, PostGroup group);

    Optional<Post> findById(BigInteger id);

    BaseSuccessResponse deleteById(BigInteger id);

    BaseSuccessResponse edit(PostParam postParam);

    Page<PostVO> searchByKeywords(String keywords,PageRequest pageRequest);

    int addVisitsById(BigInteger id);

}
