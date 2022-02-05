package org.rumblekat.club.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.rumblekat.club.entity.ClubMember;
import org.rumblekat.club.repository.ClubMemberRepository;
import org.rumblekat.club.security.dto.ClubAuthMemberDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("ClubUserDetailsService loadUserByUserName " + username);

        Optional<ClubMember> result = clubMemberRepository.findByEmail(username,false);
        if(!result.isPresent()){
            throw new UsernameNotFoundException("Check Email or Social");
        }

        ClubMember clubMember = result.get();

        log.info("--------------------------------");
        log.info(clubMember);

        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
            clubMember.getEmail(),
            clubMember.getPassword(),
            clubMember.isFromSocial(),
            clubMember.getRoleSet().stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet())
        );
        clubAuthMember.setName(clubMember.getName());
        clubAuthMember.setFromSocial(clubMember.isFromSocial());

        return clubAuthMember;
    }
}
/*
* 사용자의 정보를 가져오는 핵심적 역할
* AuthenticationManager는 내부적으로 UserDetailsService를 호출해서 사용자의 정보를 가져온다.
*
* TODO: @RequiredArgsConstructor
* 초기화 되지않은 final 필드나 @NonNull이 붙은 필드에 대해 생성자를 생성한다. -> 주로 의존성 주입 편의성을 위해 사용
*
* */
