package cyclone.lunchvoting.service;


import cyclone.lunchvoting.dto.VotingStatus;
import cyclone.lunchvoting.util.DateTimeUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class VotingStatusService {

    public VotingStatusService(@Value("${cyclone.lunchvoting.voting-ends}") String votingEnds) {
        this.votingEndTime = DateTimeUtils.hhDashMmToLocalTime(votingEnds);
    }

    @Getter
    private final LocalTime votingEndTime;

    public VotingStatus getVotingStatus(LocalTime time) {
        return new VotingStatus(time, votingEndTime, isVotingActive(time));
    }

    public boolean isVotingActive(LocalTime time) {
        return votingEndTime.isAfter(time);
    }
}
