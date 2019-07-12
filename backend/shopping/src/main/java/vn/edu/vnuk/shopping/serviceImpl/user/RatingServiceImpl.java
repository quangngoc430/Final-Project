package vn.edu.vnuk.shopping.serviceImpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.exception.rating.RatingIsExistException;
import vn.edu.vnuk.shopping.exception.rating.RatingNotFoundException;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.model.OauthAccessToken;
import vn.edu.vnuk.shopping.model.Rating;
import vn.edu.vnuk.shopping.repository.RatingRepository;
import vn.edu.vnuk.shopping.service.user.RatingService;
import vn.edu.vnuk.shopping.service.user.TokenService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_NORMAL_USER')")
    public Rating create(Rating ratingParam, String accessToken) throws TokenIsExpiredException, TokenNotFoundException, RatingIsExistException {
        Account account = tokenService.get(accessToken).getAccount();

        Rating rating = ratingRepository.findByAccountIdAndItemId(account.getId(), ratingParam.getItemId());

        if (rating == null) {
            ratingParam.setAccountId(account.getId());
            ratingParam.setCreatedAt(new Date());
            ratingParam.setUpdatedAt(new Date());
            return ratingRepository.save(ratingParam);
        } else {
            throw new RatingIsExistException(rating.getId());
        }
    }

    @Override
    public Rating update(Long ratingId, Rating ratingParam, String accessToken) throws RatingNotFoundException {
        Optional<Rating> ratingOptional = ratingRepository.findById(ratingId);

        if (!ratingOptional.isPresent()) throw new RatingNotFoundException(ratingId);
        Rating rating = ratingOptional.get();

        rating.setComment(ratingParam.getComment());
        rating.setValue(ratingParam.getValue());
        rating.setUpdatedAt(new Date());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllByItemId(Long itemId) {
        return ratingRepository.findAllByItemId(itemId);
    }
}
