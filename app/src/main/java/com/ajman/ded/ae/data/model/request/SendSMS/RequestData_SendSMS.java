package com.ajman.ded.ae.data.model.request.SendSMS;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(name = "SendSMS ", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class RequestData_SendSMS {

    @Element(name = "MobileNo", required = false)
    private String mobileNo;

    @Element(name = "MessageEN", required = false)
    private String messageEN;

    @Element(name = "MessageAR", required = false)
    private String messageAR;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMessageEN() {
        return messageEN;
    }

    public void setMessageEN(String messageEN) {
        this.messageEN = messageEN;
    }

    public String getMessageAR() {
        return messageAR;
    }

    public void setMessageAR(String messageAR) {
        this.messageAR = messageAR;
    }
}
