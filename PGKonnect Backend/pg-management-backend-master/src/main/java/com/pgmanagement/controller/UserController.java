package com.pgmanagement.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pgmanagement.dto.CommonApiResponse;
import com.pgmanagement.dto.RegisterUserRequestDto;
import com.pgmanagement.dto.ResetPasswordRequestDto;
import com.pgmanagement.dto.UserLoginRequest;
import com.pgmanagement.dto.UserLoginResponse;
import com.pgmanagement.dto.UserResponseDto;
import com.pgmanagement.dto.UserWalletUpdateResponse;
import com.pgmanagement.entity.User;
import com.pgmanagement.pg.RazorPayPaymentResponse;
import com.pgmanagement.resource.UserResource;
import com.pgmanagement.service.EmailService;
import com.pgmanagement.service.UserService;
import com.razorpay.RazorpayException;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserResource userResource;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	private Map<String, String> otpStorage = new HashMap<>();
	
	
	// for guest and owner register
	@PostMapping("register")
	public ResponseEntity<CommonApiResponse> registerUser(@RequestBody RegisterUserRequestDto request) {
		return this.userResource.registerUser(request);
	}


	@PostMapping("login")
	public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
		return userResource.login(userLoginRequest);
	}

	

	@GetMapping("/fetch/user-id")
	public ResponseEntity<UserResponseDto> fetchUserById(@RequestParam("userId") int userId) {
		return userResource.getUserById(userId);
	}

	@PutMapping("update/wallet")
	public ResponseEntity<UserWalletUpdateResponse> createRazorPayOrder(@RequestBody User user)
			throws RazorpayException {
		return userResource.createRazorPayOrder(user);
	}

	@PutMapping("razorpPay/response")
	public ResponseEntity<CommonApiResponse> updateUserWallet(@RequestBody RazorPayPaymentResponse razorPayResponse)
			throws RazorpayException {
		return userResource.handleRazorPayPaymentResponse(razorPayResponse);
	}
	
	
	 @PostMapping("/forgot-password")
	    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
	        String email = request.get("email");
	        User user = userService.getUserByEmailid(email);

	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                 .body(Collections.singletonMap("message", "User not found"));
	        }

	        // Generate OTP
	        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
	        otpStorage.put(email, otp);

	        // Send OTP via Email
	        emailService.sendEmail(email, "Password Reset OTP", "Your OTP is: " + otp);

	        return ResponseEntity.ok(Collections.singletonMap("message", "OTP sent to email"));
	    }
	 
	 @PostMapping("/reset-password")
	 public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestDto request) {
	     String email = request.getEmailId();
	     String otp = request.getOtp();
	     String newPassword = request.getNewPassword();

	     if (!otpStorage.containsKey(email) || !otpStorage.get(email).equals(otp)) {
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                              .body(Collections.singletonMap("message", "Invalid or expired OTP"));
	     }

	     // Fetch user from database
	     User user = userService.getUserByEmailid(email);
	     if (user == null) {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                              .body(Collections.singletonMap("message", "User not found"));
	     }

	     // Encrypt the new password before saving
	     String hashedPassword = passwordEncoder.encode(newPassword);
	     user.setPassword(hashedPassword);
	     userService.updateUser(user);

	     // Remove OTP after successful password reset
	     otpStorage.remove(email);

	     return ResponseEntity.ok(Collections.singletonMap("message", "Password updated successfully"));
	 }

}
