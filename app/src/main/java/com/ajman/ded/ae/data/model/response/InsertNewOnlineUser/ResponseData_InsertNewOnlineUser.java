package com.ajman.ded.ae.data.model.response.InsertNewOnlineUser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "InsertNewOnlineUser_JosnResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class ResponseData_InsertNewOnlineUser {

    @Element(name = "InsertNewOnlineUser_JosnResult", required = false)
    private String InsertAccountResult;

    public String getInsertAccountResult() {
        return InsertAccountResult;
    }

    public void setInsertAccountResult(String insertAccountResult) {
        this.InsertAccountResult = insertAccountResult;
    }
}
