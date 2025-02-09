package com.pgmanagement.service;

import com.pgmanagement.entity.FacilityDetail;

public interface FacilityDetailService {

	FacilityDetail addDetail(FacilityDetail facilityDetail);

	FacilityDetail getDetailById(int facilityId);

}
