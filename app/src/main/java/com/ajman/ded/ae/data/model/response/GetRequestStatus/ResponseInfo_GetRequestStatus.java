package com.ajman.ded.ae.data.model.response.GetRequestStatus;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "GetRequestStatus_jsonResult", strict = false)
public class ResponseInfo_GetRequestStatus {

    @Element(name = "GetRequestStatus_jsonResult", required = false)
    private
    List<Item_GetRequestStatus> elements;

    public List<Item_GetRequestStatus> getElements() {
        return elements;
    }

    public void setElements(List<Item_GetRequestStatus> elements) {
        this.elements = elements;
    }

}
