import { Page } from '@playwright/test';
import { BasePage } from './BasePage';
import { LoginPage } from './LoginPage';

export class HomePage extends BasePage {
    constructor(page: Page) {
        super(page);
    }

    async goto() {
        await this.page.goto('https://automationexercise.com');
        await this.acceptCookieConsentIfPresent();
    }

    async goToLoginPage(): Promise<LoginPage> {
        await this.page.locator("a[href='/login']").click();
        return new LoginPage(this.page);
    }
}