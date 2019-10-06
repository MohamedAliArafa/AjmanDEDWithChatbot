package com.ajman.ded.ae.data.model.response.OnlineUseUAEPass;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "OnlineUseUAEPAssInsertUpdate_jsonResponse ", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class ResponseData_UAEPass {

    @Element(name = "OnlineUseUAEPAssInsertUpdate_jsonResult", required = false)
    private String accountResult;

    public String getAccountResult() {
        return accountResult;
    }

    public void setAccountResult(String accountResult) {
        this.accountResult = accountResult;
    }
}
