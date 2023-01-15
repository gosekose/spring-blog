package blog.exception.service;

import blog.exception.controller.dto.RegisterDto;
import blog.exception.domain.Member;
import blog.exception.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean save(RegisterDto registerDto) {

        Member findMember = memberRepository.findMemberByEmail(registerDto.getEmail());

        if (findMember == null) {
            Member saveMember = memberRepository.save(new Member(registerDto.getEmail(), registerDto.getPassword()));
            return true;
        }
        return false;
    }


}
