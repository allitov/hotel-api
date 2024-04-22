package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.service.UserService;
import com.allitov.hotelapi.web.dto.request.UserRequest;
import com.allitov.hotelapi.web.dto.response.UserListResponse;
import com.allitov.hotelapi.web.dto.response.UserResponse;
import com.allitov.hotelapi.web.mapping.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll() {
        log.info("Find all users");

        return ResponseEntity.ok(userMapper.entityListToListResponse(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") Integer id) {
        log.info("Find by id request with id = '{}'", id);

        return ResponseEntity.ok(userMapper.entityToResponse(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserRequest request) {
        log.info("Create request with body = '{}'", request);

        return ResponseEntity.created(
                URI.create("/api/v1/user/" + userService.create(userMapper.requestToEntity(request)).getId())
        ).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable("id") Integer id, @RequestBody UserRequest request) {
        log.info("Update by id request with id = '{}' and body = '{}'", id, request);
        userService.updateById(id, userMapper.requestToEntity(request));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        log.info("Delete by id request with id = '{}'", id);
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
