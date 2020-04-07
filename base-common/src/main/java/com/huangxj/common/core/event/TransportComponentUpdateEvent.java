package com.huangxj.common.core.event;


import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 引擎组件更新
 *
 * @author huangxj
 */
@Data
public class TransportComponentUpdateEvent extends ApplicationEvent {

    private String componentKey;

    public TransportComponentUpdateEvent(Object source, String componentKey) {
        super(source);
        this.componentKey = componentKey;
    }
}
