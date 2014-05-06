package com.phaxio;

import com.google.gson.JsonObject;
import com.phaxio.status.FaxRecipientStatus;
import com.phaxio.status.FaxStatus;
import java.util.Date;

public class FaxRecipient {

    private String phoneNumber;
    private FaxRecipientStatus status;
    private Date completedAt;

    public Date getCompletedAt() {
        return completedAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public FaxRecipientStatus getStatus() {
        return status;
    }

    void mapJsonToSelf(JsonObject object){
        if (object.has("number")) this.phoneNumber = object.get("number").getAsString();
        if (object.has("status")) this.status = FaxRecipientStatus.fromApiName(object.get("status").getAsString());
        if (object.has("completed_at")) this.completedAt = new Date(object.get("completed_at").getAsLong() * 1000);
    }
}
