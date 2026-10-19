package in.vahanmitra.api.shop;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ServiceOfferingRequest(
        @NotBlank String name,
        String description,
        @NotNull @DecimalMin("1.00") BigDecimal price,
        @NotBlank String estimatedTime) {}
