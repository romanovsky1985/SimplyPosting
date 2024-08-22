package my.SimplyPosting.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EntityListeners(AuditingEntityListener.class)
public class UserModel implements UserDetails, BaseEntity {

    //////////////////// Base Fields ////////////////////

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8)
    private String role;

    @Column(length = 32)
    private String firstName;

    @Column(length = 32)
    private String lastName;

    @Column(unique = true, length = 64)
    private String email;

    @Column(unique = true, length = 32)
    private String username;

    @Column(length = 64)
    private String cryptPassword;

    boolean deleted;

    @CreatedDate
    private LocalDate createdAt;

    private LocalDate bannedBefore;

    //////////////////// Methods ////////////////////

    public boolean isBanned() {
        return bannedBefore != null && LocalDate.now().isBefore(bannedBefore);
    }

    //////////////////// UserDetails Implementation ////////////////////

    @Override
    public String getPassword() {
        return cryptPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !deleted;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }
}
