package com.ajman.ded.ae.data.model.response.GetRequestStatus;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "Body", strict = false)
public class ResponseBody_GetRequestStatus {

    @Element(name = "GetRequestStatus_jsonResponse", required = false)
    public ResponseData_GetRequestStatus data;

    public ResponseData_GetRequestStatus getData() {
        return data;
    }

    public void setData(ResponseData_GetRequestStatus data) {
        this.data = data;
    }
}
