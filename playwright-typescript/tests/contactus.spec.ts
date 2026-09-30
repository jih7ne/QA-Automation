import { test, expect } from '@playwright/test';
import { HomePage } from '../pages/HomePage';
import path from 'path';

test('contact us form submission @TC6', async ({ page }) => {
    const filePath = path.join(__dirname, '..', 'fixtures', 'test-upload.txt');

    const homePage = new HomePage(page);
    await homePage.goto();
    const contactUsPage = await homePage.goToContactUsPage();

    expect(await contactUsPage.isGetInTouchVisible()).toBe(true);

    await contactUsPage.fillForm(
        'Jihane QA',
        'jihane.qa@example.com',
        'Test Subject',
        'This is an automated test message.',
        filePath
    );

    await contactUsPage.submitAndAcceptAlert();

    expect(await contactUsPage.isSuccessMessageDisplayed()).toBe(true);
});