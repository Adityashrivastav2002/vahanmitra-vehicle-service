package in.vahanmitra.api.booking;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    Optional<Booking> findByReferenceCode(String referenceCode);
    List<Booking> findByShopIdOrderByCreatedAtDesc(String shopId);
    long countByStatus(String status);
}
