import { Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class CheckoutPage extends BasePage {
    constructor(page: Page) {
        super(page);
    }

    async isReviewOrderVisible(): Promise<boolean> {
        return this.page.locator("//h2[text()='Review Your Order']").isVisible();
    }

    async addOrderComment(comment: string) {
        await this.page.locator("textarea[name='message']").fill(comment);
    }

    async clickPlaceOrder() {
        await this.safeClick(this.page.locator("//a[text()='Place Order']"));
    }
}