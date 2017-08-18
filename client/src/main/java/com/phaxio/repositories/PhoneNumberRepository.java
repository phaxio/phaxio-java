package com.phaxio.repositories;

import com.phaxio.services.Requests;
import com.phaxio.resources.PhoneNumber;
import com.phaxio.restclient.RestClient;
import com.phaxio.restclient.entities.RestRequest;

import java.util.HashMap;
import java.util.Map;

public class PhoneNumberRepository
{
    private Requests client;

    public PhoneNumberRepository(Requests client)
    {
        this.client = client;
    }

    /**
     * Provisions a new fax number.
     * @param countryCode The country code to provsion the number in.
     * @param areaCode The area code to provsion the number in.
     * @return A PhoneNumber object representing the new number.
     */
    public PhoneNumber create(String countryCode, String areaCode) {
        return create(countryCode, areaCode, null);
    }

    /**
     * Provisions a new fax number.
     * @param countryCode The country code to provsion the number in.
     * @param areaCode The area code to provsion the number in.
     * @param callbackUrl The URL that Phaxio will post to when a fax is recieved at this number.
     * @return A PhoneNumber object representing the new number.
     */
    public PhoneNumber create(String countryCode, String areaCode, String callbackUrl) {
        RestRequest request = new RestRequest();
        request.resource = "phone_numbers";

        request.addParameter("country_code", countryCode);
        request.addParameter("area_code", areaCode);

        if (callbackUrl != null)
        {
            request.addParameter("callback_url", callbackUrl);
        }

        return client.post(request, PhoneNumber.class);
    }

    /**
     * Gets the information for a phone number
     * @param number The number to retrieve
     * @return A PhoneNumber object
     */
    public PhoneNumber retrieve(String number)
    {
        RestRequest request = new RestRequest();

        request.resource = "phone_numbers/" + RestClient.escape(number);

        return client.get(request, PhoneNumber.class);
    }

    /**
     * Lists the phone numbers provisioned to your account.
     * @return A Iterable of PhoneNumbers
     */
    public Iterable<PhoneNumber> list()
    {
        return list(new HashMap<String, Object>());
    }

    /**
     * Lists the phone numbers provisioned to your account.
     * @param filters The filters to apply (see the API documentation for parameters)
     * @return A Iterable of PhoneNumbers
     */
    public Iterable<PhoneNumber> list(Map<String, Object> filters)
    {
        RestRequest request = new RestRequest();
        request.resource = "phone_numbers";

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            request.addParameter(entry.getKey(), entry.getValue());
        }

        return client.list(request, PhoneNumber.class);
    }
}
