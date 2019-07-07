package vn.edu.vnuk.shopping.serviceImpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.model.Rating;
import vn.edu.vnuk.shopping.repository.RatingRepository;
import vn.edu.vnuk.shopping.service.user.RatingService;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_NORMAL_USER')")
    public Rating create(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllByItemId(Long itemId) {
        return ratingRepository.findAllByItemId(itemId);
    }
}
