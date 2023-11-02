package com.cstore.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/api/v1/users"})
public class RegUserController {
    private final RegUserService regUserService;

    @RequestMapping(
        method = RequestMethod.GET,
        path = "{user_id}"
    )
    public RegUser getAllUserDetails(
        @PathVariable(
            name = "user_id",
            required = true
        )
        Long userId
    ) {
        return regUserService.getAllUserDetails(userId);
    }
}
