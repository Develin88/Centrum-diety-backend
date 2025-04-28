package pl.cyrkoniowa.centrumdiety.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cyrkoniowa.centrumdiety.entity.Account;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.userName = account.getUserName();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.email = account.getEmail();
    }
}
