package com.ajman.ded.ae.data.model.response.ApplicationListByEmail;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "ApplicationListByEmail_jsonResult", strict = false)
public class ResponseInfo_ApplicationListByEmail {

    @Element(name = "ApplicationListByEmail_jsonResult", required = false)
    String elements;

    public String getElements() {
        return elements;
    }

    public void setElements(String elements) {
        this.elements = elements;
    }
}
