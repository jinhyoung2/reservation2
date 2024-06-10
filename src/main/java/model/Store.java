package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Store {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String description;
    private String address;


    @ManyToOne
    private User manager;

    @OneToMany(mappedBy = "store")
    private List<Review> reviews;

}