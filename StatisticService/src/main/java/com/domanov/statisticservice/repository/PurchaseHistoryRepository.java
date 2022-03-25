package com.domanov.statisticservice.repository;

import com.domanov.statisticservice.model.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Integer> {
}
