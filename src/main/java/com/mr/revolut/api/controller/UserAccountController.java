package com.mr.revolut.api.controller;

import com.mr.revolut.api.database.DummyDB;
import com.mr.revolut.api.dto.UserAccountDTO;
import com.mr.revolut.api.exception.RevolutApiException;
import com.mr.revolut.api.model.UserAccount;
import com.mr.revolut.api.service.UserAccountService;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/userAccount")
@Produces(MediaType.APPLICATION_JSON)
@Log4j2
public class UserAccountController {

    private UserAccountService userAccountService = new UserAccountService();

    @GET
    @Path("/all")
    public Map<Long, UserAccount> getAllUserAccounts() {
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

    @GET
    @Path("{id}/transactions")
    public List getAccountTransactions(long accountId) {
        return DummyDB.getUserAccounts().get(accountId).getTransactions();
    }

    @POST
    @Path("/create")
    public Response createAccount(UserAccountDTO userAccountDTO) {
        UserAccount account = null;
        try {
            log.info("Creating new user with balance = " + userAccountDTO.getBalance());
            account = userAccountService.createUserAccount(userAccountDTO.getBalance());
            log.info("New User created" + account);
            return Response.ok().entity(account).build();
        } catch (RevolutApiException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/withdraw/{amount}")
    public Response withdraw(@PathParam("id") long userId, @PathParam("amount") String amount) {
        try {
            UserAccount userAccount = userAccountService.findUserAccount(userId);
            userAccountService.withdraw(userAccount, amount);
            return Response.ok().entity(userAccount).build();
        } catch (RevolutApiException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/deposit/{amount}")
    public Response deposit(@PathParam("id") long userId, @PathParam("amount") String amount) throws RevolutApiException {
        try {
            UserAccount userAccount = userAccountService.findUserAccount(userId);
            userAccountService.deposit(userAccount, amount);
            return Response.ok().entity(userAccount).build();
        } catch (RevolutApiException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long userId) {
        try {
            return Response.ok().entity(userAccountService.deleteUser(userId)).build();
        } catch (RevolutApiException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
