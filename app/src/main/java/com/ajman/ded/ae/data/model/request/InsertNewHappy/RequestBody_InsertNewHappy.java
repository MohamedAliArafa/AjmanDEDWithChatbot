package com.ajman.ded.ae.data.model.request.InsertNewHappy;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class RequestBody_InsertNewHappy {

    @Element(name = "InsertNewHappy ", required = false)
    private RequestData_InsertNewHappy requestData;

    public RequestData_InsertNewHappy getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData_InsertNewHappy requestData) {
        this.requestData = requestData;
    }
}
