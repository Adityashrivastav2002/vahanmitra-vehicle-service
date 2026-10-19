package in.vahanmitra.api.shop;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {
    List<ServiceOffering> findByShopIdOrderByNameAsc(String shopId);
}
