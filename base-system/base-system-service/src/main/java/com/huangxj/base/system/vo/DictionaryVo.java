package com.huangxj.base.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 视图实体类
 *
 * @author huangxj
 * @since 2019-10-09
 */
@Data
@ApiModel(value = "DictionaryVO对象", description = "DictionaryVO对象")
public class DictionaryVo implements Serializable, Comparable<DictionaryVo> {
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
    @ApiModelProperty(value = "1:启用,0禁用")
    private Integer status;
    private Integer sort;

    @ApiModelProperty(value = "子列表")
    private List<DictionaryVo> children;

    @Override
    public int compareTo(DictionaryVo o) {
        if (this.getSort() == null || o.getSort() == null) {
            return -1;
        }
        return this.getSort() - o.getSort();
    }
}
