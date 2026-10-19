package in.vahanmitra.api.booking;

import in.vahanmitra.api.shop.ServiceOffering;
import in.vahanmitra.api.shop.Shop;
import in.vahanmitra.api.shop.ShopRepository;
import jakarta.validation.Valid;
import java.security.SecureRandom;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/bookings")
@Transactional
public class BookingController {
    private static final Set<String> STATUSES=Set.of("REQUESTED","ACCEPTED","REJECTED","EN_ROUTE","ARRIVED","IN_PROGRESS","COMPLETED");
    private static final SecureRandom RANDOM=new SecureRandom();
    private final BookingRepository bookings;private final ShopRepository shops;
    public BookingController(BookingRepository bookings,ShopRepository shops){this.bookings=bookings;this.shops=shops;}

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@Valid @RequestBody CreateBookingRequest request){
        Shop shop=shops.findById(request.shopId()).filter(Shop::isActive).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Shop not found"));
        ServiceOffering offering=shop.getServices().stream().filter(item->item.getName().equalsIgnoreCase(request.serviceName())).findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Service not offered by this shop"));
        String ref="VM-"+(10000+RANDOM.nextInt(90000));
        Booking booking=new Booking(ref,request.customerName(),request.customerPhone(),request.vehicle(),offering.getName(),request.location(),request.mode(),request.preferredTime(),offering.getPrice(),offering.getEstimatedTime(),shop);
        return BookingResponse.from(bookings.save(booking));
    }
    @GetMapping("/{referenceCode}")
    public BookingResponse get(@PathVariable String referenceCode){return BookingResponse.from(find(referenceCode));}
    @GetMapping
    public List<BookingResponse> forShop(@RequestParam String shopId){return bookings.findByShopIdOrderByCreatedAtDesc(shopId).stream().map(BookingResponse::from).toList();}
    @PatchMapping("/{referenceCode}/status")
    public BookingResponse updateStatus(@PathVariable String referenceCode,@Valid @RequestBody BookingStatusRequest request){
        String status=request.status().toUpperCase();if(!STATUSES.contains(status))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unsupported booking status");
        Booking booking=find(referenceCode);if(!allowedNext(booking.getStatus()).contains(status))throw new ResponseStatusException(HttpStatus.CONFLICT,"Cannot move booking from "+booking.getStatus()+" to "+status);
        booking.setStatus(status);return BookingResponse.from(bookings.save(booking));
    }
    private Booking find(String ref){return bookings.findByReferenceCode(ref).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Booking not found"));}
    private Set<String> allowedNext(String current){return switch(current){case "REQUESTED"->Set.of("ACCEPTED","REJECTED");case "ACCEPTED"->Set.of("EN_ROUTE");case "EN_ROUTE"->Set.of("ARRIVED");case "ARRIVED"->Set.of("IN_PROGRESS");case "IN_PROGRESS"->Set.of("COMPLETED");default->Set.of();};}
}
