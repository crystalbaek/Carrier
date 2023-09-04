package com.shop.carrier.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.carrier.data.entity.Carrier; // 변경된 엔티티 클래스명에 맞게 임포트 수정

public interface CarrierRepository extends JpaRepository<Carrier, Long> { // 인터페이스명도 변경
    @Query("SELECT c FROM Carrier c WHERE c.brand like %:brand%") // 엔티티명과 필드명도 변경
    List<Carrier> queryByBrand(String brand);

    @Query("SELECT c FROM Carrier c WHERE c.productId = :productId") // productId로 조회하는 쿼리 추가
    Carrier findByProductId(Long productId);

    // 케릭터 냉장고 커리문
    @Query("SELECT c FROM Carrier c WHERE c.price <= 1000000 and c.capacity <= 600 and (lower(c.brand) like lower(concat('%', :brand1, '%')) or lower(c.brand) like lower(concat('%', :brand2, '%')) or lower(c.brand) like lower(concat('%', :brand3, '%'))) ORDER BY c.price ASC, c.capacity DESC")
    List<Carrier> selectAllCharacter(@Param("brand1") String brand1, @Param("brand2") String brand2,
    @Param("brand3") String brand3);

    // 가성비 냉장고powerConsumptionGrade
    @Query("SELECT c FROM Carrier c WHERE c.price > 560000 AND c.price <= 1000000 and c.capacity >= 450 and c.capacity <= 1000 and (:brand IS NULL OR lower(c.brand) like lower(concat('%', :brand, '%'))) and c.powerConsumptionGrade > 0 and c.powerConsumptionGrade <= 5 and c.doorCount <= 4 and c.numberOfUsers <= 4 ORDER BY c.price ASC, c.capacity DESC, c.doorCount DESC, c.powerConsumptionGrade ASC, c.numberOfUsers DESC")
    List<Carrier> selectAllgasungbi(@Param("brand") String brand);

    // 신혼부부 냉장고
    @Query("SELECT c FROM Carrier c WHERE c.price >= 600000 AND c.price <= 1200000 AND c.capacity <= 800 AND c.capacity >= 400 AND c.doorCount <=4 AND c.powerConsumptionGrade <= 5 AND (:brand IS NULL OR lower(c.brand) like lower(concat('%', :brand, '%'))) AND c.powerConsumptionGrade > 0 ORDER BY c.price ASC, c.capacity DESC, c.doorCount DESC, c.powerConsumptionGrade ASC")
    List<Carrier> selectAllNwl(@Param("brand") String brand);

    // 1인가구
    @Query("SELECT c FROM Carrier c WHERE c.price <= 600000 AND c.capacity <= 300 AND c.capacity >= 50 AND (:brand IS NULL OR lower(c.brand) like lower(concat('%', :brand, '%'))) AND c.powerConsumptionGrade > 0 and c.powerConsumptionGrade <= 5 AND c.doorCount <= 4 AND c.numberOfUsers <= 4 ORDER BY c.price ASC, c.capacity DESC, c.numberOfUsers DESC, c.doorCount DESC, c.powerConsumptionGrade ASC")
    List<Carrier> selectAllsingle(@Param("brand") String brand);

}
