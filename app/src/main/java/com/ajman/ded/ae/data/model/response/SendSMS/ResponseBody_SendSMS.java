package com.ajman.ded.ae.data.model.response.SendSMS;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Body", strict = false)
public class ResponseBody_SendSMS {

    @Element(name = "SendSMSResponse", required = false)
    private ResponseData_SendSMS data;

    public ResponseData_SendSMS getData() {
        return data;
    }

    public void setData(ResponseData_SendSMS data) {
        this.data = data;
    }
}
