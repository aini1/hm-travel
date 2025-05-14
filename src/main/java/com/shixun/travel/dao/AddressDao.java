package com.shixun.travel.dao;

import com.shixun.travel.domain.Address;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 收货地址持久层
 * */
public interface AddressDao {
    /**
     * 通过uid，查询某个用户所有收货地址
     * */
    @Select("SELECT * FROM tab_address WHERE uid=#{uid}")
    List<Address> findAddressByUid(int uid);
}
