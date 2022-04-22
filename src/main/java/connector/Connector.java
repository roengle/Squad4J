package connector;

public abstract class Connector {
    private final String connectorName;

    protected Connector(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getConnectorName() {
        return connectorName;
    }
}
