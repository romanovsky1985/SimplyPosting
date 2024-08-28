package my.SimplyPosting.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
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
    private long id;

    private String role;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String deletedEmail;

    @Column(unique = true)
    private String username;

    private String cryptPassword;

    @CreatedDate
    private LocalDate createdAt;

    boolean deleted;

    private LocalDate bannedBefore;

    //////////////////// Base Relations ////////////////////

    @OneToMany(mappedBy = "author")
    private List<PostModel> posts;

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
