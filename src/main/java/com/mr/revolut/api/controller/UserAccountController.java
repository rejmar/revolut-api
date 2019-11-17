package com.mr.revolut.api.controller;

import com.mr.revolut.api.exception.RevolutApiException;
import com.mr.revolut.api.model.UserAccount;
import com.mr.revolut.api.service.UserAccountService;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/userAccount")
@Produces(MediaType.APPLICATION_JSON)
@Log4j2
public class UserAccountController {

    private UserAccountService userAccountService = new UserAccountService();

    @GET
    @Path("/all")
    public Set<UserAccount> getAllUserAccounts() {
        return userAccountService.getAllUsers();
    }

    @GET
    @Path("/{id}")
    public Response findUserAccount(@PathParam("id") long id) {
        try {
            return Response.ok().entity(userAccountService.findUserAccount(id)).build();
        } catch (RevolutApiException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/balance")
    public Response findBalance(@PathParam("id") long id) {
        try {
            return Response.ok().entity(userAccountService.findUserAccount(id).getBalance()).build();
        } catch (RevolutApiException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

//    @POST
//    @Path("/create")
//    public Response createAccount(UserAccount userAccount) {
//
//    }

    @GET
    @Path("/test")
    public String test() {
        return "TEST";
    }
}
