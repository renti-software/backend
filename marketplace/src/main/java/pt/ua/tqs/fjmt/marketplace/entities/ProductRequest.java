package pt.ua.tqs.fjmt.marketplace.entities;

public class ProductRequest extends AuthenticatedRequest {

    private Product product;

    public ProductRequest(Authenticator authenticator, Product product) {
        super(authenticator);
        this.product = product;
    }

    public ProductRequest() {
    }

    public void setProduct(Product product) {
        this.product = product;
    } 

    public Product getProduct() {
        return product;
    }
    
}