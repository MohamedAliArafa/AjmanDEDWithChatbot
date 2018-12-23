package com.ajman.ded.ae.data.model.response.InsertNewHappy;


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
public class ResponseEnvelope_InsertNewHappy {

    @Element(required = false, name = "Body")
    public ResponseBody_InsertNewHappy body;

    public ResponseBody_InsertNewHappy getBody() {
        return body;
    }

    public void setBody(ResponseBody_InsertNewHappy body) {
        this.body = body;
    }
}
