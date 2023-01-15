package blog.exception.controller;

import blog.exception.controller.dto.RegisterDto;
import blog.exception.exception.exception.BindingInvalidException;
import blog.exception.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException();
        }

        if (!memberService.save(registerDto)) {
            throw new IllegalArgumentException();
        }

        return new ResponseEntity(HttpStatus.OK);

    }

}
