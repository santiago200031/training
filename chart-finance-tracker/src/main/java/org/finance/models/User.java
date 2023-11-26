package org.finance.models;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class User {
    @SerializedName("api_key")
    private String apiKey;

    private UUID activityId;
}