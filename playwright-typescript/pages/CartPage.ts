import { Page } from '@playwright/test';
import { BasePage } from './BasePage';
import { CheckoutPage } from './CheckoutPage';

export class CartPage extends BasePage {
    constructor(page: Page) {
        super(page);
    }

    async getCartItemCount(): Promise<number> {
        return this.page.locator('#cart_info_table tbody tr').count();
    }

    async proceedToCheckout(): Promise<CheckoutPage> {
        await this.safeClick(this.page.locator("//a[text()='Proceed To Checkout']"));
        return new CheckoutPage(this.page);
    }
}