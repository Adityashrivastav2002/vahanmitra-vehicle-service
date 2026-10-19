package in.vahanmitra.api.shop;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, String> {
    List<Shop> findByActiveTrueOrderByNameAsc();
}
