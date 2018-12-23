package com.ajman.ded.ae.data.model.response.ApplicationListByEmail;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "Body", strict = false)
public class ResponseBody_ApplicationListByEmail {

    @Element(name = "ApplicationListByEmail_jsonResponse", required = false)
    public ResponseData_ApplicationListByEmail data;

    public ResponseData_ApplicationListByEmail getData() {
        return data;
    }

    public void setData(ResponseData_ApplicationListByEmail data) {
        this.data = data;
    }
}
