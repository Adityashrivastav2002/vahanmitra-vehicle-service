package in.vahanmitra.api.booking;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {
    private final BookingRepository bookings;

    public MetricsController(BookingRepository bookings) {
        this.bookings = bookings;
    }

    @GetMapping
    public Map<String, Long> getMetrics() {
        return Map.of("servicesDelivered", bookings.countByStatus("COMPLETED"));
    }
}
