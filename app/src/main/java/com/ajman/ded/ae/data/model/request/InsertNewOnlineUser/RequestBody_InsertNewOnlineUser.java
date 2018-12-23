package com.ajman.ded.ae.data.model.request.InsertNewOnlineUser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class RequestBody_InsertNewOnlineUser {

    @Element(name = "InsertNewOnlineUser_Josn", required = false)
    private RequestData_InsertNewOnlineUser requestData;

    public RequestData_InsertNewOnlineUser getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData_InsertNewOnlineUser requestData) {
        this.requestData = requestData;
    }
}
