package com.prancibot.lifestyle.exceptionHandler;

import com.prancibot.common.dto.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        int statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        String currentTime = ZonedDateTime.now(ZoneId.systemDefault()).toString();
        return Response.status(statusCode)
                .entity(new ExceptionResponse(exception.getMessage(), statusCode, currentTime))
                .build();
    }
}