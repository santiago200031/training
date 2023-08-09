package org.villas.service;

import io.micrometer.core.annotation.Timed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.villas.entity.MessageEntity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/messages")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class MessageService {
    private final List<MessageEntity> messages = new ArrayList<>();

    @APIResponse
    @Transactional
    @Path("/send/{content}")
    public Response setMessagesSent(@PathParam("content") String content) {
        MessageEntity message = new MessageEntity();
        message.sentTime = (LocalTime.now());
        message.message = (content);

        messages.add(message);

        persistMessage(message);
        MessageEntity.persist(message);

        return Response.ok(message).build();
    }

    @Timed(value = "message.persistMessage", extraTags = {"messageEntity", "persist"})
    @Transactional
    public void persistMessage(MessageEntity message) {
        MessageEntity.persist(message);
    }

    @GET
    @APIResponse
    @Path("/all")
    public Response getMessages() {
        return Response.ok(this.messages
                .stream()
                .map(m -> m.message)
                .collect(Collectors.toList())
        ).build();
    }
}
