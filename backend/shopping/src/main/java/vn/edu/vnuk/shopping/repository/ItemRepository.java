package vn.edu.vnuk.shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.shopping.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    @Query("select item " +
           "from Item item " +
           "where item.name like concat('%', lower(trim(:keyword)), '%') ")
    Page<Item> findAllByNameLike(@Param("keyword") String keyword,
                                 Pageable pageable);

    @Query("select item " +
           "from Item item " +
           "where item.id in :itemIds and item.name like concat('%', lower(trim(:keyword)), '%') ")
    Page<Item> findAllBy(@Param("keyword") String keyword,
                         @Param("itemIds") List<Long> itemIds,
                         Pageable pageable);

    @Query("select item " +
           "from Item item " +
           "inner join Category category " +
           "on category.id = item.categoryId " +
           "where category.id = :categoryId and item.name like concat('%', lower(trim(:keyword)), '%') ")
    Page<Item> findAllByCategoryId(@Param("keyword") String keyword,
                                   @Param("categoryId") Long categoryId,
                                   Pageable pageable);
}