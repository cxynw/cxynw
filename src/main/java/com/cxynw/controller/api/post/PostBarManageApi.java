package com.cxynw.controller.api.post;

import com.cxynw.model.does.PostGroup;
import com.cxynw.model.enums.CodeEnum;
import com.cxynw.model.query.Create;
import com.cxynw.model.query.Edit;
import com.cxynw.model.query.PostGroupQuery;
import com.cxynw.model.vo2.BasePageVO;
import com.cxynw.model.vo2.BaseVO;
import com.cxynw.model.vo2.PostGroupPageVO;
import com.cxynw.model.vo2.PostGroupVO;
import com.cxynw.service.PostGroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post/group")
public class PostBarManageApi {

    private final PostGroupService groupService;

    public PostBarManageApi(PostGroupService groupService) {
        this.groupService = groupService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = {""})
    @ApiOperation("查询贴吧信息")
    public BasePageVO<List<PostGroupVO>> page(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @Valid @Max(value = 64,message = "最多一次性显示64个") @Min(value = 1,message = "最少需要显示一个") @RequestParam(value = "amount",defaultValue = "24") Integer amount,
            @Valid @RequestParam(value = "order",defaultValue = "desc") String order){
        if(page < 1){
            page = 1;
        }
        if(!order.equalsIgnoreCase("desc") || order.equalsIgnoreCase("asc")){
            order = "desc";
        }

        Page<PostGroup> groupPage = groupService.page(PageRequest.of(page-1, amount, Sort.Direction.fromString(order), "createTime"));

        return new PostGroupPageVO(groupPage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    @ApiOperation("创建一个贴吧")
    public BaseVO create(@Validated(Create.class) @RequestBody PostGroupQuery query){
        groupService.create(query.convert());
        return new BaseVO(CodeEnum.SUCCEED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ApiOperation("根据贴吧的ID删除贴吧")
    public BaseVO delete(@PathVariable("id")BigInteger id){
        Optional<PostGroup> group = groupService.findById(id);
        if(group.isEmpty()){
            return new BaseVO(CodeEnum.FAIL,"要删除的贴吧不存在");
        }
        groupService.removeById(id);
        return new BaseVO(CodeEnum.SUCCEED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    @ApiOperation("更新贴吧的名字")
    public BaseVO update(@PathVariable("id")BigInteger id, @Validated(Edit.class)@RequestBody PostGroupQuery query){
        Optional<PostGroup> group = groupService.findById(id);
        if(group.isEmpty()){
            return new BaseVO(CodeEnum.FAIL,"要编辑的贴吧不存在");
        }

        group.get().setGroupName(query.getName());
        return new BaseVO(CodeEnum.SUCCEED);
    }

}
