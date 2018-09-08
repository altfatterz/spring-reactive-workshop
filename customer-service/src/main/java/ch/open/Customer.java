package ch.open;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
class Customer {

    @Id
    private String id;
    private String firstName;
    private String lastName;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}