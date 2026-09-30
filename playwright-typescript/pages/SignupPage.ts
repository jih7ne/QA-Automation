import { Page } from '@playwright/test';
import { BasePage } from './BasePage';
import { AccountInfoPage } from './AccountInfoPage';

export class SignupPage extends BasePage {
    constructor(page: Page) {
        super(page);
    }

    async signup(name: string, email: string): Promise<AccountInfoPage> {
        await this.page.locator("input[data-qa='signup-name']").fill(name);
        await this.page.locator("input[data-qa='signup-email']").fill(email);
        await this.safeClick(this.page.locator("button[data-qa='signup-button']"));
        return new AccountInfoPage(this.page);
    }
}