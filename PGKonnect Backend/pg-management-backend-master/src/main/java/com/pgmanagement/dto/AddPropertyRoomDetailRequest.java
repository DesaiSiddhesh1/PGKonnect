package com.pgmanagement.dto;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.pgmanagement.entity.PropertyRoomDetail;

import lombok.Data;

@Data
public class AddPropertyRoomDetailRequest {

	private String roomNo;

	private String description;

	private String roomFor; // Boys, Girls, Both

	private String type; // Private or Shared

	private Integer totalBeds; // total beds in the room, applicable for shared rooms

	private BigDecimal privateRoomPrice;

	private BigDecimal sharedRoomOneBedPrice;

	private String sharedBedsNos; // for eg. 101, 102, 103

	private int propertyId;

	public static PropertyRoomDetail toEntity(AddPropertyRoomDetailRequest request) {
		PropertyRoomDetail propertyRoomDetail = new PropertyRoomDetail();
		BeanUtils.copyProperties(request, propertyRoomDetail, "sharedRoomBeds", "sharedBedsNos", "propertyId",
				"property");
		return propertyRoomDetail;
	}

}
