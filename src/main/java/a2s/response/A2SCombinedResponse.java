package a2s.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class describing a "combined" A2S response. A combined A2S response contains a {@link A2SInfoResponse} and
 * a {@link A2SRulesResponse}.
 *
 * @see A2SInfoResponse
 * @see A2SRulesResponse
 *
 * @author Robert Engle
 */
@AllArgsConstructor
@Getter
public class A2SCombinedResponse {
    private final A2SInfoResponse info;
    private final A2SRulesResponse rules;
}
