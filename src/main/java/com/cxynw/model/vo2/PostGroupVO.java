package com.cxynw.model.vo2;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostGroupVO {

    private String id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

}
