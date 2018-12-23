package com.ajman.ded.ae.data.model.request.GetRequestStatus;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(name = "GetRequestStatus_json", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class RequestData_GetRequestStatus {

    @Element(name = "ApplicationNo", required = false)
    private String applicationNo;

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }
}
