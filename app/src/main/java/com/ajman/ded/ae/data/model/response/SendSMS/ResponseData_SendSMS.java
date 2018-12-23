package com.ajman.ded.ae.data.model.response.SendSMS;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "SendSMSResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class ResponseData_SendSMS {

    @Element(name = "SendSMSResult", required = false)
    private String SendSMSResult;

    public String getSendSMSResult() {
        return SendSMSResult;
    }

    public void setSendSMSResult(String sendSMSResult) {
        this.SendSMSResult = sendSMSResult;
    }
}
