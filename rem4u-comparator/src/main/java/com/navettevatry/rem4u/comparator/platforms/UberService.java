package com.navettevatry.rem4u.comparator.platforms;

import com.google.api.client.auth.oauth2.Credential;
import com.navettevatry.rem4u.common.resources.dto.lecab.updated.estimate.EstimateRequest;
import com.navettevatry.rem4u.common.resources.dto.lecab.updated.estimate.EstimateResponse;
import com.navettevatry.rem4u.common.resources.dto.standard.VTCComparatorRequest;
import com.navettevatry.rem4u.common.resources.dto.standard.VTCComparatorResponse;
import com.navettevatry.rem4u.common.resources.dto.uber.Idresponse;
import com.navettevatry.rem4u.common.resources.dto.uber.PickupTimeEstimateRequest;
import com.navettevatry.rem4u.common.resources.dto.uber.PriceEstimatesPromotionRequest;
import com.navettevatry.rem4u.common.resources.dto.uber.ProductsRequest;
import com.navettevatry.rem4u.common.resources.dto.uber.updated.FareEstimateRange;
import com.navettevatry.rem4u.common.resources.dto.uber.updated.UberRequest;
import com.navettevatry.rem4u.common.resources.dto.uber.updated.UberResponse;
import com.navettevatry.rem4u.common.resources.enumeration.FrontDistance;
import com.navettevatry.rem4u.common.utils.mapper.requests.UberRequestsMapper;
import com.navettevatry.rem4u.common.utils.mapper.responses.UberResponsesMapper;
import com.navettevatry.rem4u.common.utils.restclient.RestTemplateBuilder;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.auth.OAuth2Credentials;
import com.uber.sdk.rides.client.CredentialsSession;
import com.uber.sdk.rides.client.ServerTokenSession;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.uber.sdk.rides.client.UberRidesApi;
import com.uber.sdk.rides.client.model.*;
import com.uber.sdk.rides.client.services.RidesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Response;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 *  Uber needed APIs Service Class
 * Created by Chakib Daii.
 */

