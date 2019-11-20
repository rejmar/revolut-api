package com.mr.revolut.api.controller;

import com.mr.revolut.api.database.DummyDB;
import com.mr.revolut.api.dto.UserTransactionDTO;
import com.mr.revolut.api.exception.RevolutApiException;
import com.mr.revolut.api.service.UserTransactionService;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@Log4j2
public class UserTransactionController {
    private UserTransactionService userTransactionService = new UserTransactionService();

    @GET
    @Path("/all")
    public Map getAllTransactions() {
        return DummyDB.getTransactions();
    }

    @PUT
    @Path("/transfer")
    public Response transfer(UserTransactionDTO userTransactionDTO) {
        try {
            userTransactionService.transfer(userTransactionDTO);
            return Response.ok().entity(userTransactionDTO).build();
        } catch (RevolutApiException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }
}
