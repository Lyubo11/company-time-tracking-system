package com.company.timecompany.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    @Column(unique = true)
    private String username;
    @Column(length = 64, nullable = false)
    private String password;
    @Size(min = 2, max = 50, message = "First name must be between 2 and  50 characters")
    private String firstName;
    @Size(min = 2, max = 50, message = "Last name must be between 2 and  50 characters")
    @Column(length = 50, nullable = false)
    private String lastName;
    @PastOrPresent(message = "The creation date must be in the past or present")
    private Date createdAt;
    @OneToMany(mappedBy = "user")
    private List<Project> projects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @NotEmpty(message = "Please select at least one role")
    private Set<Role> roles = new HashSet<>();

    private boolean enabled;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public User(String username, boolean enabled) {
        this.username = username;
        this.enabled = enabled;
    }

    public User(String username) {
        this.username = username;
    }

    public User(String user, String s, String ivan, String ivanov) {
        this.username = user;
        this.password = s;
        this.firstName = ivan;
        this.lastName = ivanov;
    }

    public boolean isAdmin() {
        for (Role role : getRoles()) {
            if (role.getName().equalsIgnoreCase("admin")) {
                return true;
            }
        }

        return false;
    }
}
