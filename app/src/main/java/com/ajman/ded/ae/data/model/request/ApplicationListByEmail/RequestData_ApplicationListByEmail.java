package com.ajman.ded.ae.data.model.request.ApplicationListByEmail;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(name = "ApplicationListByEmail_json", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class RequestData_ApplicationListByEmail {

    @Element(name = "Email", required = false)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
