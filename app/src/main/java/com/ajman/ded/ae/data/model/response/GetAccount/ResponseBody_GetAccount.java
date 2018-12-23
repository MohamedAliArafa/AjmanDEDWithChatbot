package com.ajman.ded.ae.data.model.response.GetAccount;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Body", strict = false)
public class ResponseBody_GetAccount {

    @Element(name = "GetAccount_JOSNResponse", required = false)
    public ResponseData_GetAccount data;

    public ResponseData_GetAccount getData() {
        return data;
    }

    public void setData(ResponseData_GetAccount data) {
        this.data = data;
    }
}
