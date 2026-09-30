import { Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class ContactUsPage extends BasePage {
    constructor(page: Page) {
        super(page);
    }

    async isGetInTouchVisible(): Promise<boolean> {
        return this.page.locator("//h2[text()='Get In Touch']").isVisible();
    }

    async fillForm(name: string, email: string, subject: string, message: string, filePath: string) {
        await this.page.locator("input[name='name']").fill(name);
        await this.page.locator("input[name='email']").fill(email);
        await this.page.locator("input[name='subject']").fill(subject);
        await this.page.locator("textarea[name='message']").fill(message);
        await this.page.locator("input[name='upload_file']").setInputFiles(filePath);
    }

    async submitAndAcceptAlert() {
        this.page.once('dialog', async (dialog) => {
            await dialog.accept();
        });
        await this.safeClick(this.page.locator("input[name='submit']"));
    }

    async isSuccessMessageDisplayed(): Promise<boolean> {
        return this.page.locator('.status.alert-success').isVisible();
    }
}