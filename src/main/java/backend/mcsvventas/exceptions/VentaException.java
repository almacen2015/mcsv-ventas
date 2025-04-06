package backend.mcsvventas.exceptions;

public class VentaException extends RuntimeException {
    public static final String CLIENT_ID_INVALID = "Client id invalid";
    public static final String PRODUCT_ID_INVALID = "Product id invalid";
    public static final String DETAILS_INVALID = "Details invalid";
    public static final String QUANTITY_INVALID = "Quantity invalid";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String QUANTITY_GREATER_THAN_STOCK = "Quantity is greater than stock";
    public static final String PRODUCT_REPEATED = "Product repeated";
    public static final String PAGE_NUMBER_INVALID = "Page number can't be less or equals than 0";
    public static final String SIZE_NUMBER_INVALID = "Size number can't be less or equals than 0";
    public static final String SORT_NAME_INVALID = "Sort name is invalid";

    public VentaException(String message) {
        super(message);
    }
}
