package com.prancibot.rs;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/healthz")
public class HealthResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHealth() {
        return "ok";
    }
}
