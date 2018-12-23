package com.ajman.ded.ae.data.model.request.ApplicationListByEmail;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class RequestBody_ApplicationListByEmail {

    @Element(name = "ApplicationListByEmail_json", required = false)
    private RequestData_ApplicationListByEmail requestData;

    public RequestData_ApplicationListByEmail getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData_ApplicationListByEmail requestData) {
        this.requestData = requestData;
    }
}
