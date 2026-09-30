import { Page } from '@playwright/test';
import { BasePage } from './BasePage';
import { HomePage } from './HomePage';

export class AccountInfoPage extends BasePage {
    constructor(page: Page) {
        super(page);
    }

    async fillAndSubmit(
        password: string,
        firstName: string,
        lastName: string,
        address: string,
        country: string,
        state: string,
        city: string,
        zipcode: string,
        mobile: string
    ) {
        await this.page.locator('#id_gender1').check();
        await this.page.locator('#password').fill(password);

        await this.page.locator('#days').selectOption('10');
        await this.page.locator('#months').selectOption({ label: 'May' });
        await this.page.locator('#years').selectOption('1998');

        await this.page.locator('#first_name').fill(firstName);
        await this.page.locator('#last_name').fill(lastName);
        await this.page.locator('#address1').fill(address);
        await this.page.locator('#country').selectOption({ label: country });
        await this.page.locator('#state').fill(state);
        await this.page.locator('#city').fill(city);
        await this.page.locator('#zipcode').fill(zipcode);
        await this.page.locator('#mobile_number').fill(mobile);

        await this.safeClick(this.page.locator("button[data-qa='create-account']"));
    }

    async isAccountCreated(): Promise<boolean> {
        return this.page.locator("h2[data-qa='account-created']").isVisible();
    }

    async clickContinue(): Promise<HomePage> {
        await this.safeClick(this.page.locator("[data-qa='continue-button']"));
        return new HomePage(this.page);
    }
}