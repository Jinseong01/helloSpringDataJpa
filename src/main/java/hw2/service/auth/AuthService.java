package hw2.service.auth;

import hw2.entity.Member;
import hw2.entity.Role;

import java.util.List;

public interface AuthService {
    Member createMember(Member member, List<Role> memberRoles);

    boolean checkEmailExists(String email);

    Role findByRolename(String rolename);
}
