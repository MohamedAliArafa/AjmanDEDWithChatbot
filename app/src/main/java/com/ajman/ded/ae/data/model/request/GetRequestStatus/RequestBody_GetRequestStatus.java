package com.ajman.ded.ae.data.model.request.GetRequestStatus;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class RequestBody_GetRequestStatus {

    @Element(name = "GetRequestStatus_json", required = false)
    private RequestData_GetRequestStatus requestData;

    public RequestData_GetRequestStatus getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData_GetRequestStatus requestData) {
        this.requestData = requestData;
    }
}
