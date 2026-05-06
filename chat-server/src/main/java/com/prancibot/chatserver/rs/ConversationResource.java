package com.prancibot.chatserver.rs;

import com.prancibot.chatserver.dto.ConversationDTO;
import com.prancibot.chatserver.dto.CreateConversationDTO;
import com.prancibot.chatserver.pagination.PaginationParam;
import com.prancibot.chatserver.service.ConversationStoreService;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("conversations")
public class ConversationResource {
    private final ConversationStoreService service;
    @Context
    private UriInfo uriInfo;

    public ConversationResource(ConversationStoreService service) {
        this.service = service;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createConversation(CreateConversationDTO dto) {
        ConversationDTO conversation = service.create(dto);
        return Response.created(URI.create("/conversations/" + conversation.getId())).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchConversation(@QueryParam("name") String name, @BeanParam PaginationParam pagination) {
        if (name == null || name.isBlank()) {
            return Response.ok(List.of()).build();
        }
        List<ConversationDTO> match = service.searchByName(name, pagination);
        return Response.ok(match).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllConversation(@BeanParam PaginationParam pagination) {
        List<ConversationDTO> match = service.getAllConversation(pagination);
        return Response.ok(match).build();
    }
}
