package com.ajman.ded.ae.data.model.request.GetAccount;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class RequestBody_GetAccount {

    @Element(name = "GetAccount_JOSN", required = false)
    private RequestData_GetAccount requestData;

    public RequestData_GetAccount getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData_GetAccount requestData) {
        this.requestData = requestData;
    }
}
