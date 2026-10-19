package in.vahanmitra.api.shop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Table(name = "service_offerings")
public class ServiceOffering {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank private String name;
    private String description;
    @DecimalMin("1.00") private BigDecimal price;
    private String estimatedTime;
    @ManyToOne(optional = false) private Shop shop;

    protected ServiceOffering() {}
    public ServiceOffering(String name, String description, BigDecimal price, String estimatedTime) {
        this.name=name; this.description=description; this.price=price; this.estimatedTime=estimatedTime;
    }
    public Long getId(){return id;} public String getName(){return name;} public String getDescription(){return description;}
    public BigDecimal getPrice(){return price;} public String getEstimatedTime(){return estimatedTime;} public Shop getShop(){return shop;}
    public void update(String name,String description,BigDecimal price,String estimatedTime){this.name=name;this.description=description;this.price=price;this.estimatedTime=estimatedTime;}
    void setShop(Shop shop){this.shop=shop;}
}
