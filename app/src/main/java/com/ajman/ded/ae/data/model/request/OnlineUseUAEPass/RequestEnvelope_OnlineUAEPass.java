package com.ajman.ded.ae.data.model.request.OnlineUseUAEPass;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


@Root(name = "soap:Envelope")
@NamespaceList({
        @Namespace(prefix = "xsi", reference = "http://www.w3.org/2001/XMLSchema-instance"),
        @Namespace(prefix = "xsd", reference = "http://www.w3.org/2001/XMLSchema"),
        @Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})

public class RequestEnvelope_OnlineUAEPass {

    @Element(name = "soap:Body")
    private RequestBody_OnlineUaePass body;

    public RequestBody_OnlineUaePass getBody() {
        return body;
    }

    public void setBody(RequestBody_OnlineUaePass body) {
        this.body = body;
    }
}
