package org.ecommerce.controller;

import org.ecommerce.entitymodel.User;
import org.ecommerce.repository.UserRepository;
import org.ecommerce.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		// Encrypt the password before saving
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// Save user to the database
		userRepository.save(user);
		return ResponseEntity.ok("User registered successfully.");
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody User user) {
		// Find user by username
		User existingUser = userRepository.findByUsername(user.getUsername());

		if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
			// Generate JWT token
			String token = jwtUtil.generateToken(user.getUsername());
			return ResponseEntity.ok(token);
		}

		// Invalid credentials
		return ResponseEntity.status(401).body("Invalid credentials.");
	}

//    @PostMapping("/login")
//    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
//        User existingUser = userRepository.findByUsername(user.getUsername());
//        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
//            String token = jwtUtil.generateToken(user.getUsername());
//            Map<String, String> response = new HashMap<>();
//            response.put("token", token);
//            return ResponseEntity.ok(response);
//        }
//        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
//    }

	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam String username, @RequestParam String newPassword) {
		// Find user by username
		User user = userRepository.findByUsername(username);

		if (user != null) {
			// Update password
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
			return ResponseEntity.ok("Password updated successfully.");
		}

		// User not found
		return ResponseEntity.status(404).body("User not found.");
	}
}
