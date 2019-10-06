package com.ajman.ded.ae.data.model.request.OnlineUseUAEPass;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(name = "OnlineUseUAEPAssInsertUpdate_json", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class RequestData_OnlineUaePass {

    @Element(name = "mobile", required = false)
    private String mobile;

    @Element(name = "email", required = false)
    private String email;

    @Element(name = "idn", required = false)
    private String idn;

    @Element(name = "fullnameAR", required = false)
    private String fullnameAR;

    @Element(name = "fullnameEN", required = false)
    private String fullnameEN;

    @Element(name = "passportNumber", required = false)
    private String passportNumber;

    @Element(name = "idCardExpiryDate", required = false)
    private String idCardExpiryDate;

    @Element(name = "passportExpiryDate", required = false)
    private String passportExpiryDate;

    @Element(name = "uuid", required = false)
    private String uuid;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdn() {
        return idn;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public String getFullnameAR() {
        return fullnameAR;
    }

    public void setFullnameAR(String fullnameAR) {
        this.fullnameAR = fullnameAR;
    }

    public String getFullnameEN() {
        return fullnameEN;
    }

    public void setFullnameEN(String fullnameEN) {
        this.fullnameEN = fullnameEN;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getIdCardExpiryDate() {
        return idCardExpiryDate;
    }

    public void setIdCardExpiryDate(String idCardExpiryDate) {
        this.idCardExpiryDate = idCardExpiryDate;
    }

    public String getPassportExpiryDate() {
        return passportExpiryDate;
    }

    public void setPassportExpiryDate(String passportExpiryDate) {
        this.passportExpiryDate = passportExpiryDate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
