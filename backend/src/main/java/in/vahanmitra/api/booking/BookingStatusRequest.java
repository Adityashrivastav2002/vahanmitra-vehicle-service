package in.vahanmitra.api.booking;

import jakarta.validation.constraints.NotBlank;

public record BookingStatusRequest(@NotBlank String status) {}
