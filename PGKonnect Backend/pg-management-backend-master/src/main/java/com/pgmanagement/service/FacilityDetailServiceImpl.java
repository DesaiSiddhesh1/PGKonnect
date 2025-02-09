package com.pgmanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgmanagement.dao.FacilityDetailDao;
import com.pgmanagement.entity.FacilityDetail;

@Service
public class FacilityDetailServiceImpl implements FacilityDetailService {

	@Autowired
	private FacilityDetailDao facilityDetailDao;

	@Override
	public FacilityDetail addDetail(FacilityDetail facilityDetail) {
		// TODO Auto-generated method stub
		return facilityDetailDao.save(facilityDetail);
	}

	@Override
	public FacilityDetail getDetailById(int facilityId) {
		// TODO Auto-generated method stub
		Optional<FacilityDetail> optional = facilityDetailDao.findById(facilityId);

		if (optional.isEmpty()) {
			return null;
		} else {
			return optional.get();
		}
	}

}
