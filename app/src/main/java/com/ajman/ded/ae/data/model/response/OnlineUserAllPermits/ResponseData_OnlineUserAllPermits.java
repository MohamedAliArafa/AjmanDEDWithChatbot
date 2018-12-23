package com.ajman.ded.ae.data.model.response.OnlineUserAllPermits;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "OnlineUserAllPermits_jsonResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class ResponseData_OnlineUserAllPermits {

    @Element(name = "OnlineUserAllPermits_jsonResult", required = false)
    private String elements;

    public String getElements() {
        return elements;
    }

    public void setElements(String element) {
        this.elements = element;
    }
}
