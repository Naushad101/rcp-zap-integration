package com.bnt.rcp.service;

import java.util.List;
import java.util.Map;

import com.bnt.rcp.dto.MerchantDto;

public interface MerchantService {

    Integer createMerchant(MerchantDto merchantDto);

    MerchantDto getMerchantById(Integer id);
    
    List<MerchantDto> getAllMerchants();

    Map<Integer, String> getAllMerchantsIdAndCode();

    Integer updateMerchant(Integer id, MerchantDto merchantDto);

    void deleteMerchant(Integer id);

    Map<Integer, String> getAllMerchantCategoryCodes();

}
