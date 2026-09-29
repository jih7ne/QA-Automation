import { Page, Locator } from '@playwright/test';

export class BasePage {
    protected page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    async blockAdRequests() {
        const adDomains = [
            'doubleclick.net',
            'googlesyndication.com',
            'googleadservices.com',
            'google-analytics.com',
            'googletagservices.com',
            'adservice.google.com',
            'pagead2.googlesyndication.com',
        ];

        await this.page.route('**/*', (route) => {
            const url = route.request().url();
            if (adDomains.some(domain => url.includes(domain))) {
                route.abort();
            } else {
                route.continue();
            }
        });
    }

    async acceptCookieConsentIfPresent() {
        try {
            const allowButton = this.page.locator('button.fc-cta-consent');
            await allowButton.click({ timeout: 5000 });
        } catch {
            // No consent dialog appeared — safe to ignore
        }
    }
}