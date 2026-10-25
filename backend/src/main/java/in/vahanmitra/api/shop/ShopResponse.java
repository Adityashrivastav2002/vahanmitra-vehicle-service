package in.vahanmitra.api.shop;

import java.math.BigDecimal;
import java.util.List;

public record ShopResponse(String id, String name, String tag, String kind, String rating, String reviews,
                           String distance, String address, List<String> areas, BigDecimal price, String time,
                           Double latitude, Double longitude,
                           String image, String icon, List<String> services, String offer,
                           List<ServiceResponse> catalog) {
    public static ShopResponse from(Shop shop) {
        List<ServiceResponse> offerings=shop.getServices().stream().map(ServiceResponse::from).toList();
        BigDecimal starting=offerings.stream().map(ServiceResponse::price).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        return new ShopResponse(shop.getId(),shop.getName(),shop.getTag(),shop.getKind(),String.valueOf(shop.getRating()),
                shop.getReviews(),shop.getDistance(),shop.getAddress(),shop.getServiceAreas(),starting,shop.getEstimatedTime(),
                shop.getLatitude(),shop.getLongitude(),
                shop.getImageKey(),shop.getIcon(),offerings.stream().limit(3).map(ServiceResponse::name).toList(),shop.getOffer(),offerings);
    }
    public record ServiceResponse(Long id,String name,String description,BigDecimal price,String time) {
        static ServiceResponse from(ServiceOffering offering){return new ServiceResponse(offering.getId(),offering.getName(),offering.getDescription(),offering.getPrice(),offering.getEstimatedTime());}
    }
}
