import { Page } from '@playwright/test';
import { BasePage } from './BasePage';
import { LoginPage } from './LoginPage';
import { SignupPage } from './SignupPage';
import { ContactUsPage } from './ContactUsPage';
import { ProductsPage } from './ProductsPage';

export class HomePage extends BasePage {
    constructor(page: Page) {
        super(page);
    }

    async goto() {
        await this.blockAdRequests();
        await this.page.goto('https://automationexercise.com');
        await this.acceptCookieConsentIfPresent();
    }

    async goToLoginPage(): Promise<LoginPage> {
        await this.safeClick(this.page.locator("a[href='/login']"));
        return new LoginPage(this.page);
    }

    async goToSignupPage(): Promise<SignupPage> {
        await this.safeClick(this.page.locator("a[href='/login']"));
        return new SignupPage(this.page);
    }

    async goToContactUsPage(): Promise<ContactUsPage> {
        await this.safeClick(this.page.locator("a[href='/contact_us']"));
        return new ContactUsPage(this.page);
    }

    async goToProductsPage(): Promise<ProductsPage> {
        await this.safeClick(this.page.locator("a[href='/products']"));
        return new ProductsPage(this.page);
    }

    async isLoggedInAsVisible(): Promise<boolean> {
        return this.page.locator("//a[contains(text(),'Logged in as')]").isVisible();
    }

    async deleteAccountAndVerify() {
        await this.safeClick(this.page.locator("a[href='/delete_account']"));
        const heading = this.page.locator("h2[data-qa='account-deleted']");
        if (!(await heading.isVisible())) {
            throw new Error("Expected 'ACCOUNT DELETED!' message after deletion, but it wasn't shown.");
        }
    }

    async subscribeToNewsletter(email: string) {
        const emailField = this.page.locator('#susbscribe_email');
        await emailField.scrollIntoViewIfNeeded();
        await emailField.fill(email);
        await this.safeClick(this.page.locator('#subscribe'));
    }

    async isSubscribeSuccessVisible(): Promise<boolean> {
        return this.page.locator('.alert-success').isVisible();
    }
}