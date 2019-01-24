package com.ajman.ded.ae.data.model.request.OnlineUserAllLicense_CountMob;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class RequestBody_OnlineUserAllLicense_CountMob {

    @Element(name = "OnlineUserAllLicense_CountMob_json", required = false)
    private RequestData_OnlineUserAllLicense_CountMob requestData;

    public RequestData_OnlineUserAllLicense_CountMob getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData_OnlineUserAllLicense_CountMob requestData) {
        this.requestData = requestData;
    }
}
