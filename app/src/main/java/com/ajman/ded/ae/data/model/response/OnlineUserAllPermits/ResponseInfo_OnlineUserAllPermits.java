package com.ajman.ded.ae.data.model.response.OnlineUserAllPermits;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "OnlineUserAllPermits_jsonResult", strict = false)
public class ResponseInfo_OnlineUserAllPermits {

    @Element(name = "OnlineUserAllPermits_jsonResult", required = false)
    List<Item_OnlineUserAllPermits> elements;

    public List<Item_OnlineUserAllPermits> getElements() {
        return elements;
    }

    public void setElements(List<Item_OnlineUserAllPermits> elements) {
        this.elements = elements;
    }

}
