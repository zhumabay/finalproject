package kz.bitlab.bootcamp.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String email;
    private String fullName;
    private String avatarUrl;
    private boolean isOnline;
    private Timestamp exitTime;
    private List<RoleDto> roles;
}
