package com.prancibot.lifestyle.rs;

import com.prancibot.lifestyle.services.BioClockRecordService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/bio-clock")
public class BiologicalClockResource {
    private final BioClockRecordService service;

    public BiologicalClockResource(BioClockRecordService service) {
        this.service = service;
    }

    @POST
    @Path("/record/{activity}")
    @Produces(MediaType.APPLICATION_JSON)
    public void recordWakeupOrSleepTime(@PathParam("activity") String activity) {
        service.record(activity);
    }
}
