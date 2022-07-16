package server;

public class LayerClassnameFormatter {

    private LayerClassnameFormatter(){
        throw new IllegalStateException("Utility classes cannot be instantiated");
    }

    public static String formatMap(final String name){
        //TODO: Use dictionary lookup since Squad's layer naming convention is too ambiguous
        String returnVal = name.replaceAll("[0-9\\-]", "");
        returnVal = returnVal.replace(" ", "_");
        return returnVal;
    }

    public static String formatLayer(final String layerName) {
        //TODO: Implementation
        return "";
    }
}
