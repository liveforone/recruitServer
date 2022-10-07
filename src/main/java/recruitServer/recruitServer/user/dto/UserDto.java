package recruitServer.recruitServer.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import recruitServer.recruitServer.user.domain.Role;
import recruitServer.recruitServer.user.domain.Users;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private Role auth;

    public Users toEntity() {
        return Users.builder()
                .id(id)
                .email(email)
                .password(password)
                .auth(auth)
                .build();
    }
}
