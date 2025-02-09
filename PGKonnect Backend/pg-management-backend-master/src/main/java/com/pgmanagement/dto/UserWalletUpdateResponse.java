package com.pgmanagement.dto;

import com.pgmanagement.pg.RazorPayPaymentRequest;

public class UserWalletUpdateResponse extends CommonApiResponse {

	private RazorPayPaymentRequest razorPayRequest;

	public RazorPayPaymentRequest getRazorPayRequest() {
		return razorPayRequest;
	}

	public void setRazorPayRequest(RazorPayPaymentRequest razorPayRequest) {
		this.razorPayRequest = razorPayRequest;
	}

}
