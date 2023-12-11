package alotra.milktea.model;

import alotra.milktea.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    private int productID;
    private String name;
    private double price;
    private String imageURL;
    private String description;
    private Category category;
    private short status;
    private MultipartFile imageFile;
}
