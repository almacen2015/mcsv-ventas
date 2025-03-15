package backend.mcsvventas.exceptions;

public class VentaException extends RuntimeException {
    public static final String CLIENT_ID_INVALID = "Client id invalid";
    public static final String PRODUCT_ID_INVALID = "Product id invalid";
    public static final String DETAILS_INVALID = "Details invalid";
    public static final String QUANTITY_INVALID = "Quantity invalid";

    public VentaException(String message) {
        super(message);
    }
}
