package nl.quintor._security.model.dto;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class FriendDTO {

    @Positive
    private long userId;
}
