package vn.edu.vnuk.shopping.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.shopping.model.OauthAccessToken;

@Repository
public interface OauthAccessTokenRepository extends CrudRepository<OauthAccessToken, Long> {

    OauthAccessToken getByAccessToken(String accessToken);
}