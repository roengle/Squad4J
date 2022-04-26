package a2s.response;

/**
 * @author Robert Engle
 *
 * Class describing a "combined" A2S response. A combined A2S response contains a {@link A2SInfoResponse} and
 * a {@link A2SRulesResponse}.
 *
 * @see A2SInfoResponse
 * @see A2SRulesResponse
 */
public class A2SCombinedResponse {
    private final A2SInfoResponse info;
    private final A2SRulesResponse rules;

    /**
     * Constructs a {@link A2SCombinedResponse}.
     *
     * @param info the {@link A2SInfoResponse} for this response
     * @param rules the {@link A2SRulesResponse} for this response
     */
    public A2SCombinedResponse(A2SInfoResponse info, A2SRulesResponse rules){
        this.info = info;
        this.rules = rules;
    }

    /**
     * Gets the {@link A2SInfoResponse} for this response.
     *
     * @return the {@link A2SInfoResponse} for this response
     */
    public A2SInfoResponse getInfo() {
        return info;
    }

    /**
     * Gets the {@link A2SRulesResponse} for this response.
     *
     * @return the {@link A2SRulesResponse} for this response
     */
    public A2SRulesResponse getRules() {
        return rules;
    }
}
