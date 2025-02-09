package com.pgmanagement.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String description;

	private String address;

	private String image;

	@OneToMany(mappedBy = "property", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<FacilityDetail> facilities = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@OneToMany(mappedBy = "property", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<PropertyRoomDetail> rooms = new ArrayList<>();

	private String status;

}
