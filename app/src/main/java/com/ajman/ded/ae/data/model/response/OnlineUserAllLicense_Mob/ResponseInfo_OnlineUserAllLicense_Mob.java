package com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_Mob;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "OnlineUserAllLicense_Mob_jsonResult", strict = false)
public class ResponseInfo_OnlineUserAllLicense_Mob {

    @Element(name = "OnlineUserAllLicense_Mob_jsonResult", required = false)
    List<ItemOnlineUserAllLicenseMob> elements;

    public List<ItemOnlineUserAllLicenseMob> getElements() {
        return elements;
    }

    public void setElements(List<ItemOnlineUserAllLicenseMob> elements) {
        this.elements = elements;
    }

}
