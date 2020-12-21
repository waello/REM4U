package com.navettevatry.rem4u.common.utils.mapper.requests.updated;

import com.navettevatry.rem4u.common.resources.dto.standard.VTCComparatorRequest;
import com.navettevatry.rem4u.common.resources.dto.urbanDriver.Address;
import com.navettevatry.rem4u.common.resources.dto.urbanDriver.Addresss;
import com.navettevatry.rem4u.common.resources.dto.urbanDriver.PassengerRequestsRequest;

import java.util.function.Function;

public class MArcelRequestMapper {
    public static Function<VTCComparatorRequest, PassengerRequestsRequest> MArcelVTCComparatorRequestTopassenger_requests
            = new Function<VTCComparatorRequest, PassengerRequestsRequest>() {
        @Override
        public PassengerRequestsRequest apply(VTCComparatorRequest vtcComparatorRequest) {

            PassengerRequestsRequest Tr = new PassengerRequestsRequest();
            Address ad1 = new Address ();
            ad1.setLong(vtcComparatorRequest.getDepartureLocation().getLongitude());
            ad1.setLat(vtcComparatorRequest.getDepartureLocation().getLatitude());
            ad1.setName(vtcComparatorRequest.getDepartureLocation().getAddress());
            ad1.setzip_code("");
            Tr.setAddress_pick_up(ad1);
            Addresss ad2= new Addresss ();
            ad2.setLat(vtcComparatorRequest.getArrivalLocation().getLatitude());
            ad2.setlong(vtcComparatorRequest.getArrivalLocation().getLongitude());
            ad2.setName(vtcComparatorRequest.getArrivalLocation().getAddress());
            Tr.setAddress_drop_off(ad2);
            ad2.setzip_code("");
            Tr.setType(1);
            Tr.setChannel(2);

            return Tr;


        }

    };

}
