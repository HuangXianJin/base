package com.huangxj.base.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * 数据字典
 * @author huangxj
 * @date 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@TableName("sys_dictionary")
@ApiModel(value="Dictionary对象", description="")
public class Dictionary implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "编码")
    private String itemCode;

    @ApiModelProperty(value = "索引编码")
    private String indexCode;

    @ApiModelProperty(value = "名称")
    private String itemName;

    @ApiModelProperty(value = "取值")
    private String itemValue;

    @ApiModelProperty(value = "描述")
    private String description;

    @TableField(fill = FieldFill.INSERT)
    private Integer createId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Integer modifyId;

    @TableField(fill = FieldFill.UPDATE)
    private Date modifyTime;

    @ApiModelProperty(value = "1:启用,0禁用")
    private Integer status;

    private Integer sort;


}
