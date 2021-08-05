package net.epiclanka.training.model;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;

public class Customer {
    @Id
    private long id;
    @NotBlank
    @Pattern(regexp = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$", message = "Incorrect NIC Value")
    private String nic;
    @NotBlank
    @Pattern(regexp = "[A-Za-z]*", message = "Incorrect Name")
    private String name;
    @NotBlank
    @Pattern(regexp = "^(.+)@(.+)$", message = "Incorrect Email")
    private String email;
    @NotBlank
    @Pattern(regexp = "^[+]*[(]{0,2}[0-9]{2,4}[)]{0,1}[-\\s\\./0-9]*$", message = "Incorrect Phone number")
    private String phoneNumber;
    @Min(value=10, message="must be equal or greater than 10")
    @Max(value=45, message="must be equal or less than 45")
    private int age;

    public Customer() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
