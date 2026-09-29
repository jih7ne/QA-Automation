import { Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class LoginPage extends BasePage {
    constructor(page: Page) {
        super(page);
    }

    async login(email: string, password: string) {
        await this.page.locator("input[data-qa='login-email']").fill(email);
        await this.page.locator("input[data-qa='login-password']").fill(password);
        await this.page.locator("button[data-qa='login-button']").click();
    }

    async isLoginErrorDisplayed(): Promise<boolean> {
        return this.page.locator('.login-form p').isVisible();
    }

    async getErrorMessageText(): Promise<string> {
        return this.page.locator('.login-form p').innerText();
    }

    async isLoggedIn(): Promise<boolean> {
        return this.page.locator("//a[contains(text(),'Logged in as')]").isVisible();
    }
}