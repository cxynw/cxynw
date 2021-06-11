package com.cxynw.service;

import com.cxynw.model.vo.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @see PostVO
 * 该接口用来直接查询出post封装好的vo(view object)对象
 */
public interface PostVoService {

    Page<PostVO> page(PageRequest pageRequest);

}
