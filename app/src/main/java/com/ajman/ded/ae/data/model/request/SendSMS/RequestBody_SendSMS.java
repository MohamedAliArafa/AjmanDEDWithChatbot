package com.ajman.ded.ae.data.model.request.SendSMS;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class RequestBody_SendSMS {

    @Element(name = "SendSMS ", required = false)
    private RequestData_SendSMS requestData;

    public RequestData_SendSMS getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData_SendSMS requestData) {
        this.requestData = requestData;
    }
}
