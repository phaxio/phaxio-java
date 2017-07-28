package com.phaxio.repositories;

import com.phaxio.services.Requests;

public class PublicRepository
{
    public PublicRepository(Requests client)
    {
        areaCode = new AreaCodeRepository(client);
        supportedCountry = new SupportedCountriesRepository(client);
    }

    public final AreaCodeRepository areaCode;
    public final SupportedCountriesRepository supportedCountry;
}
