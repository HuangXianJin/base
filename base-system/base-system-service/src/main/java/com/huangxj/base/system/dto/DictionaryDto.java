package com.huangxj.base.system.dto;

import com.huangxj.base.system.entity.Dictionary;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * 数据传输对象实体类
 *
 * @author huangxj
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictionaryDto extends Dictionary implements Serializable {

}
