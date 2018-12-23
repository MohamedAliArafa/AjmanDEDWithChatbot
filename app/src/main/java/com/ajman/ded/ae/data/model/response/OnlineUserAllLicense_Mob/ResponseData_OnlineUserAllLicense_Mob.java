package com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_Mob;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(name = "OnlineUserAllLicense_Mob_jsonResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class ResponseData_OnlineUserAllLicense_Mob {

    @Element(name = "OnlineUserAllLicense_Mob_jsonResult", required = false)
    private String element;

    public String getElements() {
        return element;
    }

    public void setElements(String data) {
        this.element = data;
    }
}
