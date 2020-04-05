package kaa.covid19.informer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Person {
    @Id
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
}
