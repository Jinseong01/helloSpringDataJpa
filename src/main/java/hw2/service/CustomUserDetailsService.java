package hw2.service;

import hw2.entity.Member;
import hw2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Email: " + userName + "인 사용자를 찾을 수 없습니다."));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .authorities(getAuthorities(member))
                .build()
                ;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Member member) {
        String[] memberRoles = member.getRoles()
                .stream()
                .map((role) -> role.getRolename())
                .toArray(String[]::new);

        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(memberRoles);
        return authorities;
    }
}
