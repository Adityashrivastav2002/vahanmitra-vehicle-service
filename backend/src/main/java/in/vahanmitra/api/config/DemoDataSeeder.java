package in.vahanmitra.api.config;

import in.vahanmitra.api.shop.ServiceOffering;
import in.vahanmitra.api.shop.Shop;
import in.vahanmitra.api.shop.ShopRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoDataSeeder {
    @Bean
    CommandLineRunner seedShops(ShopRepository shops) {
        return args -> {
            if (shops.existsById("sharma-auto-care")) return;
            List<Shop> demoShops=List.of(
                shop("sharma-auto-care","Sharma Auto Care","Your neighbourhood garage","Multi-brand car workshop",4.9,"1.2k","1.2 km","Saket, South Delhi","garage","S","Free 20-point inspection","2–3 hrs",List.of("Delhi"),List.of(
                    service("Periodic car service","Routine check-up, oil & filters",799,"2–3 hrs"),service("AC inspection & gas refill","Cooling check and AC service",1199,"2–4 hrs"),service("Doorstep diagnostics","On-site scan for warning lights",499,"45–60 min"),service("Battery health check","Testing and replacement support",299,"30–45 min"),service("Brake service","Pads, fluid and safety inspection",899,"1–2 hrs"))),
                shop("gurugram-motor-works","Gurugram Motor Works","Premium car care, close by","Luxury & multi-brand car workshop",4.8,"864","2.4 km","Sector 29, Gurugram","car","G","₹300 off your first visit","3–4 hrs",List.of("Gurugram"),List.of(
                    service("Premium periodic service","Synthetic oil, filters & inspection",1899,"3–4 hrs"),service("Computer diagnostics","Full system scan and report",799,"45–90 min"),service("Brake inspection & service","Brake pads, discs and fluid check",1499,"2–3 hrs"),service("AC repair","Cooling diagnosis and repair",1599,"2–4 hrs"),service("Pickup & drop","Vehicle collection within Gurugram",399,"45–60 min"))),
                shop("noida-bike-seva","Noida Bike Seva","Fast, fair two-wheeler care","Two-wheeler specialists",4.7,"632","3.1 km","Sector 18, Noida","bike","N","Free chain clean today","45–90 min",List.of("Noida"),List.of(
                    service("Bike periodic service","Oil, chain clean and safety check",399,"45–90 min"),service("Puncture repair","Tubeless puncture and pressure check",149,"20–35 min"),service("Battery check & replacement","Battery test and fitment",699,"30–60 min"),service("Brake & clutch service","Adjustment, pads and cable check",349,"30–60 min"),service("Roadside help","Local two-wheeler breakdown visit",299,"30–45 min"))),
                shop("ncr-roadside-sahayata","NCR Roadside Sahayata","Roadside help across NCR","Heavy vehicle recovery",4.9,"418","4.8 km","Delhi NCR dispatch network","truck","R","Dispatch demo · 24/7","35 min arrival",List.of("Delhi NCR","Delhi","Gurugram","Noida"),List.of(
                    service("Heavy vehicle recovery dispatch","Truck and bus roadside assistance",2499,"35–60 min arrival"),service("Truck tyre replacement","On-site heavy tyre change",1999,"60–90 min"),service("Breakdown inspection","Mechanic visit and fault assessment",1499,"45–75 min arrival"),service("Heavy vehicle towing","Recovery vehicle dispatch",4999,"60–120 min arrival"),service("Fleet maintenance visit","Scheduled multi-vehicle inspection",3499,"Half day")))
            );
            shops.saveAll(demoShops);
        };
    }
    private static Shop shop(String id,String name,String tag,String kind,double rating,String reviews,String distance,String address,String image,String icon,String offer,String time,List<String> areas,List<ServiceOffering> catalog){
        Shop shop=new Shop(id,name,tag,kind,rating,reviews,distance,address,image,icon,offer,time,areas);catalog.forEach(shop::addService);return shop;
    }
    private static ServiceOffering service(String name,String description,int price,String time){return new ServiceOffering(name,description,BigDecimal.valueOf(price),time);}
}
