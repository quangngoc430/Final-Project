package vn.edu.vnuk.shopping.model;

import com.fasterxml.jackson.annotation.JsonView;
import vn.edu.vnuk.shopping.validation.Account.GroupCreateAccount;
import vn.edu.vnuk.shopping.validation.Account.GroupUpdateAccount;
import vn.edu.vnuk.shopping.validation.Account.GroupUpdateAccountPassword;
import vn.edu.vnuk.shopping.validation.GroupLoginAccount;
import vn.edu.vnuk.shopping.view.View;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.*;
import javax.validation.groups.Default;

@Entity
@Table(name = "Account")
public class Account implements Serializable {
    
    private static final long serialVersionUID = -470638540947227479L;

    @Id
    @JsonView(View.Public.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JsonView(View.Public.class)
    @NotEmpty(message = "email is not empty", groups = {GroupCreateAccount.class, GroupLoginAccount.class})
    @Email(regexp = "^[a-zA-Z0-9._]+\\@gmail.com", message = "email contains [a-z|A-Z|0-9|.|_] and end with @gmail.com",
            groups = {GroupCreateAccount.class, GroupLoginAccount.class})
    @Size(min = 8, max = 80, message = "email must have between 8 and 80 characters",
            groups = {GroupCreateAccount.class, GroupLoginAccount.class, Default.class})
    @Column(name = "Email")
    private String email;

    @JsonView(View.Public.class)
    @NotEmpty(message = "password is not empty",
            groups = {GroupCreateAccount.class, GroupUpdateAccountPassword.class, GroupLoginAccount.class})
    @Pattern(regexp = "^[a-zA-Z0-9]+", message = "password have the wrong pattern",
            groups = {GroupCreateAccount.class, GroupUpdateAccountPassword.class, GroupLoginAccount.class})
    @Size(min = 8, max = 40, message = "password must have between 8 and 40 characters",
            groups = {GroupCreateAccount.class, GroupUpdateAccountPassword.class, GroupLoginAccount.class})
    @Column(name = "Password")
    private String password;

    @NotEmpty(message = "newPassword is not empty",
            groups = {GroupUpdateAccountPassword.class})
    @Pattern(regexp = "^[a-zA-Z0-9]+", message = "newPassword have the wrong pattern",
            groups = {GroupUpdateAccountPassword.class})
    @Size(min = 8, max = 40, message = "newPassword must have between 8 and 40 characters",
            groups = {GroupUpdateAccountPassword.class})
    @Transient
    private String newPassword;

    @JsonView(View.Public.class)
    @Size(max = 80, message = "fullname must have max 80 characters",
            groups = {GroupCreateAccount.class, GroupUpdateAccount.class, Default.class})
    @Column(name = "Fullname")
    private String fullname;

    @JsonView(View.Public.class)
    @NotNull
    @Column(name = "RoleId")
    private Long roleId;

    @JsonView(View.Public.class)
    @NotNull
    @Column(name = "Status")
    private Long status;

    @JsonView(View.Public.class)
    @Column(name = "CreatedAt")
    private Date createdAt;

    @JsonView(View.Public.class)
    @Column(name = "UpdatedAt")
    private Date updatedAt;

    @JsonView(View.Public.class)
    @Transient
    private Role role;

    @JsonView(View.Public.class)
    @Transient
    private List<Order> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}