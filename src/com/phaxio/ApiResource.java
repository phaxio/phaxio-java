package com.phaxio;

import com.google.gson.JsonObject;

public abstract class ApiResource {

    abstract void mapJsonToSelf(JsonObject object);
}
