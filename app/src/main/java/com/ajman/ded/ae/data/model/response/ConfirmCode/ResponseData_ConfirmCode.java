package com.ajman.ded.ae.data.model.response.ConfirmCode;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "ConfirmCodeResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class ResponseData_ConfirmCode {

    @Element(name = "ConfirmCodeResult", required = false)
    private int ConfirmCodeResult;

    public int getConfirmCodeResult() {
        return ConfirmCodeResult;
    }

    public void setConfirmCodeResult(int confirmCodeResult) {
        ConfirmCodeResult = confirmCodeResult;
    }
}
