package com.ajman.ded.ae.data.model.request.InsertNewHappy;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(name = "InsertNewHappy ", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class RequestData_InsertNewHappy {

    @Element(name = "Happy", required = false)
    private int happy;

    @Element(name = "UnHappy", required = false)
    private int unHappy;

    @Element(name = "Neutral", required = false)
    private int neutral;


    public int getHappy() {
        return happy;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }

    public int getUnHappy() {
        return unHappy;
    }

    public void setUnHappy(int unHappy) {
        this.unHappy = unHappy;
    }

    public int getNeutral() {
        return neutral;
    }

    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }
}
