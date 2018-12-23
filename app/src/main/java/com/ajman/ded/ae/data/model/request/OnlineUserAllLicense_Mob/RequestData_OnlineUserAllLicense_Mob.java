package com.ajman.ded.ae.data.model.request.OnlineUserAllLicense_Mob;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(name = "OnlineUserAllLicense_Mob_json", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class RequestData_OnlineUserAllLicense_Mob {

    @Element(name = "Email", required = false)
    private String email;

    @Element(name = "flagLicenseStatus", required = false)
    private String flagLicenseStatus;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFlagLicenseStatus() {
        return flagLicenseStatus;
    }

    public void setFlagLicenseStatus(String flagLicenseStatus) {
        this.flagLicenseStatus = flagLicenseStatus;
    }
}
