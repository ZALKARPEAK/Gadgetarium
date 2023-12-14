package com.example.gadegetarium.api;

import com.example.gadegetarium.dto.SimpleResponse;
import com.example.gadegetarium.dto.User.PaginationResponse;
import com.example.gadegetarium.dto.User.UserRequest;
import com.example.gadegetarium.dto.User.UserRequestInfo;
import com.example.gadegetarium.dto.User.UserResponseInfo;
import com.example.gadegetarium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/All")
    public PaginationResponse getAll(@RequestParam int page,@RequestParam int size){
        return userService.getAllUsers(page, size);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/FindByEmail")
    public UserResponseInfo findUserByEmail(UserRequest request){
        return userService.getUserById(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PatchMapping("/Update")
    public ResponseEntity<SimpleResponse> update(UserRequestInfo requestInfo){
        SimpleResponse simpleResponse= userService.updateUserByEmailAndPassword(requestInfo);
        return new ResponseEntity<>(simpleResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/Delete")
    private ResponseEntity<SimpleResponse> delete(@RequestParam Long id){
        SimpleResponse simpleResponse = userService.deleteUserById(id);
        return new ResponseEntity<>(simpleResponse, HttpStatus.OK);
    }
}
