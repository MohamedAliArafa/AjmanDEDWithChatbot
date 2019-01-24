package com.ajman.ded.ae.data.model.request.UserId;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class RequestBody_UserId {

    @Element(name = "GetAccountInfoByEmail_JOSN", required = false)
    private RequestData_UserId requestData;

    public RequestData_UserId getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData_UserId requestData) {
        this.requestData = requestData;
    }
}
