package com.ajman.ded.ae.data.model.response.InsertNewHappy;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "InsertNewHappyResult", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class ResponseData_InsertNewHappy {

    @Element(name = "InsertNewHappyResult", required = false)
    private int InsertNewHappyResult;

    public int getInsertNewHappyResult() {
        return InsertNewHappyResult;
    }

    public void setInsertNewHappyResult(int insertNewHappyResult) {
        InsertNewHappyResult = insertNewHappyResult;
    }
}
