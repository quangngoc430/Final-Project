package vn.edu.vnuk.shopping.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.shopping.model.ItemHasCategory;

@Repository
public interface ItemHasCategoryRepository extends CrudRepository<ItemHasCategory, Long> {

}