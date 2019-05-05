package vn.edu.vnuk.shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.shopping.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query("select category " +
           "from Category category " +
           "where lower(category.name) like concat('%', lower(trim(:keyword)), '%') ")
    Page<Category> findAll(@Param("keyword") String keyword, Pageable pageable);

    boolean existsByName(String name);
}