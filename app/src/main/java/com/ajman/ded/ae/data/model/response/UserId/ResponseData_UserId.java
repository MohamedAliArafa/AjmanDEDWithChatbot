package com.ajman.ded.ae.data.model.response.UserId;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "GetAccountInfoByEmail_JOSNResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class ResponseData_UserId {

    @Element(name = "GetAccountInfoByEmail_JOSNResult", required = false)
    private String elements;

    public String getElements() {
        return elements;
    }

    public void setElements(String element) {
        this.elements = element;
    }
}
