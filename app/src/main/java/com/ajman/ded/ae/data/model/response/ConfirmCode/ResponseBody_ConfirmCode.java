package com.ajman.ded.ae.data.model.response.ConfirmCode;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Body", strict = false)
public class ResponseBody_ConfirmCode {

    @Element(name = "ConfirmCodeResponse", required = false)
    private ResponseData_ConfirmCode data;

    public ResponseData_ConfirmCode getData() {
        return data;
    }

    public void setData(ResponseData_ConfirmCode data) {
        this.data = data;
    }
}
