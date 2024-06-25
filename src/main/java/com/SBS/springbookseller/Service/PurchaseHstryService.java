package com.SBS.springbookseller.Service;

import com.SBS.springbookseller.DAO.entities.PurchaseHistory;
import com.SBS.springbookseller.DAO.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PurchaseHstryService {
    PurchaseHistory savePurchaseHistory(PurchaseHistory purchaseHistory,Long cartId, Long userId);
    Page<PurchaseHistory> findPurchasedItemByUserId(Long userId, int size, int page);

    Page<PurchaseHistory> findAllPurchase(int size, int page);
}
