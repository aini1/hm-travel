package com.shixun.travel.service.impl;

import com.shixun.travel.dao.AddressDao;
import com.shixun.travel.domain.Address;
import com.shixun.travel.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> findAddressByUid(int uid) {
        return addressDao.findAddressByUid(uid);
    }
}
