package in.vahanmitra.api.booking;

import in.vahanmitra.api.shop.Shop;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="bookings")
public class Booking {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private String referenceCode;
    private String customerName;
    private String customerPhone;
    private String vehicleType;
    private String serviceName;
    private String serviceLocation;
    private String serviceMode;
    private String preferredTime;
    private BigDecimal quotedPrice;
    private String estimatedJobTime;
    private String status;
    private Instant createdAt;
    @ManyToOne(optional=false) private Shop shop;
    protected Booking(){}
    public Booking(String referenceCode,String customerName,String customerPhone,String vehicleType,String serviceName,
                   String serviceLocation,String serviceMode,String preferredTime,BigDecimal quotedPrice,
                   String estimatedJobTime,Shop shop){
        this.referenceCode=referenceCode;this.customerName=customerName;this.customerPhone=customerPhone;
        this.vehicleType=vehicleType;this.serviceName=serviceName;this.serviceLocation=serviceLocation;
        this.serviceMode=serviceMode;this.preferredTime=preferredTime;this.quotedPrice=quotedPrice;
        this.estimatedJobTime=estimatedJobTime;this.shop=shop;this.status="REQUESTED";this.createdAt=Instant.now();
    }
    public String getReferenceCode(){return referenceCode;} public String getCustomerName(){return customerName;}
    public String getVehicleType(){return vehicleType;} public String getServiceName(){return serviceName;}
    public String getServiceLocation(){return serviceLocation;} public String getServiceMode(){return serviceMode;}
    public String getPreferredTime(){return preferredTime;} public BigDecimal getQuotedPrice(){return quotedPrice;}
    public String getEstimatedJobTime(){return estimatedJobTime;} public String getStatus(){return status;}
    public Instant getCreatedAt(){return createdAt;} public Shop getShop(){return shop;}
    public void setStatus(String status){this.status=status;}
}
