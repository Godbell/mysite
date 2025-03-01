package mysite.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
public class UserVo {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String gender;
    private String joinDate;
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "UserVo [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
            + gender + ", joinDate=" + joinDate + "]";
    }

    public String getRole() {
        return role != null ? role : null;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
