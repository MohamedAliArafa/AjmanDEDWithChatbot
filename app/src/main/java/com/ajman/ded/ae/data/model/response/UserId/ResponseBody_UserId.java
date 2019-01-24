package com.ajman.ded.ae.data.model.response.UserId;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Body", strict = false)
public class ResponseBody_UserId {

    @Element(name = "GetAccountInfoByEmail_JOSNResponse", required = false)
    private ResponseData_UserId data;

    public ResponseData_UserId getData() {
        return data;
    }

    public void setData(ResponseData_UserId data) {
        this.data = data;
    }
}
