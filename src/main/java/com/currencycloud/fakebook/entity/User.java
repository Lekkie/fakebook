package com.currencycloud.fakebook.entity;

import com.currencycloud.fakebook.entity.BaseModel;
import com.currencycloud.fakebook.entity.Role;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_users")
public class User  extends BaseModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;
    @Column
    private String username;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String password;
    @Column
    @ElementCollection(targetClass=Role.class)
    private Set<Role> roles;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
