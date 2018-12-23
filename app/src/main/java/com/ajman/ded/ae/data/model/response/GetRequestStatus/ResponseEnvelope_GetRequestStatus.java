package com.ajman.ded.ae.data.model.response.GetRequestStatus;

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
public class ResponseEnvelope_GetRequestStatus {

    @Element(required = false, name = "Body")
    public ResponseBody_GetRequestStatus body;

    public ResponseBody_GetRequestStatus getBody() {
        return body;
    }

    public void setBody(ResponseBody_GetRequestStatus body) {
        this.body = body;
    }
}
