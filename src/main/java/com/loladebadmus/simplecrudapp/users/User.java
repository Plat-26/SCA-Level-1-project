package com.loladebadmus.simplecrudapp.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loladebadmus.simplecrudapp.rentals.Rental;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users",
        uniqueConstraints = { @UniqueConstraint(
                name = "username_unique_constraint",
                columnNames = "name"
        )
})
public class User {
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid"
    )
    @GeneratedValue(
            generator = "user_uuid"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private UUID id;


    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "VARCHAR(25)"
    )
    @NotBlank(message = "Please enter a user name")
    private String name;

    public User(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rental> rentals = new ArrayList<>();

    public User() {
    }

    public void addRental(Rental rental) {
        this.getRentals().add(rental);
    }

    public  void removeRental(Rental rental) {
        this.getRentals().remove(rental);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
