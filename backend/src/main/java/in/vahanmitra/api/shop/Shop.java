package in.vahanmitra.api.shop;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shops")
public class Shop {
    @Id private String id;
    @NotBlank private String name;
    private String tag;
    private String kind;
    private double rating;
    private String reviews;
    private String distance;
    private String address;
    private String imageKey;
    private String icon;
    private String offer;
    private String estimatedTime;
    private boolean active = true;

    @ElementCollection
    private List<String> serviceAreas = new ArrayList<>();

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceOffering> services = new ArrayList<>();

    protected Shop() {}

    public Shop(String id, String name, String tag, String kind, double rating, String reviews,
                String distance, String address, String imageKey, String icon, String offer, String estimatedTime,
                List<String> serviceAreas) {
        this.id=id; this.name=name; this.tag=tag; this.kind=kind; this.rating=rating; this.reviews=reviews;
        this.distance=distance; this.address=address; this.imageKey=imageKey; this.icon=icon; this.offer=offer;
        this.estimatedTime=estimatedTime; this.serviceAreas=new ArrayList<>(serviceAreas);
    }

    public void addService(ServiceOffering offering) { services.add(offering); offering.setShop(this); }
    public void removeService(ServiceOffering offering) { services.remove(offering); offering.setShop(null); }
    public String getId(){return id;} public String getName(){return name;} public String getTag(){return tag;}
    public String getKind(){return kind;} public double getRating(){return rating;} public String getReviews(){return reviews;}
    public String getDistance(){return distance;} public String getAddress(){return address;} public String getImageKey(){return imageKey;}
    public String getIcon(){return icon;} public String getOffer(){return offer;} public String getEstimatedTime(){return estimatedTime;}
    public boolean isActive(){return active;} public List<String> getServiceAreas(){return serviceAreas;}
    public List<ServiceOffering> getServices(){return services;}
}
