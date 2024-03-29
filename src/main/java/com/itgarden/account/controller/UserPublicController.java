package com.itgarden.account.controller;

import com.itgarden.account.common.staticdata.TokenType;
import com.itgarden.account.common.staticdata.UserType;
import com.itgarden.account.dto.AuthenticationRequestInfo;
import com.itgarden.account.dto.AuthenticationResponseInfo;
import com.itgarden.account.dto.EmployeeInfo;
import com.itgarden.account.dto.UserRoleInfo;
import com.itgarden.account.entity.JwtToken;
import com.itgarden.account.exception.DuplicateKeyFoundException;
import com.itgarden.account.exception.InvalidTokenException;
import com.itgarden.account.exception.InvalidUserNamePasswordException;
import com.itgarden.account.messages.ResponseMessage;
import com.itgarden.account.security.JwtUtils;
import com.itgarden.account.service.AuthenticationService;
import com.itgarden.account.service.EmployeeService;
import com.itgarden.account.service.RoleService;
import com.itgarden.account.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
 * Created by Suresh Stalin on 17 / Nov / 2020.
 */

@RestController
@RequestMapping("api/public") // http://localhost:9091/api/public/users POST method
public class UserPublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private RoleService roleService;

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseMessage<AuthenticationResponseInfo>>
    authenticate(@RequestBody AuthenticationRequestInfo authenticationRequest) {
        try {
            // This authenticate method call AuthenticationService.loadUserByUsername
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException bce) {
            throw new InvalidUserNamePasswordException(bce.getMessage());
        }
        UserDetails userDetails = authenticationService.loadUserByUsername(authenticationRequest.getUserName());
        AuthenticationResponseInfo authenticationResponseDTO = authenticationService.generateAuthResponse(userDetails, TokenType.ACCESS_TOKEN);
        JwtToken jwtTokenResponse = authenticationService.saveJwt(authenticationResponseDTO);
        if (jwtTokenResponse != null) {
            ResponseMessage responseMessage = ResponseMessage.withResponseData(authenticationResponseDTO, "Authentication Success", "Info");
            return new ResponseEntity<ResponseMessage<AuthenticationResponseInfo>>(responseMessage, HttpStatus.OK);
        } else {
            ResponseMessage responseMessage = ResponseMessage.withResponseData(new AuthenticationResponseInfo(), "Authentication Failure", "Error");
            return new ResponseEntity<ResponseMessage<AuthenticationResponseInfo>>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody AuthenticationRequestInfo authenticationRequest) throws Exception {
        String refreshToken = authenticationRequest.getRefreshToken();
        //Validate the token while extracting user.
        if (jwtUtils.isRefreshTokenExpired(refreshToken)) {
            throw new InvalidTokenException("Invalid Token: The Refresh Token is Expired");
        }
//        jwtUtils.extractUsername(refreshToken,TokenType.REFRESH_TOKEN);
        UserDetails userDetails = authenticationService.loadUserByUsername(jwtUtils.extractUsername(refreshToken, TokenType.REFRESH_TOKEN));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword()));
        } catch (BadCredentialsException bce) {
            throw new InvalidUserNamePasswordException(bce.getMessage());
        }
        AuthenticationResponseInfo authenticationResponseDTO = authenticationService.generateAuthResponse(userDetails, TokenType.REFRESH_TOKEN);
        JwtToken jwtTokenResponse = authenticationService.saveJwt(authenticationResponseDTO);
        ResponseMessage responseMessage = ResponseMessage.withResponseData(authenticationResponseDTO, "Refresh Token Generated", "Message");
        return new ResponseEntity<ResponseMessage<AuthenticationResponseInfo>>(responseMessage, HttpStatus.OK);
    }

    @PostMapping("/superadmin/{id}") // http://localhost:9091/api/public/users/employees
    public ResponseEntity<ResponseMessage<?>> saveEmployee(@Valid @RequestBody EmployeeInfo requestBody, @PathVariable Long id) throws Exception {
        requestBody.setType(UserType.EMPLOYEE.name());
        ResponseMessage responseMessage = roleService.findResourceById(id);
        UserRoleInfo userRoleInfo = (UserRoleInfo) responseMessage.getResponseClassType();
        if (userRoleInfo.getRoleId() ==id) {
            throw new DuplicateKeyFoundException("The Super Admin already created: It can create only once.");
        } else {
            userValidator.validate(requestBody);
            ResponseMessage registrationResponse = employeeService.createSuperAdmin(requestBody);
            return new ResponseEntity<ResponseMessage<?>>(registrationResponse, HttpStatus.CREATED);
        }
    }
}
