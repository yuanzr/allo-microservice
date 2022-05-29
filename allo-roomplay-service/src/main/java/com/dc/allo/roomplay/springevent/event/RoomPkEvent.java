package com.dc.allo.roomplay.springevent.event;

import org.springframework.context.ApplicationEvent;

public class RoomPkEvent extends ApplicationEvent {
    public RoomPkEvent(Object source) {
        super(source);
    }
}
