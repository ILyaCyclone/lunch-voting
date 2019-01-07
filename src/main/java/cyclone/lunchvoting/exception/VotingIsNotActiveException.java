package cyclone.lunchvoting.exception;

public class VotingIsNotActiveException extends RuntimeException {
    public VotingIsNotActiveException(String msg) {
        super(msg);
    }
}
