package com.shixun.travel.service;

import com.shixun.travel.domain.Address;

import java.util.List;

public interface AddressService {

    List<Address> findAddressByUid(int uid);
}
