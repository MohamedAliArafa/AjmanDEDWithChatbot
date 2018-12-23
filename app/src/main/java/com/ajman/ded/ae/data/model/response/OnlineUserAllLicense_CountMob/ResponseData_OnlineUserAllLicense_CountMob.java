package com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_CountMob;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "OnlineUserAllLicense_CountMob_jsonResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class ResponseData_OnlineUserAllLicense_CountMob {

    @Element(name = "OnlineUserAllLicense_CountMob_jsonResult", required = false)
    private String elements;

    public String getElements() {
        return elements;
    }

    public void setElements(String element) {
        this.elements = element;
    }
}
