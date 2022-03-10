package cd.events.java.sdk.consumer;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.CloudEvent;

@Path("/")
public class EventConsumerResource {

    public static final String CD_ARTIFACT_EVENT_TYPE = "cd.artifact.packaged.v1";

    @Autowired
    ObjectMapper objectMapper;

    @POST
    @Path("event")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response consumeEvent(@RequestBody CloudEvent inputEvent) throws IOException {
    	System.out.println("CloudEvent Type received - " +inputEvent.getType());
    	if (!inputEvent.getType().equals("cd.artifact.packaged.v1")) {
    		return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("Event type should be \"" + CD_ARTIFACT_EVENT_TYPE + "\" but is \"" + inputEvent.getType() + "\"")
                    .build();
        }
    	System.out.println("Received Event with Post");
    	System.out.println("CloudEvent Attributes received - ");
    	for (String att : inputEvent.getAttributeNames()) {
    		System.out.println("Attribute Name -> " + att);
    	}
    	
    	CDEvent data = objectMapper.readValue(inputEvent.getData().toBytes(), CDEvent.class);
    	System.out.println("CDEvent getID --> " +data.getId());
    	System.out.println("CDEvent getSubject --> " +data.getSubject());
    	return Response.ok().build();
    }
    
    @GET
    @Path("event")
    public Response handleGetRequest() {
		return Response.ok("Java-SDK Event Consumer API !!").build();
    	
    }
}
