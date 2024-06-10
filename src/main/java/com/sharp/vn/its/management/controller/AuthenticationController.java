package com.sharp.vn.its.management.controller;

import com.sharp.vn.its.management.dto.ResponseData;
import com.sharp.vn.its.management.dto.UserDTO;
import com.sharp.vn.its.management.repositories.UserRepository;
import com.sharp.vn.its.management.security.UserSecurityDetails;
import com.sharp.vn.its.management.service.UserManagementService;
import com.sharp.vn.its.management.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * The type Authentication controller.
 */
@RestController()
@RequestMapping(value = "/auth")
public class AuthenticationController {

    /**
     * The Service.
     */
    @Autowired
    private UserManagementService service;

    /**
     * The Authentication manager.
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * The User repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * The Jwt utils.
     */
    @Autowired
    private JwtUtil jwtUtils;



    /**
     * Register user response api.
     *
     * @param request the request
     * @return the response api
     */
    @PostMapping("/signup")
    public ResponseData<?> registerUser(@Valid @RequestBody UserDTO request) {
        service.saveUser(request);
        return new ResponseData<>(Boolean.TRUE);
    }

    /**
     * Authenticate user response api.
     *
     * @param loginRequest the login request
     * @return the response api
     */
    @PostMapping("/login")
    public ResponseData<?> authenticateUser(@RequestBody UserDTO loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserSecurityDetails userDetails = (UserSecurityDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        String userName = userDetails.getUsername();
        String jwt = jwtUtils.generateToken(userDetails);
        return new ResponseData<>(new UserDTO(userDetails.getUsername(), jwt));
    }


}
