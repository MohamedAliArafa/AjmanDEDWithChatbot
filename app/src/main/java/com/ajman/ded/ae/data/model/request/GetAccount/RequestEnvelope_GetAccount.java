package com.ajman.ded.ae.data.model.request.GetAccount;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "xsi", reference = "http://www.w3.org/2001/XMLSchema-instance"),
        @Namespace(prefix = "xsd", reference = "http://www.w3.org/2001/XMLSchema"),
        @Namespace(prefix = "soap12", reference = "http://www.w3.org/2003/05/soap-envelope")
})

public class RequestEnvelope_GetAccount {

    @Element(name = "soap12:Body")
    private RequestBody_GetAccount body;

    public RequestBody_GetAccount getBody() {
        return body;
    }

    public void setBody(RequestBody_GetAccount body) {
        this.body = body;
    }
}
