package hw2.service.auth;

import hw2.entity.Member;
import hw2.entity.Role;
import hw2.repository.MemberRepository;
import hw2.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member createMember(Member member, List<Role> memberRoles) {
        for (Role mr : memberRoles) {
            if (roleRepository.findByRolename(mr.getRolename()).isEmpty()) {
                roleRepository.save(mr);
            }
        }

        // generate new Bcrypt hash
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        member.setRoles(memberRoles);

        Member newMember = memberRepository.save(member);

        return newMember;
    }

    @Override
    public boolean checkEmailExists(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public Role findByRolename(String rolename) {
        Optional<Role> role = roleRepository.findByRolename(rolename);
        return role.orElseGet(() -> new Role(rolename));
    }
}
