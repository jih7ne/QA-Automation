package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;
import org.openqa.selenium.WebElement;

public class ProductsPage extends BasePage {

    private By searchInput = By.id("search_product");
    private By searchButton = By.id("submit_search");
    private By searchedProductsTitle = By.xpath("//h2[text()='Searched Products']");
    private By productItems = By.cssSelector(".productinfo p");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void searchProduct(String productName) {
        type(searchInput, productName);
        click(searchButton);
    }

    public boolean isSearchedProductsVisible() {
        return isDisplayed(searchedProductsTitle);
    }

    public int getSearchResultsCount() {
        return driver.findElements(productItems).size();
    }
    private By productsLink = By.linkText("Products");

    public ProductsPage goToProductsPage() {
        click(productsLink);
        return new ProductsPage(driver);
    }
    private By addToCartButtons = By.cssSelector(".productinfo .add-to-cart");
    private By viewCartLinkInModal = By.xpath("//u[text()='View Cart']");

    public void addFirstProductToCart() {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        buttons.get(0).click();
    }

    public CartPage viewCartFromModal() {
        click(viewCartLinkInModal);
        return new CartPage(driver);
    }
}