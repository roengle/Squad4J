package a2s.response;

public class A2SCombinedResponse {
    private A2SInfoResponse info;
    private A2SRulesResponse rules;

    public A2SCombinedResponse(A2SInfoResponse info, A2SRulesResponse rules){
        this.info = info;
        this.rules = rules;
    }

    public A2SInfoResponse getInfo() {
        return info;
    }

    public A2SRulesResponse getRules() {
        return rules;
    }
}
