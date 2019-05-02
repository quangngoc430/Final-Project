package vn.edu.vnuk.shopping.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.shopping.model.DiscountPrice;

@Repository
public interface DiscountPriceRepository extends CrudRepository<DiscountPrice, Long> {
}