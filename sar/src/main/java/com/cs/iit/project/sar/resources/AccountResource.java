package com.cs.iit.project.sar.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.cs.iit.project.sar.dto.request.AccountRequest;
import com.cs.iit.project.sar.dto.request.RateAccountRequest;
import com.cs.iit.project.sar.dto.response.AccountResponse;
import com.cs.iit.project.sar.dto.response.CreateAccountAidResponse;
import com.cs.iit.project.sar.dto.response.RateAccountSidResponse;
import com.cs.iit.project.sar.dto.response.ViewDriverRatingsResponse;
import com.cs.iit.project.sar.dto.response.ViewRiderRatingsResponse;
import com.cs.iit.project.sar.services.AccountService;
import com.cs.iit.project.sar.utilities.Location;

@Path("accounts")
public class AccountResource {

	AccountService repo = new AccountService();

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAccount(AccountRequest userRequest, @Context UriInfo uriInfo) {
		CreateAccountAidResponse aidResponse = repo.createAccount(userRequest);
		String aidStr = String.valueOf(aidResponse.getAid());
		
		return Response.created(Location.getUri(uriInfo, aidStr))
				.status(Status.CREATED)
				.entity(aidResponse)
				.build();
	}
	
	@Path("{aid}/status")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void activeAccount(@PathParam("aid") int aid, AccountRequest user) {
		user.setAid(aid);
		repo.activateAccount(aid, user);
	}
	
	@Path("{aid}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateAccount(@PathParam("aid") Integer aid, AccountRequest user) {
		user.setAid(aid);
		repo.updateAccount(aid, user);
	}
	
	@Path("{aid}")
	@DELETE
	public void deleteAccount(@PathParam("aid") int aid) {
		repo.deleteAccount(aid);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AccountResponse> viewAllAccounts() {
		return repo.viewAllAccounts();
	}
	
	@GET 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<AccountResponse> searchAccounts(@QueryParam("key") String key) {
		if(key == null || key.isBlank()) {
			return repo.viewAllAccounts();
		}
		return repo.searchAccounts(key);
	}
	
	@Path("{aid}/ratings")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response rateAccount(@PathParam("aid") int aid, RateAccountRequest rating, @Context UriInfo uriInfo) {
		
		RateAccountSidResponse sidResponse = repo.rateAccount(aid, rating);
		String sidStr = String.valueOf(sidResponse);
		
		return Response.created(Location.getUri(uriInfo, sidStr))
				.status(Status.CREATED)
				.entity(sidResponse)
				.build();
	}
	
	@Path("{aid}/driver")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ViewDriverRatingsResponse viewDriverRatings(@PathParam("aid") int aid) {
		return repo.viewDriverRatings(aid);
	}
	
	@Path("{aid}/rider")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ViewRiderRatingsResponse viewRiderRatings(@PathParam("aid") int aid) {
		return repo.viewRiderRatings(aid);
	}
}
