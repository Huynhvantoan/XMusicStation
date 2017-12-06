
package com.nct.xmusicstation.data.model.auth;

import com.google.gson.annotations.SerializedName;
import com.nct.xmusicstation.model.UserAccount;

/**
 * Created by Toan.IT on 4/22/15.
 * Email:Huynhvantoan.itc@gmail.com
 */
public class TokenInfo {

    public static final TokenInfo EMPTY_TOKEN = new TokenInfo();

    public String token;
    public UserAccount user;
    @SerializedName("expires_at")
    public long expiresAt;

}
