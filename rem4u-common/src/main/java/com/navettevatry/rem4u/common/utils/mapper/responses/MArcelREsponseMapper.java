package com.navettevatry.rem4u.common.utils.mapper.responses;

import com.navettevatry.rem4u.common.resources.dto.standard.*;
import com.navettevatry.rem4u.common.resources.dto.urbanDriver.PassengerRequestsResponse;
import com.navettevatry.rem4u.common.resources.enumeration.standard.ComfortType;
import com.navettevatry.rem4u.common.resources.enumeration.standard.VTCPlatformName;
import com.navettevatry.rem4u.common.utils.enrichers.averageValue.BlocSansMarqueEconomique;

import java.util.ArrayList;
import java.util.List;

public class MArcelREsponseMapper {
    public static VTCComparatorResponse MarcelVehicleTypeToVTCComparatorResponse(List<PassengerRequestsResponse> vehiculeResponse

            , VTCComparatorResponse vtcComparatorResponse) {
        System.out.println("MarcelVehicleTypeToVTCComparatorResponse"+vehiculeResponse.toString());

        try{
            if (vtcComparatorResponse == null)
                vtcComparatorResponse = new VTCComparatorResponse(null,
                        null,
                        null,
                        null,
                        null,
                        new ArrayList<>());

            for(PassengerRequestsResponse bb : vehiculeResponse){

                vtcComparatorResponse.getOffers().add(
                        new Offer(VTCPlatformName.Marcel,
                                new Driver(null, null, null, null),
                                new Vehicle( null,
                                        (int)bb.getVehicleType().getMax_places(),(int) bb.getVehicleType().getMax_luggages(), null, null,
                                        getComfortType(bb.getVehicleType().getDesignation()) ,
                                        (float) BlocSansMarqueEconomique.getAverageValue()),
                                new Price("EURO", (float) bb.getCustomerPrice()/100),
                                (int) bb.getVehicleType().getDelay_free_in_min(),
                                null,
                                null,
                                null,
                                (int) bb.getDuration()*60));
            }

        }catch(Exception e){System.out.println("3awed "+e.getMessage());}
        return vtcComparatorResponse;
    }
    protected static ComfortType getComfortType(String comfortType){
        if(comfortType.equals("Berline"))
            return ComfortType.BERLIN;
        else if(comfortType.equals("VAN"))
            return ComfortType.ECO;
        else if(comfortType.equals("Luxe") || comfortType.equals("Business"))
            return ComfortType.BUSINESS;
        else  if (comfortType.equals("e•co"))
            return  ComfortType.ECO;
        else
            return ComfortType.VAN;
    }
}