@Service
public class UberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UberService.class);

    @Value("${uber.client-id}")
    private String CLIENT_ID;
    @Value("${uber.client-secret}")
    private String CLIENT_SECRET;
    @Value("${uber.server-token}")
    private String SERVER_TOKEN;
    @Value("${uber.authorization-code}")
    private String AUTH_CODE;
    @Value("${uber.user-id}")
    private String USER_ID;

    private RidesService ridesService;


    @Autowired
    protected RestTemplate apisRestTemplate;

    protected HttpHeaders httpHeaders;

    @PostConstruct()
    void init() {
        LOGGER.debug("[UberService] Post Construct");
        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        SessionConfiguration config = new SessionConfiguration.Builder()
                .setClientId(CLIENT_ID)
//                .setClientSecret(CLIENT_SECRET)
                .setServerToken(SERVER_TOKEN)
                .build();

        ServerTokenSession session = new ServerTokenSession(config);

//        List<Scope> scopes = new ArrayList<>();
//        scopes.add(Scope.ALL_TRIPS); //just an example
//
//        SessionConfiguration config = new SessionConfiguration.Builder()
//                .setClientId(CLIENT_ID)
//                .setClientSecret(CLIENT_SECRET)
//                .setScopes(scopes)
//                .setRedirectUri("http://localhost:9091/")
//                .build();
//
//        OAuth2Credentials credentials = new OAuth2Credentials.Builder()
//                .setSessionConfiguration(config)
//                .build();
//
//        try {
//            String authorizationUrl = credentials.getAuthorizationUrl();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Credential credential = credentials.authenticate(AUTH_CODE, USER_ID);
//        CredentialsSession session = new CredentialsSession(config, credential);

        this.ridesService = UberRidesApi.with(session).build().createService();
    }

    /**
     * Asynchronous Method to use to get Uber response
     * @param vtcComparatorRequest
     * @return @{@link CompletableFuture<VTCComparatorResponse>}
     */
    //TODO: test & verify
    @Async
    public CompletableFuture<VTCComparatorResponse> getUberResponseAsync(VTCComparatorRequest vtcComparatorRequest) throws Exception {
        LOGGER.debug("[UberService] getUberResponseAsync");
        final long start = System.currentTimeMillis();

        VTCComparatorResponse vtcComparatorResponse = null;
//        try{
//            vtcComparatorResponse = UberResponsesMapper
//                .productsListToVTCComparatorResponse(
//                        this.getProducts(UberRequestsMapper.VTCComparatorRequestToProductsRequest.apply(vtcComparatorRequest)),
//                null);
//        }catch (Exception e){ LOGGER.debug(e.toString()); }
//
//        try{
//            vtcComparatorResponse = UberResponsesMapper
//                    .priceEstimatesListToVTCComparatorResponse(
//                            this.getPriceEstimates(UberRequestsMapper.VTCComparatorRequestToPriceEstimatesPromotionRequest.apply(vtcComparatorRequest)),
//                            vtcComparatorResponse);
//        }catch (Exception e){ LOGGER.debug(e.toString()); }
//        try{
//            vtcComparatorResponse = UberResponsesMapper
//                    .timeEstimatesListToVTCComparatorResponse(
//                            this.getPickupTimeEstimate(UberRequestsMapper.VTCComparatorRequestToPickupTimeEstimateRequest.apply(vtcComparatorRequest)),
//                            vtcComparatorResponse);
//        }catch (Exception e){ LOGGER.debug(e.toString()); }
//        try{
//            vtcComparatorResponse = UberResponsesMapper
//                    .promotionToVTCComparatorResponse(
//                            this.getPromotion(UberRequestsMapper.VTCComparatorRequestToPriceEstimatesPromotionRequest.apply(vtcComparatorRequest)),
//                            vtcComparatorResponse);
//        }catch (Exception e){ LOGGER.debug(e.toString()); }

//        try{
//            vtcComparatorResponse = UberResponsesMapper
//                    .priceEstimatesListToVTCComparatorResponse(
//                            this.getPriceEstimates(UberRequestsMapper.VTCComparatorRequestToPriceEstimatesPromotionRequest.apply(vtcComparatorRequest)),
//                            vtcComparatorResponse);
//        }catch (Exception e){ LOGGER.debug(e.toString()); }
//        try{
//            vtcComparatorResponse = UberResponsesMapper
//                    .timeEstimatesListToVTCComparatorResponse(
//                            this.getPickupTimeEstimate(UberRequestsMapper.VTCComparatorRequestToPickupTimeEstimateRequest.apply(vtcComparatorRequest)),
//                            vtcComparatorResponse);
//        }catch (Exception e){ LOGGER.debug(e.toString()); }

        try{
            vtcComparatorResponse = UberResponsesMapper
                    .priceEstimatesListToVTCComparatorResponse(
                            this.getFareEstimates(UberRequestsMapper.VTCComparatorRequestToUberRequest.apply(vtcComparatorRequest)),
                            vtcComparatorResponse);
        }catch (Exception e){ LOGGER.debug(e.toString()); }

        LOGGER.debug("[UberService] Elapsed time: {}", (System.currentTimeMillis() - start));

        return CompletableFuture.completedFuture(vtcComparatorResponse);
    }

    /**
     * Method to use to get Uber response
     * @param vtcComparatorRequest
     * @return @{@link VTCComparatorResponse}
     */
    public VTCComparatorResponse getUberResponse(VTCComparatorRequest vtcComparatorRequest) throws Exception {
        LOGGER.debug("[UberService] getUberResponse");
        final long start = System.currentTimeMillis();

        VTCComparatorResponse vtcComparatorResponse = null;
        /**try{
            vtcComparatorResponse = UberResponsesMapper
                    .productsListToVTCComparatorResponse(
                            this.getProducts(UberRequestsMapper.VTCComparatorRequestToProductsRequest.apply(vtcComparatorRequest)),
                            null);
        }catch (Exception e){ LOGGER.debug(e.toString()); }
        try{
            vtcComparatorResponse = UberResponsesMapper
                    .priceEstimatesListToVTCComparatorResponse(
                            this.getPriceEstimates(UberRequestsMapper.VTCComparatorRequestToPriceEstimatesPromotionRequest.apply(vtcComparatorRequest)),
                            vtcComparatorResponse);
        }catch (Exception e){ LOGGER.debug(e.toString()); }**/
//        try{
//            vtcComparatorResponse = UberResponsesMapper
//                    .timeEstimatesListToVTCComparatorResponse(
//                            this.getPickupTimeEstimate(UberRequestsMapper.VTCComparatorRequestToPickupTimeEstimateRequest.apply(vtcComparatorRequest)),
//                            vtcComparatorResponse);
//        }catch (Exception e){ LOGGER.debug(e.toString()); }
//        try{
//            vtcComparatorResponse = UberResponsesMapper
//                    .promotionToVTCComparatorResponse(
//                            this.getPromotion(UberRequestsMapper.VTCComparatorRequestToPriceEstimatesPromotionRequest.apply(vtcComparatorRequest)),
//                            vtcComparatorResponse);
//        }catch (Exception e){ LOGGER.debug(e.toString()); }

        try{
            vtcComparatorResponse = UberResponsesMapper
                    .priceEstimatesListToVTCComparatorResponse(
                            this.getFareEstimates(UberRequestsMapper.VTCComparatorRequestToUberRequest.apply(vtcComparatorRequest)),
                            vtcComparatorResponse);
        }catch (Exception e){ LOGGER.debug(e.toString()); }

        LOGGER.debug("[UberService] Elapsed time: {}", (System.currentTimeMillis() - start));

        return vtcComparatorResponse;
    }

    /**
     * get available uber products
     * @param productsRequest
     * @return @{@link List<Product>}
     */
    public List<Product> getProducts(ProductsRequest productsRequest){
        LOGGER.debug("[UberService] getProducts");
        List<Product> products = null;
        try {
            Response<ProductsResponse> response = ridesService.getProducts(productsRequest.getLatitude(),
                    productsRequest.getLongitude()).execute();
            products = response.body().getProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * get Price estimates
     * @param priceEstimatesPromotionRequest
     * @return @{@link List<PriceEstimate>}
     */
    public List<PriceEstimate> getPriceEstimates(PriceEstimatesPromotionRequest priceEstimatesPromotionRequest){
        LOGGER.debug("[UberService] getPriceEstimates");
        List<PriceEstimate> priceEstimates = null;
        try {
            Response<PriceEstimatesResponse> response =
                    ridesService.getPriceEstimates(priceEstimatesPromotionRequest.getStartLatitude(),
                            priceEstimatesPromotionRequest.getStartLongitude(),priceEstimatesPromotionRequest.getEndLatitude(),
                            priceEstimatesPromotionRequest.getEndLongitude()).execute();
            priceEstimates = response.body().getPrices();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return priceEstimates;
    }

    /**
     * get Time estimates
     * @param pickupTimeEstimateRequest
     * @return @{@link List<TimeEstimate>}
     */
    public List<TimeEstimate> getPickupTimeEstimate(PickupTimeEstimateRequest pickupTimeEstimateRequest){
        LOGGER.debug("[UberService] getPickupTimeEstimate");
        List<TimeEstimate> timeEstimates = null;
        try {
            Response<TimeEstimatesResponse> response =
                    ridesService.getPickupTimeEstimate(pickupTimeEstimateRequest.getStartLatitude(),
                            pickupTimeEstimateRequest.getStartLongitude(), pickupTimeEstimateRequest.getProductId())
                            .execute();
            timeEstimates = response.body().getTimes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeEstimates;
    }

    /**
     * get Promotion
     * @param priceEstimatesPromotionRequest
     * @return @{@link Promotion}
     */
    public Promotion getPromotion(PriceEstimatesPromotionRequest priceEstimatesPromotionRequest){
        Promotion promotion = null;
        try {
            Response<Promotion> response =
                    ridesService.getPromotions(priceEstimatesPromotionRequest.getStartLatitude(),
                            priceEstimatesPromotionRequest.getStartLongitude(),
                            priceEstimatesPromotionRequest.getEndLatitude(),
                            priceEstimatesPromotionRequest.getEndLongitude()).execute();
            promotion = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promotion;
    }


    public UberResponse getFareEstimates(UberRequest uberRequest){
        LOGGER.debug("[UberService] getFareEstimates");
        this.httpHeaders = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json");
        this.httpHeaders.setContentType( mediaType);
        this.httpHeaders.put("x-csrf-token", Collections.singletonList("x"));
        this.httpHeaders.put("accept", Collections.singletonList("*/*"));
            this.httpHeaders.put("sec-fetch-site", Collections.singletonList("same-origin"));
        this.httpHeaders.put("sec-fetch-mode", Collections.singletonList("cors"));
        this.httpHeaders.put("sec-fetch-dest", Collections.singletonList("empty"));
        this.httpHeaders.put("accept-language", Arrays.asList("fr-FR","fr;q=0.9","en-US;q=0.8","en;q=0.7"));
        this.httpHeaders.put("cookie", Collections.singletonList("marketing_vistor_id=c8a71372-f5d9-4157-bc8e-d680941b992e; optimizelyEndUserId=oeu1606851986434r0.6841532426234744; OPTOUTMULTI=; segmentCookie=b; _ga=GA1.2.370173496.1606851989; CONSENTMGR=ts:1606852003990%7Cconsent:true; _gcl_au=1.1.849438664.1606852004; _fbp=fb.1.1606852005703.1257407147; udi-id=RSBqaoOdbjbAUZ9vJ049Wp1024TgsHixiw+Mz5XG5n7tt7HgAXzOlr4HH3knnLMyA89aEY00NLVPHvjBNtZcZNcs1BYd3Mq6PlPAydKWTRx821hO2RNGMLsGjshuRKhB3tzDZXO9XQgkZT+FXjyroP5oxn2v9WaD6BkbGEXhM9UtcdKkU1/577OTG8QxoJDbl+SDBF+DccBbdVqYOB2WNQ==uGRi9gN5SpVSaHRcuJuvzg==2D3jcVYtl2dYHGVw2NLKoXDfKs4Vk13AsAy9pP9QuPY=; _cc=ATbLVYXh0GqoZ1NLU3zCfF51; sid=QA.CAESEPsYNrP7QEMkrOQUQjgQN4YY7JW-_wUiATEqJGQwMjEwNDhjLTQ4OWMtNDg4OS1iZTAyLTMxM2JhOGNkMzgwMzJAyziB6gjFmVhAyLgOXUGFtHC93cZFfDCjTgEtWUGFdlIF2LjR_oeWPkPvCElX-AaD_rNWgKPQRgSkqtKlLAvW6ToBMUIIdWJlci5jb20.yL2VgujJuFnZMBxwaKGW8iNI0fQZEwzGHJu01Ed2OQk; csid=1.1609534190921.0vLFytolU7iJedug91r8KK7glygZbUQhPSVn0ya1sBw=; fsid=e2555afk-ejlp-jopr-vzmp-sru3327906z3; _hjid=b154a72f-e63e-4114-a6a8-8b6eaba6a9f8; AMP_TOKEN=%24NOT_FOUND; _gid=GA1.2.984473321.1608457934; _ua={\"session_id\":\"59064ecc-dad6-4289-ae67-455456c4f493\",\"session_time_ms\":1608458051684}; jwt-session=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2MDg0NTgwNTIsImV4cCI6MTYwODU0NDQ1Mn0.-1K0Pu_gYlGRIbN0xXPaaqEd5WP03DvQS40RVEvJlbM; utag_main=v_id:01761fd789fa0012580ee3fbe9c003073001b06b0086e$_sn:4$_ss:0$_st:1608459854379$_se:4$ses_id:1608457919994%3Bexp-session$_pn:2%3Bexp-session; newVsReturning=undefined; udi-fingerprint=LM0WYMS37xS9Wc8owhhjepBfcdAVPxjPpWjgy9MoOLSQ1jXIqK3KC0rgduSTtN7LKLiwJ7bWj8APFrYAkUY6AQ==GVltijGcp5Jll3Db+PhITYnYqC/H2Z5h2IDYohHGZb4=; feature-preferences={\"allow-geolocation\":true}"));
                             System.out.println( "erere");
        System.out.println( uberRequest.toString());
        System.out.println("rayen"+uberRequest.getDestination().getID());

        String uri = "https://m.uber.com/api/getFareEstimates";

        HttpEntity<UberRequest> entity = new HttpEntity<>(uberRequest , this.httpHeaders);
        ResponseEntity<UberResponse> jobsEstimateResponseResponse =
                apisRestTemplate.exchange(uri, HttpMethod.POST, entity, UberResponse.class);
        System.out.println( "yy"+jobsEstimateResponseResponse.getStatusCode().toString());
        System.out.println( "yzy"+jobsEstimateResponseResponse.getBody().toString());

        return  jobsEstimateResponseResponse.getBody();

    }
    public FrontDistance distance(VTCComparatorRequest vtc) {
        System.out.println( "jhjhjjj"+vtc.toString());

        this.httpHeaders = new HttpHeaders();

        this.httpHeaders.put("authority", Collections.singletonList("maps.googleapis.com"));
        this.httpHeaders.put("cache-control", Collections.singletonList("max-age=0"));
        this.httpHeaders.put("upgrade-insecure-requests", Collections.singletonList("1"));
        this.httpHeaders.put("user-agent", Collections.singletonList("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.150"));
        this.httpHeaders.put("accept", Collections.singletonList("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"));
        this.httpHeaders.put("sec-fetch-site", Collections.singletonList("none"));
        this.httpHeaders.put("sec-fetch-mode", Collections.singletonList("navigate"));
        this.httpHeaders.put("sec-fetch-user", Collections.singletonList("?1"));
        this.httpHeaders.put("sec-fetch-dest", Collections.singletonList("document"));
        this.httpHeaders.put("accept-language", Collections.singletonList("en-US,en;q=0.9,fr;q=0.8"));

        String uri ="https://maps.googleapis.com/maps/api/distancematrix/json?origins="+vtc.getDepartureLocation().getLatitude().toString()+","+vtc.getDepartureLocation().getLongitude().toString()+"&destinations="+vtc.getArrivalLocation().getLatitude().toString()+","+vtc.getArrivalLocation().getLongitude().toString()+"&mode=driving&language=en-EN&sensor=false&key=AIzaSyDkJjmwF0PRX3IKKGwIn3v8j4uoHvjnWl0";
        HttpEntity entity = new HttpEntity<>( this.httpHeaders);
        ResponseEntity<FrontDistance> jobsEstimateResponseResponse =
                apisRestTemplate.exchange(uri, HttpMethod.POST, entity, FrontDistance.class);
        System.out.println( "jhjhjjj"+jobsEstimateResponseResponse.getStatusCode().toString());

        return  jobsEstimateResponseResponse.getBody();
    }
    public Idresponse getIDdep(VTCComparatorRequest uberRequest){
        this.httpHeaders = new HttpHeaders();

        this.httpHeaders.put("authority", Collections.singletonList("maps.googleapis.com"));
        this.httpHeaders.put("cache-control", Collections.singletonList("max-age=0"));
        this.httpHeaders.put("upgrade-insecure-requests", Collections.singletonList("1"));
        this.httpHeaders.put("user-agent", Collections.singletonList("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.150"));
        this.httpHeaders.put("accept", Collections.singletonList("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"));
        this.httpHeaders.put("sec-fetch-site", Collections.singletonList("none"));
        this.httpHeaders.put("sec-fetch-mode", Collections.singletonList("navigate"));
        this.httpHeaders.put("sec-fetch-user", Collections.singletonList("?1"));
        this.httpHeaders.put("sec-fetch-dest", Collections.singletonList("document"));
        this.httpHeaders.put("accept-language", Collections.singletonList("en-US,en;q=0.9,fr;q=0.8"));

        String uri = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+uberRequest.getDepartureLocation().getLatitude()+","+uberRequest.getDepartureLocation().getLongitude()+"&key=AIzaSyDkJjmwF0PRX3IKKGwIn3v8j4uoHvjnWl0";

        HttpEntity<VTCComparatorRequest> entity = new HttpEntity<>(uberRequest , this.httpHeaders);
        ResponseEntity<Idresponse> jobsEstimateResponseResponse =
                apisRestTemplate.exchange(uri, HttpMethod.GET, entity, Idresponse.class);
        System.out.println( "yy"+jobsEstimateResponseResponse.getStatusCode().toString());

        return  jobsEstimateResponseResponse.getBody();

    }
    public Idresponse getIDArr(VTCComparatorRequest uberRequest){
        this.httpHeaders = new HttpHeaders();

        this.httpHeaders.put("authority", Collections.singletonList("maps.googleapis.com"));
        this.httpHeaders.put("cache-control", Collections.singletonList("max-age=0"));
        this.httpHeaders.put("upgrade-insecure-requests", Collections.singletonList("1"));
        this.httpHeaders.put("user-agent", Collections.singletonList("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.150"));
        this.httpHeaders.put("accept", Collections.singletonList("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"));
        this.httpHeaders.put("sec-fetch-site", Collections.singletonList("none"));
        this.httpHeaders.put("sec-fetch-mode", Collections.singletonList("navigate"));
        this.httpHeaders.put("sec-fetch-user", Collections.singletonList("?1"));
        this.httpHeaders.put("sec-fetch-dest", Collections.singletonList("document"));
        this.httpHeaders.put("accept-language", Collections.singletonList("en-US,en;q=0.9,fr;q=0.8"));

        String uri = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+uberRequest.getArrivalLocation().getLatitude()+","+uberRequest.getArrivalLocation().getLongitude()+"&key=AIzaSyDkJjmwF0PRX3IKKGwIn3v8j4uoHvjnWl0";

        HttpEntity<VTCComparatorRequest> entity = new HttpEntity<>(uberRequest , this.httpHeaders);
        ResponseEntity<Idresponse> jobsEstimateResponseResponse =
                apisRestTemplate.exchange(uri, HttpMethod.GET, entity, Idresponse.class);
        System.out.println( "yy"+jobsEstimateResponseResponse.getStatusCode().toString());

        return  jobsEstimateResponseResponse.getBody();

    }
}
