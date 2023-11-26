package org.finance.producers;

import com.google.gson.Gson;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class GsonProducer {
    @Produces
    public Gson createGson() {
        return new Gson();
    }
}