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
public class PropertyRoomDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String roomNo;

	private String description;

	private String roomFor; // Boys, Girls, Both

	private String type; // Private or Shared

	private Integer totalBeds; // total beds in the room, applicable for shared rooms

	private BigDecimal privateRoomPrice;

	private BigDecimal sharedRoomOneBedPrice;

	@OneToMany(mappedBy = "roomDetail", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<PropertySharedRoomBedDetail> sharedRoomBeds = new ArrayList<>();
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

}
