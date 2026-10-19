package in.vahanmitra.api.booking;

import java.math.BigDecimal;
import java.time.Instant;

public record BookingResponse(String ref,String customer,String vehicle,String service,String location,String mode,
                              String preferredTime,String status,BigDecimal price,String time,String shopId,
                              String shopName,Instant createdAt) {
    public static BookingResponse from(Booking booking){return new BookingResponse(booking.getReferenceCode(),booking.getCustomerName(),booking.getVehicleType(),booking.getServiceName(),booking.getServiceLocation(),booking.getServiceMode(),booking.getPreferredTime(),booking.getStatus(),booking.getQuotedPrice(),booking.getEstimatedJobTime(),booking.getShop().getId(),booking.getShop().getName(),booking.getCreatedAt());}
}
