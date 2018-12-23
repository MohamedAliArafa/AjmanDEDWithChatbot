package com.ajman.ded.ae.data.model.request.ConfirmCode;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class RequestBody_ConfirmCode {

    @Element(name = "ConfirmCode  ", required = false)
    private RequestData_ConfirmCode requestData;

    public RequestData_ConfirmCode getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData_ConfirmCode requestData) {
        this.requestData = requestData;
    }
}
