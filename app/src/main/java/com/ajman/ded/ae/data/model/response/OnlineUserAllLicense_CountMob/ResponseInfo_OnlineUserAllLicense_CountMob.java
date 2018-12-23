package com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_CountMob;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "OnlineUserAllLicense_CountMob_jsonResult", strict = false)
public class ResponseInfo_OnlineUserAllLicense_CountMob {

    @Element(name = "OnlineUserAllLicense_CountMob_jsonResult", required = false)
    List<Item_OnlineUserAllLicense_CountMob> elements;

    public List<Item_OnlineUserAllLicense_CountMob> getElements() {
        return elements;
    }

    public void setElements(List<Item_OnlineUserAllLicense_CountMob> elements) {
        this.elements = elements;
    }

}
