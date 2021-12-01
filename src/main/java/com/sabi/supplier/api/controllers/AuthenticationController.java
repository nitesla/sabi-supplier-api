package com.sabi.supplier.api.controllers;


import com.sabi.framework.dto.requestDto.GeneratePassword;
import com.sabi.framework.dto.requestDto.LoginRequest;
import com.sabi.framework.dto.responseDto.AccessTokenWithUserDetails;
import com.sabi.framework.dto.responseDto.GeneratePasswordResponse;
import com.sabi.framework.dto.responseDto.PartnersCategoryReturn;
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
import com.sabi.supplier.service.repositories.SupplierRepository;
import com.sabi.suppliers.core.models.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
    private final SupplierRepository supplierRepository;


    public AuthenticationController(UserService userService,SupplierRepository supplierRepository) {
        this.userService = userService;
        this.supplierRepository = supplierRepository;
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
//                userService.validateGeneratedPassword(user.getId());

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

        String clientId= "";
        String referralCode="";
        String isEmailVerified="";
        List<PartnersCategoryReturn> partnerCategory= null;
        if (user.getUserCategory().equals(Constants.OTHER_USER)) {
            Supplier supplier = supplierRepository.findByUserId(user.getId());
            if(supplier !=null){
                clientId = String.valueOf(supplier.getId());
            }
        }

        AccessTokenWithUserDetails details = new AccessTokenWithUserDetails(newToken, user,
                accessList,userService.getSessionExpiry(),clientId,referralCode,isEmailVerified,partnerCategory);
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



    @PutMapping("/generatepassword")
    public ResponseEntity<Response> generatePassword(@Validated @RequestBody GeneratePassword request){
        HttpStatus httpCode ;
        Response resp = new Response();
        GeneratePasswordResponse response=userService.generatePassword(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Password generated successfully");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


}
