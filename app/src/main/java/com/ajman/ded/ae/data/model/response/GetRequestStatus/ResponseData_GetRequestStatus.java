package com.ajman.ded.ae.data.model.response.GetRequestStatus;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(name = "GetRequestStatus_jsonResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class ResponseData_GetRequestStatus {

    @Element(name = "GetRequestStatus_jsonResult", required = false)
    private String elements;

    public String getElements() {
        return elements;
    }

    public void setElements(String elements) {
        this.elements = elements;
    }
}
