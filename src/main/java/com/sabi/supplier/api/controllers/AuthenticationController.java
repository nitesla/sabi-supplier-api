package com.sabi.supplier.api.controllers;


import com.sabi.framework.dto.requestDto.LoginRequest;
import com.sabi.framework.dto.responseDto.AccessTokenWithUserDetails;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.exceptions.LockedException;
import com.sabi.framework.exceptions.UnauthorizedException;
import com.sabi.framework.loggers.LoggerUtil;
import com.sabi.framework.models.User;
import com.sabi.framework.security.AuthenticationWithToken;
import com.sabi.framework.service.ExternalTokenService;
import com.sabi.framework.service.TokenService;
import com.sabi.framework.service.UserService;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.framework.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"authenticate")
public class AuthenticationController {
    private  static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Value("${login.attempts}")
    private int loginAttempts;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ExternalTokenService externalTokenService;

    private final UserService userService;


    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest request)  {

        log.info(":::::::::: login Request %s:::::::::::::" + loginRequest.getUsername());
        String loginStatus;
        String ipAddress = Utility.getClientIp(request);
        User user = userService.loginUser(loginRequest);
        if (user != null) {
            if (user.isLoginStatus()) {
                //FIRST TIME LOGIN
                if (user.getPasswordChangedOn() == null || user.getIsActive()==false) {
                    Response resp = new Response();
                    resp.setCode(CustomResponseCode.CHANGE_P_REQUIRED);
                    resp.setDescription("Change password Required, account has not been activated");
                    return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);//202
                }
                if (user.getIsActive()==false) {
                    Response resp = new Response();
                    resp.setCode(CustomResponseCode.FAILED);
                    resp.setDescription("User Account Deactivated, please contact Administrator");
                    return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
                }
                if (user.getLoginAttempts() >= loginAttempts || user.getLockedDate() != null) {
                    // lock account after x failed attempts or locked date is not null
                    userService.lockLogin(user.getId());
                    throw new LockedException(CustomResponseCode.LOCKED_EXCEPTION, "Your account has been locked, kindly contact System Administrator");
                }
            } else {
                //update login failed count and failed login date
                loginStatus = "failed";
                userService.updateFailedLogin(user.getId());
                throw new UnauthorizedException(CustomResponseCode.UNAUTHORIZED, "Invalid Login details.");
            }
        } else {
            //NO NEED TO update login failed count and failed login date SINCE IT DOES NOT EXIST
            throw new UnauthorizedException(CustomResponseCode.UNAUTHORIZED, "Login details does not exist");
        }
        //String accessList = roleService.getPermissionsByUserId(user.getId());
        String accessList = "";
        AuthenticationWithToken authWithToken = new AuthenticationWithToken(user, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,"+accessList));
        String newToken = "Bearer" +" "+this.tokenService.generateNewToken();
        authWithToken.setToken(newToken);
        tokenService.store(newToken, authWithToken);
        SecurityContextHolder.getContext().setAuthentication(authWithToken);
        userService.updateLogin(user.getId());

        String agentId= "";
        String referralCode="";
        String isEmailVerified="";
        String partnersCategory="";

        AccessTokenWithUserDetails details = new AccessTokenWithUserDetails(newToken, user,
                accessList,userService.getSessionExpiry(),agentId,referralCode,isEmailVerified,partnersCategory);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }





    @PostMapping("/logout")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean logout() {
        try {
            AuthenticationWithToken auth = (AuthenticationWithToken) SecurityContextHolder.getContext().getAuthentication();
            return tokenService.remove(auth.getToken());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            LoggerUtil.logError(logger, ex);
        }
        return false;
    }




    @PostMapping("/externaltoken")
    public void externalToken() throws Exception {
       externalTokenService.externalTokenRequest();
    }




}
