package mainecoins.service;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.market.BookTicker;
import com.binance.api.client.domain.market.TickerStatistics;
import com.binance.api.client.exception.BinanceApiException;
import lombok.extern.slf4j.Slf4j;
import mainecoins.repository.CustomUserRepository;
import mainecoins.exception.CustomException;
import mainecoins.model.CustomUser;
import mainecoins.model.dto.SingInDTO;
import mainecoins.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static mainecoins.security.SecurityConstants.HEADER_STRING;
import static mainecoins.security.SecurityConstants.TOKEN_PREFIX;

@Service
@Slf4j
public class CustomUserService {

    private final CustomUserRepository customUserRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserService(CustomUserRepository customUserRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.customUserRepository = customUserRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomUser signUp(CustomUser customUser) {
        if (!customUserRepository.existsByEmail(customUser.getEmail())) {

            customUser.setPassword(passwordEncoder.encode(customUser.getPassword()));

            CustomUser saveUser = customUserRepository.save(customUser);
            return saveUser;
        } else {
            log.error("Email is already in use");
            throw new CustomException("Email is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public CustomUser signIn(SingInDTO singInDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(singInDTO.getEmail(), singInDTO.getPassword()));

        return customUserRepository.findByEmail(singInDTO.getEmail());

    }

    public ResponseEntity<GenericResponse> getResponseEntityWithToken(String token) {
        MultiValueMap<String, String> headers = new HttpHeaders();

        headers.add(HEADER_STRING, TOKEN_PREFIX + token);

        return new ResponseEntity<>(
                new GenericResponse(TOKEN_PREFIX + token, HttpStatus.OK, HttpStatus.OK.value()),
                headers,
                HttpStatus.OK
        );
    }

    public Account getStatusAccount() {
        Account account = getClient().getAccount();
        return account;
    }

    public List<TickerStatistics> getAll24HrPriceStatistics() {
        return getClient().getAll24HrPriceStatistics();
    }

    public TickerStatistics get24HrPriceStatistics(String tickerStatistics) {
        return getClient().get24HrPriceStatistics(tickerStatistics);
    }

    public List<Asset> getAssets(){
        return getClient().getAllAssets();
    }

    public List<BookTicker> getBookTickers(){
       return getClient().getBookTickers();
    }

    private BinanceApiRestClient getClient() {
        try {
            BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(
                    "RGWM8zFZVbfIPhtgjqLEROixytREUDZsWCRG1gwRzCFApwtmtALvVJuRxdBG6aSY",
                    "jsneHvxAt5GT5CS1HR2trQoU1DRI1yiSTKq5fhYeOrmTi7P2ZhLvnr3h3lZYisPZ");
            BinanceApiRestClient client = factory.newRestClient();
            return client;
        } catch (BinanceApiException e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
