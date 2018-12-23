package com.ajman.ded.ae.data.model.request.ConfirmCode;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(name = "ConfirmCode  ", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class RequestData_ConfirmCode {

    @Element(name = "Email", required = false)
    private String email;

    @Element(name = "MobileConfirmationCode", required = false)
    private String mobileConfirmationCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileConfirmationCode() {
        return mobileConfirmationCode;
    }

    public void setMobileConfirmationCode(String mobileConfirmationCode) {
        this.mobileConfirmationCode = mobileConfirmationCode;
    }
}
