package cyclone.lunchvoting.exception;

public class VotingIsNotFinishedException extends RuntimeException {
    public VotingIsNotFinishedException(String msg) {
        super(msg);
    }
}
