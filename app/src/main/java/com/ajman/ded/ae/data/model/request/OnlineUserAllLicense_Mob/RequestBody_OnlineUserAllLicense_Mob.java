package com.ajman.ded.ae.data.model.request.OnlineUserAllLicense_Mob;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class RequestBody_OnlineUserAllLicense_Mob {

    @Element(name = "OnlineUserAllLicense_Mob_json", required = false)
    private RequestData_OnlineUserAllLicense_Mob requestData;

    public RequestData_OnlineUserAllLicense_Mob getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData_OnlineUserAllLicense_Mob requestData) {
        this.requestData = requestData;
    }
}
