package com.example.msa.model.service;

import com.example.msa.etc.WalletType;
import com.example.msa.model.DTO.TransactionDTO;
import com.example.msa.model.DTO.WalletDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TronGridService {
    private static final String TRONGRID_BASE = "http://api.trongrid.io";

    private final RestTemplate restTemplate;

    public TronGridService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    public WalletDTO getWallet(String address) {
        String url = TRONGRID_BASE + "/v1/account/" + address;
        log.info("Fetching wallet from TronGrid: {}", url);
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null || !response.containsKey("data")) {
                log.warn("no data returned for address {}", address);
                return null;
            }
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) response.get("data");
            if (response == null || !response.containsKey("data")) {
                log.warn("No data returned for address: {}", address);
                return null;
            }
            List<Map<String, Object>> datalist = (List<Map<String, Object>>) response.get("data");
            if (datalist == null || datalist.isEmpty()) {
                log.warn("Empty data list for address: {}", address);
                return null;
            }

            Map<String, Object> account = datalist.get(0);

            long balanceSun = account.containsKey("balance")
                    ? ((Number) account.get("balance")).longValue()
                    : 0L;
            double balanceTrx = balanceSun / 1_000_000.0;

            boolean isActive = balanceSun > 0;
            return new WalletDTO(address, balanceTrx, WalletType.HOT, isActive);
        } catch (Exception e) {
            log.error("Error Fetching wallet for address {}: {}", address, e.getMessage());
            return null;
        }
    }

    //Transactions
    public List<TransactionDTO> getTransaction(String address) {
        String url = TRONGRID_BASE + "/v1/accounts/" + address + "/transaction?limit=20";
        log.info("Fetching transaction from TronGrid: {}", url);
        List<TransactionDTO> result = new ArrayList<>
    }
}
