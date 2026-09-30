import { Page } from '@playwright/test';
import { BasePage } from './BasePage';
import { CartPage } from './CartPage';

export class ProductsPage extends BasePage {
    constructor(page: Page) {
        super(page);
    }

    async searchProduct(productName: string) {
        await this.page.locator('#search_product').fill(productName);
        await this.safeClick(this.page.locator('#submit_search'));
    }

    async isSearchedProductsVisible(): Promise<boolean> {
        return this.page.locator("//h2[text()='Searched Products']").isVisible();
    }

    async getSearchResultsCount(): Promise<number> {
        return this.page.locator('.productinfo p').count();
    }

    async addFirstProductToCart() {
        const addToCartButtons = this.page.locator('.productinfo .add-to-cart');
        await addToCartButtons.first().click();
    }

    async viewCartFromModal(): Promise<CartPage> {
        await this.safeClick(this.page.locator("//u[text()='View Cart']"));
        return new CartPage(this.page);
    }
}