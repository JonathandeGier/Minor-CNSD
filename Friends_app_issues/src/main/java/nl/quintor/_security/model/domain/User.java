package nl.quintor._security.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.quintor._security.model.exception.PostcodeException;
import nl.quintor._security.security.Role;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(exclude = "friends")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;
    private String postcode;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<User> friends;

    public User(long id, String username, String password, String postcode, Role role, Address address, Collection<User> friends) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.address = address;
        this.friends = friends;
        try {
            this.postcode = validPostcode(postcode);
        } catch (PostcodeException e) {
            e.printStackTrace();
        }
    }

    public String validPostcode(final String postcode) throws PostcodeException {
        final String postcodeNL = "^[0-9]{4}\\s*[a-zA-Z]{2}$";

        if (postcode.matches(postcodeNL)) {
            return postcode;
        } else {
            throw new PostcodeException("postcode invalid");
        }
    }

}