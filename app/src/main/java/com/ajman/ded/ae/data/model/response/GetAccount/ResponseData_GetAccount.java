package com.ajman.ded.ae.data.model.response.GetAccount;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "GetAccount_JOSNResponse ", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class ResponseData_GetAccount {

    @Element(name = "GetAccount_JOSNResult", required = false)
    private String accountResult;

    public String getAccountResult() {
        return accountResult;
    }

    public void setAccountResult(String accountResult) {
        this.accountResult = accountResult;
    }
}
