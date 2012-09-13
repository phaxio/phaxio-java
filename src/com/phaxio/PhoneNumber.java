package com.phaxio;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.phaxio.exception.PhaxioException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneNumber {
    String number;
    String city;
    String state;
    int cost;
    Date lastBilledAt;
    Date provisionedAt;

    public void release(String number) throws PhaxioException {
        Map<String, Object> options = new HashMap<String,Object>();
        options.put("number", number);
        Phaxio.doRequest("releaseNumber", options, "POST");
    }

    public void release(PhoneNumber phoneNumber) throws PhaxioException {
        release(phoneNumber.getNumber());
    }

    public PhoneNumber provision(String areaCode) throws PhaxioException {
        return provision(areaCode, null);
    }

    public PhoneNumber provision(String areaCode, String callbackUrl) throws PhaxioException {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("area_code", areaCode);

        if (callbackUrl != null){
            options.put("callback_url", callbackUrl);
        }

        JsonObject result = Phaxio.doRequest("provisionNumber", options, "POST");
        PhoneNumber newNumber = new PhoneNumber();
        newNumber.mapJsonToSelf(result.get("data").getAsJsonObject());
        return newNumber;
    }

    public List list(Map<String, Object> options) throws PhaxioException {
        if (options == null) options = new HashMap<String, Object>();

        JsonObject result = Phaxio.doRequest("numberList ", options, "POST");
        JsonArray phoneNumberArray = result.get("data").getAsJsonArray();
        List list = new ArrayList<PhoneNumber>();

        for(JsonElement element : phoneNumberArray){
            PhoneNumber number = new PhoneNumber();
            number.mapJsonToSelf(element.getAsJsonObject());
            list.add(number);
        }

        return list;
    }

    void mapJsonToSelf(JsonObject object) throws PhaxioException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.number = object.get("number").getAsString();
        this.city = object.get("city").getAsString();
        this.state = object.get("state").getAsString();
        this.cost = object.get("cost").getAsInt();

        try {
            this.provisionedAt = sdf.parse("provisioned_at");
            this.lastBilledAt = sdf.parse("last_billed_at");
        }
        catch (ParseException e){
            throw new PhaxioException("Error parsing response from service", e);
        }
    }

    public String getCity() {
        return city;
    }

    public int getCost() {
        return cost;
    }

    public Date getLastBilledAt() {
        return lastBilledAt;
    }

    public String getNumber() {
        return number;
    }

    public Date getProvisionedAt() {
        return provisionedAt;
    }

    public String getState() {
        return state;
    }


}
