package com.pgmanagement.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String bookingId;

	private String bookingTime;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "property_id")
	private Property property;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "guest_id")
	private User guest;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "room_detail_id")
	private PropertyRoomDetail roomDetail;

	private String roomType; // private or shared

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bed_detail")
	private PropertySharedRoomBedDetail bedDetail;

	private BigDecimal amountToPayPerMonth;

	private String startDate; // epoch time

	private String endDate; // epoch time

	private Integer totalStayMonth;

	private String status; // Pending, Approved, Cancel, Reject

	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingPayment> payments;
	
}
