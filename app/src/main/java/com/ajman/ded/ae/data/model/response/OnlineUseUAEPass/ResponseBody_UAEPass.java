package com.ajman.ded.ae.data.model.response.OnlineUseUAEPass;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Body", strict = false)
public class ResponseBody_UAEPass {

    @Element(name = "OnlineUseUAEPAssInsertUpdate_jsonResponse", required = false)
    public ResponseData_UAEPass data;

    public ResponseData_UAEPass getData() {
        return data;
    }

    public void setData(ResponseData_UAEPass data) {
        this.data = data;
    }
}
