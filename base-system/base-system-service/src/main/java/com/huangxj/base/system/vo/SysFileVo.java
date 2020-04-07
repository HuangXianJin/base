package com.huangxj.base.system.vo;

import com.huangxj.common.core.utils.FileUtil;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 文件资源管理视图实体类
 *
 * @author huangxj
 * @since 2020-02-20
 */
@Data
@ApiModel(value = "FileVO对象", description = "文件资源管理")
public class SysFileVo implements Serializable {
    private Integer id;
    @ApiModelProperty(value = "附件编码")
    private String attachmentCode;
    @ApiModelProperty(value = "原始文件名")
    private String originName;
    @ApiModelProperty(value = "文件大小")
    private String fileSize;
    @ApiModelProperty(value = "文件类型")
    private String fileType;
    @ApiModelProperty(value = "文件存放路径")
    private String url;
    @ApiModelProperty(value = "文件描述")
    private String description;
    @ApiModelProperty(value = "操作")
    private String operation;
    @ApiModelProperty(value = "操作人")
    private String operator;
    @ApiModelProperty(value = "操作时间")
    private String operateTime;
    @ApiModelProperty(value = "0:无效,1:有效")
    private Integer status;

    public String getSysUrl() {
        if (StringUtils.isBlank(url)){
            return null;
        }
        return FileUtil.BASE_URL+url;
    }

}
