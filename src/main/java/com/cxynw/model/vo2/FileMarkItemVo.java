package com.cxynw.model.vo2;

import com.cxynw.model.does.FileMark;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileMarkItemVo extends BaseItemVo<FileMarkItemVo.Item>{

    public static FileMarkItemVo generate(Page<FileMark> fileMarkPage){
        FileMarkItemVo vo = new FileMarkItemVo();
        vo.setCurrentPage(fileMarkPage.getNumber()+1);
        vo.setHasPrevious(fileMarkPage.hasPrevious());
        vo.setHasNext(fileMarkPage.hasNext());
        vo.setPageSize(fileMarkPage.getSize());

        Item[] items = new Item[fileMarkPage.getContent().size()];
        List<FileMark> content = fileMarkPage.getContent();
        for(int i=0;i<items.length;i++){
            FileMark fileMark = content.get(i);
            Item item = new Item();
            item.autoSet(fileMark);
            items[i] = item;
        }
        vo.setItems(items);

        return vo;
    }

    @Data
    static class Item{
        private BigInteger fileMarkId;
        private String fileBasename;
        private String fileExtension;
        private Long fileDownloadTimes;
        private Long fileSize;
        private String fileSha256;
        @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
        private Date fileMarkCreateTime;

        public void autoSet(FileMark fileMark){
            this.fileMarkId = fileMark.getFileMarkId();
            this.fileBasename = fileMark.getFileBasename();
            this.fileExtension = fileMark.getExtension();
            this.fileSize = fileMark.getFileSize();
            this.fileSha256 = fileMark.getSha256();
            this.fileDownloadTimes = fileMark.getDownloadTimes();
            this.setFileMarkCreateTime(fileMark.getCreateTime());
        }
    }

}
