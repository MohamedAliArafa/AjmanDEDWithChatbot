package com.ajman.ded.ae.data.model.request.GetAccount;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(name = "GetAccount_JOSN", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class RequestData_GetAccount {

    @Element(name = "Email", required = false)
    private String email;

    @Element(name = "Password", required = false)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
