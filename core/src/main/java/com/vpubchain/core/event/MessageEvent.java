package com.vpubchain.core.event;

import com.vpubchain.core.coins.Value;

public class MessageEvent {

    private Value value;
    public MessageEvent(Value value){
        this.value=value;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
