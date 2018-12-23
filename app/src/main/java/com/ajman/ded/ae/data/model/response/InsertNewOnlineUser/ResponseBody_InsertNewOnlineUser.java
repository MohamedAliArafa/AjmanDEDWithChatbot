package com.ajman.ded.ae.data.model.response.InsertNewOnlineUser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Body", strict = false)
public class ResponseBody_InsertNewOnlineUser {

    @Element(name = "InsertNewOnlineUser_JosnResponse", required = false)
    private ResponseData_InsertNewOnlineUser data;

    public ResponseData_InsertNewOnlineUser getData() {
        return data;
    }

    public void setData(ResponseData_InsertNewOnlineUser data) {
        this.data = data;
    }
}
