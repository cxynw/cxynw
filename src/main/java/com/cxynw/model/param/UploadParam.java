package com.cxynw.model.param;

import com.cxynw.model.enums.DataTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UploadParam {

    /**
     * @see DataTypeEnum
     */
    @NotBlank(message = "数据类型不能为空")
    private String dataType;


}
