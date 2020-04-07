package com.huangxj.base.system.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 文件资源管理查询参数
 *
 * @author huangxj
 * @since 2020-02-20
 */
@Data
public class FileParam {
    private String filter;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(name = "startDate", value = "开始时间", dataType = "Date")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(name = "endDate", value = "结束时间", dataType = "Date")
    private Date endDate;
    @ApiModelProperty(name = "originName", value = "原始文件名", dataType = "String")
    private String originName;
}
