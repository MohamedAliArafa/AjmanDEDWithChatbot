package com.ajman.ded.ae.data.model.request.OnlineUseUAEPass;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Body", strict = false)
public class RequestBody_OnlineUaePass {

    @Element(name = "OnlineUseUAEPAssInsertUpdate_json", required = false)
    private RequestData_OnlineUaePass requestData;

    public RequestData_OnlineUaePass getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData_OnlineUaePass requestData) {
        this.requestData = requestData;
    }
}
