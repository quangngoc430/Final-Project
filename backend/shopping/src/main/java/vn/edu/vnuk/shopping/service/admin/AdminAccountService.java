package vn.edu.vnuk.shopping.service.admin;

public interface AdminAccountService {

    void deleteByEmail(String email);

    void deleteByID(Long id);

}
