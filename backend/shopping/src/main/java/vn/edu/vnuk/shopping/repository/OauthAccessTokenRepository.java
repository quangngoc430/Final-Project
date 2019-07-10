package vn.edu.vnuk.shopping.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.shopping.model.OauthAccessToken;

@Repository
public interface OauthAccessTokenRepository extends CrudRepository<OauthAccessToken, Long> {

    @Query("SELECT new vn.edu.vnuk.shopping.model.OauthAccessToken(token, account) " +
           "FROM OauthAccessToken token " +
           "INNER JOIN Account account " +
           "ON token.accountId = account.id " +
           "Where token.accessToken = :accessToken")
    OauthAccessToken getByAccessToken(@Param("accessToken") String accessToken);
}