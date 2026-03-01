package dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Contact {
     private String id;
     private String name;
     private String lastName;
     private String email;
     private String phone;
     private String address;
     @EqualsAndHashCode.Exclude
     private String description;
}


