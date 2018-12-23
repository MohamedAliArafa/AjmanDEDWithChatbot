package com.ajman.ded.ae.data.model.response.InsertNewHappy;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Body", strict = false)
public class ResponseBody_InsertNewHappy {

    @Element(name = "InsertNewHappyResult", required = false)
    private ResponseData_InsertNewHappy data;

    public ResponseData_InsertNewHappy getData() {
        return data;
    }

    public void setData(ResponseData_InsertNewHappy data) {
        this.data = data;
    }
}
