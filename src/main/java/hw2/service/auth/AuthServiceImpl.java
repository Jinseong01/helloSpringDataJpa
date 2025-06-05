package hw2.service.auth;

import hw2.entity.Member;
import hw2.entity.Role;
import hw2.entity.RoleType;
import hw2.exception.EmailAlreadyExistsException;
import hw2.repository.MemberRepository;
import hw2.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member createMember(Member member) {
        // 1. 이메일 중복 검사
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("이미 존재하는 이메일입니다: " + member.getEmail());
        }

        // 2. 사용자 권한 설정
        List<Role> roles = new ArrayList<>();
        roles.add(getRole(RoleType.ROLE_USER));

        if ("admin@hansung.ac.kr".equals(member.getEmail())) {
            roles.add(getRole(RoleType.ROLE_ADMIN));
        }

        // 3. 비밀번호 암호화 & 저장
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRoles(roles);

        return memberRepository.save(member);
    }

    private Role getRole(RoleType type) {
        return roleRepository.findByRolename(type)
                .orElseThrow(() -> new RuntimeException("Role 없음: " + type));
    }
}
