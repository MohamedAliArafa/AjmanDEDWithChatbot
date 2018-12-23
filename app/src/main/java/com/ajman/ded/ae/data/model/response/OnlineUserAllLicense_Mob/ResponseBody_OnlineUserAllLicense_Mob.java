package com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_Mob;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "Body", strict = false)
public class ResponseBody_OnlineUserAllLicense_Mob {

    @Element(name = "OnlineUserAllLicense_Mob_jsonResponse", required = false)
    private ResponseData_OnlineUserAllLicense_Mob data;

    public ResponseData_OnlineUserAllLicense_Mob getData() {
        return data;
    }

    public void setData(ResponseData_OnlineUserAllLicense_Mob data) {
        this.data = data;
    }
}
