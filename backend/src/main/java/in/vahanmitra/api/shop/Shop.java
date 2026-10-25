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
    private Double latitude;
    private Double longitude;
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
        double[] coordinates = defaultCoordinates();
        this.latitude=coordinates[0]; this.longitude=coordinates[1];
    }

    public void addService(ServiceOffering offering) { services.add(offering); offering.setShop(this); }
    public void removeService(ServiceOffering offering) { services.remove(offering); offering.setShop(null); }
    public String getId(){return id;} public String getName(){return name;} public String getTag(){return tag;}
    public String getKind(){return kind;} public double getRating(){return rating;} public String getReviews(){return reviews;}
    public String getDistance(){return distance;} public String getAddress(){return address;} public String getImageKey(){return imageKey;}
    public String getIcon(){return icon;} public String getOffer(){return offer;} public String getEstimatedTime(){return estimatedTime;}
    public Double getLatitude(){return latitude!=null?latitude:defaultCoordinates()[0];} public Double getLongitude(){return longitude!=null?longitude:defaultCoordinates()[1];}
    private double[] defaultCoordinates(){return switch(id){case "sharma-auto-care"->new double[]{28.5245,77.2066};case "gurugram-motor-works"->new double[]{28.4698,77.0639};case "noida-bike-seva"->new double[]{28.5708,77.3261};case "ncr-roadside-sahayata"->new double[]{28.6139,77.2090};case "citytune-auto-studio"->new double[]{28.5677,77.2433};case "pitstop-garage"->new double[]{28.5285,77.2066};case "south-delhi-car-clinic"->new double[]{28.5494,77.2001};case "rapid-ride-bike-point"->new double[]{28.5921,77.0460};case "gearup-two-wheeler-care"->new double[]{28.6422,77.1206};case "highway-truck-assist"->new double[]{28.3515,76.9398};default->new double[]{28.6139,77.2090};};}
    public boolean isActive(){return active;} public List<String> getServiceAreas(){return serviceAreas;}
    public List<ServiceOffering> getServices(){return services;}
}
