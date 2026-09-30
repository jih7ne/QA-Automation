import { test, expect } from '@playwright/test';
import { HomePage } from '../pages/HomePage';

test('newsletter subscription @TC10', async ({ page }) => {
    const homePage = new HomePage(page);
    await homePage.goto();

    await homePage.subscribeToNewsletter(`jihane.qa${Date.now()}@example.com`);

    expect(await homePage.isSubscribeSuccessVisible()).toBe(true);
});