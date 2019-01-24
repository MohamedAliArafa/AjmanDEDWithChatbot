package com.ajman.ded.ae.data.model.request.UserId;


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
public class RequestEnvelope_UserId {

    @Element(name = "soap12:Body", required = false)
    private RequestBody_UserId body;

    public RequestBody_UserId getBody() {
        return body;
    }

    public void setBody(RequestBody_UserId body) {
        this.body = body;
    }
}
