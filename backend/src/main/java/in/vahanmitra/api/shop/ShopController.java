package in.vahanmitra.api.shop;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/shops")
@Transactional
public class ShopController {
    private final ShopRepository shops;
    private final ServiceOfferingRepository services;
    public ShopController(ShopRepository shops,ServiceOfferingRepository services){this.shops=shops;this.services=services;}

    @GetMapping
    public List<ShopResponse> list(@RequestParam(required=false) String area,@RequestParam(required=false) String query){
        return shops.findByActiveTrueOrderByNameAsc().stream()
                .filter(shop->area==null||area.isBlank()||area.equalsIgnoreCase("Delhi NCR")||shop.getServiceAreas().stream().anyMatch(a->a.equalsIgnoreCase(area)))
                .filter(shop->query==null||query.isBlank()||(shop.getName()+" "+shop.getKind()+" "+shop.getServices().stream().map(ServiceOffering::getName).reduce("",(a,b)->a+" "+b)).toLowerCase().contains(query.toLowerCase()))
                .map(ShopResponse::from).toList();
    }
    @GetMapping("/{shopId}")
    public ShopResponse get(@PathVariable String shopId){return ShopResponse.from(findShop(shopId));}
    @PostMapping("/{shopId}/services") @ResponseStatus(HttpStatus.CREATED)
    public ShopResponse.ServiceResponse addService(@PathVariable String shopId,@Valid @RequestBody ServiceOfferingRequest request){
        Shop shop=findShop(shopId);ServiceOffering offering=new ServiceOffering(request.name(),request.description(),request.price(),request.estimatedTime());shop.addService(offering);shops.save(shop);return ShopResponse.ServiceResponse.from(offering);
    }
    @PutMapping("/{shopId}/services/{serviceId}")
    public ShopResponse.ServiceResponse updateService(@PathVariable String shopId,@PathVariable Long serviceId,@Valid @RequestBody ServiceOfferingRequest request){
        ServiceOffering offering=services.findById(serviceId).filter(item->item.getShop().getId().equals(shopId)).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Service not found"));offering.update(request.name(),request.description(),request.price(),request.estimatedTime());return ShopResponse.ServiceResponse.from(services.save(offering));
    }
    @DeleteMapping("/{shopId}/services/{serviceId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable String shopId,@PathVariable Long serviceId){
        Shop shop=findShop(shopId);ServiceOffering offering=services.findById(serviceId).filter(item->item.getShop().getId().equals(shopId)).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Service not found"));shop.removeService(offering);shops.save(shop);
    }
    private Shop findShop(String id){return shops.findById(id).filter(Shop::isActive).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Shop not found"));}
}
