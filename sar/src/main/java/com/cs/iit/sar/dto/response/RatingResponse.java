package com.cs.iit.sar.dto.response;

import javax.json.bind.annotation.JsonbProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingResponse {
	private Integer rid;
	@JsonbProperty("sent_by_id")
	private Integer sentById;
	@JsonbProperty("first_name")
	private String firstName;
	private String date;
	private Integer rating;
	private String comment;
}
