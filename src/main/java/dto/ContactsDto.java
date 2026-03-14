package dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class ContactsDto {
    private List<Contact> contacts;
}
