package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductsPage;
import io.qameta.allure.*;

@Epic("Automation Exercise - Core Flows")
@Feature("Product Search")
public class SearchProductTests extends BaseTest {

	@Test
	@Story("Search Product")
	@Severity(SeverityLevel.NORMAL)
	@Description("Verifies searching for a product returns results")
	public void testSearchProduct() {
	    HomePage homePage = new HomePage(driver);
	    ProductsPage productsPage = homePage.goToProductsPage();

	    productsPage.searchProduct("Dress");

	    Assert.assertTrue(productsPage.isSearchedProductsVisible(),
	            "Expected 'Searched Products' heading after search.");
	    Assert.assertTrue(productsPage.getSearchResultsCount() > 0,
	            "Expected at least one search result for 'Dress'.");
	}
}