package hw2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "상품명을 입력해주세요.")
    private String name;

    @NotBlank(message = "브랜드를 입력해주세요.")
    private String brand;

    @NotBlank(message = "제조국을 입력해주세요.")
    private String madeIn;

    @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
    @NotNull(message = "가격을 입력해주세요.")
    private Double price = 0.0;

    public Product(String name, String brand, String madeIn, Double price) {
        this.name = name;
        this.brand = brand;
        this.madeIn = madeIn;
        this.price = price;
    }
}