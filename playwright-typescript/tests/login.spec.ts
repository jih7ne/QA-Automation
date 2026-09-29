import { test, expect } from '@playwright/test';
import { HomePage } from '../pages/HomePage';

test.describe('Authentication', () => {

    test('valid login succeeds @TC2', async ({ page }) => {
        const homePage = new HomePage(page);
        await homePage.goto();
        const loginPage = await homePage.goToLoginPage();

        await loginPage.login('jih7ne@gmail.com', 'YourCorrectPassword123');

        expect(await loginPage.isLoggedIn()).toBe(true);
    });

    test('invalid login shows error @TC3', async ({ page }) => {
        const homePage = new HomePage(page);
        await homePage.goto();
        const loginPage = await homePage.goToLoginPage();

        await loginPage.login('nonexistent_user@example.com', 'WrongPassword999');

        expect(await loginPage.isLoginErrorDisplayed()).toBe(true);
        expect(await loginPage.getErrorMessageText()).toBe('Your email or password is incorrect!');
    });

});