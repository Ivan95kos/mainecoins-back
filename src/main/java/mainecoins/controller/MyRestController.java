package mainecoins.controller;

import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.market.BookTicker;
import com.binance.api.client.domain.market.TickerStatistics;
import mainecoins.repository.CustomUserRepository;
import mainecoins.model.CustomUser;
import mainecoins.model.dto.SingInDTO;
import mainecoins.security.JwtTokenProvider;
import mainecoins.service.CustomUserService;
import mainecoins.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@CrossOrigin(
//        origins = "*",
//        allowCredentials = "true",
//        methods = {RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH},
//        allowedHeaders = {"X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"}
//)
public class MyRestController {

    private CustomUserService customUserService;

    private JwtTokenProvider jwtTokenProvider;

    private CustomUserRepository customUserRepository;

    @Autowired
    public MyRestController(CustomUserService customUserService, JwtTokenProvider jwtTokenProvider, CustomUserRepository customUserRepository) {
        this.customUserService = customUserService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserRepository = customUserRepository;
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> singUp(@RequestBody @Valid CustomUser customUser) {

        CustomUser saveUser = customUserService.signUp(customUser);

        return new ResponseEntity<>(saveUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> signIn(@RequestBody @Valid final SingInDTO singInDTO) {

        return customUserService.getResponseEntityWithToken(jwtTokenProvider.createToken(customUserService.signIn(singInDTO)));

    }

    @GetMapping("/me")
    public CustomUser whoAmI(final Authentication authentication) {

        return customUserRepository.findByEmail(authentication.getName());

    }

    @GetMapping("/account-binance")
    public ResponseEntity<Account> getAccountStatusBinance() {

        return new ResponseEntity<>(customUserService.getStatusAccount(), HttpStatus.OK);

    }

    @GetMapping("/all-24hr-price-statistics-binance")
    public ResponseEntity<List<TickerStatistics>> getAll24HrPriceStatistics() {

        return new ResponseEntity<>(customUserService.getAll24HrPriceStatistics(), HttpStatus.OK);

    }

    @GetMapping("/24hr-price-statistics-binance")
    public ResponseEntity<TickerStatistics> get24HrPriceStatistics(@RequestParam("symbol") String tickerStatistics) {

        return new ResponseEntity<>(customUserService.get24HrPriceStatistics(tickerStatistics), HttpStatus.OK);

    }

    @GetMapping("/get-assets")
    public ResponseEntity<List<Asset>> getAssets() {

        return new ResponseEntity<>(customUserService.getAssets(), HttpStatus.OK);

    }
    @GetMapping("/get-book-tickers")
    public ResponseEntity<List<BookTicker>> getBookTickers() {

        return new ResponseEntity<>(customUserService.getBookTickers(), HttpStatus.OK);

    }


}
