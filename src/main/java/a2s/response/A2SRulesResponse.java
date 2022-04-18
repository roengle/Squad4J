package a2s.response;

import util.BufferHelper;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Robert Engle
 *
 * https://github.com/roengle/squadQuery/blob/main/src/response/A2SRulesResponse.java
 *
 * Class describing a response from an A2S_RULES query.
 *
 * For technical documentation on A2S_RULES queries, see the following:
 *
 * https://developer.valvesoftware.com/wiki/Server_queries#A2S_RULES
 */
public class A2SRulesResponse extends Response{
    private final Byte numRules;
    private final Map<String, String> ruleMap = new LinkedHashMap<>();

    public A2SRulesResponse(byte[] rawData) {
        super(rawData);

        ByteBuffer buffer = ByteBuffer.wrap(rawData);
        buffer.get();
        buffer.get();
        buffer.get();
        buffer.get();

        buffer.get();

        numRules = buffer.get();
        buffer.get();
        for(int i = 0; i < numRules; i++){
            String name = BufferHelper.getStringFromBuffer(buffer);
            String value = BufferHelper.getStringFromBuffer(buffer);

            ruleMap.put(name, value);
        }
    }

    public static A2SRulesResponse from(byte[] receivedData) {
        return new A2SRulesResponse(receivedData);
    }

    public Byte getNumRules() {
        return numRules;
    }

    public String getRuleValue(String rule){
        return ruleMap.get(rule);
    }

    /**
     * Gets entry set for the rules. Is useful for getting all rules.
     *
     * @return a {@link Set<Map.Entry<String, String>>} of the rules returned by A2S_RULES query
     */
    public Set<Map.Entry<String, String>> getRuleEntrySet(){
        return ruleMap.entrySet();
    }

    @Override
    public String toString() {
        AtomicReference<String> rules = new AtomicReference<>("{");
        ruleMap.forEach((k, v) -> {
            rules.set(rules.get() + k + " : " + v + ", ");
        });
        rules.set(rules.get().substring(0, rules.get().length() - 2));
        rules.set(rules.get() + "}");

        return new StringJoiner(", ", A2SRulesResponse.class.getSimpleName() + "[", "]")
                .add("numRules=" + numRules)
                .add("ruleMap=" + rules)
                .toString();
    }
}