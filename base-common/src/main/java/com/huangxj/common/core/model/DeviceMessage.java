package com.huangxj.common.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName RabbitMqSender
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-07-24 15:30
 * @Version V1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceMessage<T> implements Serializable {

    private String application;

    private String message;

    private String productType;

    private String deviceKey;

    private String productKey;

    private String event;

    private Date date;

    private boolean ignoreLog;

    private T data;

}
