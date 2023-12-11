package alotra.milktea.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordModel {
    private String email;
    private String newPassword;
    private String oldPassword;
    private  String repeatPassword;
    private String code;
}
