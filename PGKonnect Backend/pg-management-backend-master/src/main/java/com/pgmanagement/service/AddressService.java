package com.pgmanagement.service;

import com.pgmanagement.entity.Address;

public interface AddressService {
	
	Address addAddress(Address address);
	
	Address updateAddress(Address address);
	
	Address getAddressById(int addressId);

}
