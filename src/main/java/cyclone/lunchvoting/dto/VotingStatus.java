package cyclone.lunchvoting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotingStatus {
    private LocalTime serverTime;
    private LocalTime votingEndTime;
    private boolean votingActive;
}
