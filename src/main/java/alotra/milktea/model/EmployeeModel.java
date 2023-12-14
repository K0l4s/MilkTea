package alotra.milktea.model;

import alotra.milktea.entity.Shop;
import alotra.milktea.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeModel {
    private int employeeID;
    private String citizenID;
    private String name;
    private String photo;
    private User username;
    private Shop shop;
    private short status;
    private MultipartFile imageFile;
}
