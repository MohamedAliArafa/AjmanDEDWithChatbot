package com.ajman.ded.ae.data.model.request.OnlineUserAllPermits;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class RequestBody_OnlineUserAllPermits {

    @Element(name = "OnlineUserAllPermits_json", required = false)
    private RequestData_OnlineUserAllPermits requestData;

    public RequestData_OnlineUserAllPermits getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData_OnlineUserAllPermits requestData) {
        this.requestData = requestData;
    }
}
