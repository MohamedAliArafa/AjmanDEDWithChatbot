package com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_CountMob;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Body", strict = false)
public class ResponseBody_OnlineUserAllLicense_CountMob {

    @Element(name = "OnlineUserAllLicense_CountMob_jsonResponse", required = false)
    private ResponseData_OnlineUserAllLicense_CountMob data;

    public ResponseData_OnlineUserAllLicense_CountMob getData() {
        return data;
    }

    public void setData(ResponseData_OnlineUserAllLicense_CountMob data) {
        this.data = data;
    }
}
