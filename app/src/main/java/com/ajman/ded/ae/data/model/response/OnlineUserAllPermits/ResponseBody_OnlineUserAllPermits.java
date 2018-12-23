package com.ajman.ded.ae.data.model.response.OnlineUserAllPermits;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Body", strict = false)
public class ResponseBody_OnlineUserAllPermits {

    @Element(name = "OnlineUserAllPermits_jsonResponse", required = false)
    private ResponseData_OnlineUserAllPermits data;

    public ResponseData_OnlineUserAllPermits getData() {
        return data;
    }

    public void setData(ResponseData_OnlineUserAllPermits data) {
        this.data = data;
    }
}
