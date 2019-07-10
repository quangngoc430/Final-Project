package vn.edu.vnuk.shopping.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "OAuthAccessToken")
public class OauthAccessToken {

    public OauthAccessToken() {}

    public OauthAccessToken(OauthAccessToken oauthAccessToken, Account account) {
        this.id = oauthAccessToken.id;
        this.accessToken = oauthAccessToken.accessToken;
        this.expires = oauthAccessToken.expires;
        this.expiredAt = oauthAccessToken.expiredAt;
        this.status = oauthAccessToken.status;
        this.accountId = account.getId();
        this.createdAt = oauthAccessToken.createdAt;
        this.updatedAt = oauthAccessToken.updatedAt;

        this.account = account;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotEmpty
    @Column(name = "AccessToken")
    private String accessToken;

    @NotNull
    @Column(name = "Expires")
    private Long expires;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "ExpiredAt")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "expired_at is not null")
    @Column(name = "ExpiredAt")
    private Date expiredAt;

    @NotNull
    @Column(name = "Status")
    private Long status;

    @NotNull
    @Column(name = "AccountID")
    private Long accountId;

    @Transient
    private Account account;

    @Column(name = "CreatedAt")
    private Date createdAt;

    @Column(name = "UpdatedAt")
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    @Override
    public String toString() {
        return "OauthAccessToken{" +
                "id=" + id +
                ", accessToken='" + accessToken + '\'' +
                ", expires=" + expires +
                ", expiredAt=" + expiredAt +
                ", status=" + status +
                ", accountId=" + accountId +
                ", account=" + account +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}