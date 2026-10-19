package in.vahanmitra.api.booking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateBookingRequest(
        @NotBlank String shopId,
        @NotBlank String serviceName,
        @NotBlank String vehicle,
        @NotBlank String location,
        @NotBlank String mode,
        @NotBlank String preferredTime,
        @NotBlank String customerName,
        @NotBlank @Pattern(regexp="[6-9][0-9]{9}",message="Enter a valid 10-digit Indian mobile number") String customerPhone) {}
