package com.cs.iit.sar.services.exceptionchecker.validation;

import java.util.Map;

import com.cs.iit.sar.data.DataClass;
import com.cs.iit.sar.exception.DataNotFoundException;
import com.cs.iit.sar.exception.FieldDataInvalidException;
import com.cs.iit.sar.models.JoinRequest;
import com.cs.iit.sar.models.Ride;
import com.cs.iit.sar.models.User;

public class JoinRequestValidation {

	private static Map<Integer, Ride> ridesMap = DataClass.getRidesMap();
	private static Map<Integer, JoinRequest> joinRequestsMap = DataClass.getJoinRequestsMap();
	private static Map<Integer, User> usersMap = DataClass.getUsersMap();
	
	public static void validatePassengersToJoin(Integer passengers, Integer ridId) {
		Ride rid = ridesMap.get(ridId);
		if(passengers < 0 || passengers > rid.getMaxPassengers()) {
			throw new FieldDataInvalidException("Invalid passengers amount");
		}
	}
	
	public static void validateJid(Integer jid) {
		if(!joinRequestsMap.containsKey(jid)) {
			throw new DataNotFoundException("Join Request does not exist");
		}
	}
	
	public static void validateNullForAid(Integer aid) {
		if(aid == null) {
			throw new FieldDataInvalidException("aid appears to be null");
		}
	}
	
	public static void validateNullForRideConfirmed(Boolean rideConfirmed) {
		if(rideConfirmed == null) {
			throw new FieldDataInvalidException("Invalid value for ride_confirmed");
		}
	}
	
	public static void validateNotNullForRideConfirmed(Boolean rideConfirmed) {
		if(rideConfirmed != null) {
			throw new FieldDataInvalidException("Invalid value for ride_confirmed");
		}
	}
	
	public static void validateNotNullForPickupPassenger(Boolean pickupConfirmed) {
		if(pickupConfirmed != null) {
			throw new FieldDataInvalidException("Invalid value for pickup_confirmed");
		}
	}
	
	public static void validateRideCreator(Integer rid, Integer patchId) {
		Ride ride = ridesMap.get(rid);
		if(ride.getAid() != patchId) {
			throw new FieldDataInvalidException("This account (" + patchId + ") didn't create the ride (" + rid + ")");
		}
	}
	
	public static void validatePickupConfirmed(Boolean pickupConfirmed) {
		if(pickupConfirmed != true) {
			throw new FieldDataInvalidException("Invalid value for pickup_confirmed");
		}
	}
	
	public static void validateRequestor(Integer jid, Integer rid, Integer patchAid) {
		JoinRequest joinRequest = joinRequestsMap.get(jid);
		if(joinRequest.getAid() != patchAid) {
			throw new FieldDataInvalidException("This account (" + patchAid + ") has not requested to join this ride (" + rid + ")");
		}
	}
	
	public static void validateActiveAccount(Integer aid) {
		User user = usersMap.get(aid);
		if(!user.getActive()) {
			throw new FieldDataInvalidException("This account (" + aid + ") is not active, may not create a join ride request.");
		}
	}
}
