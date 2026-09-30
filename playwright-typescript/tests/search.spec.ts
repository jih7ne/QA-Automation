import { test, expect } from '@playwright/test';
import { HomePage } from '../pages/HomePage';

test('search product @TC9', async ({ page }) => {
    const homePage = new HomePage(page);
    await homePage.goto();
    const productsPage = await homePage.goToProductsPage();

    await productsPage.searchProduct('Dress');

    expect(await productsPage.isSearchedProductsVisible()).toBe(true);
    expect(await productsPage.getSearchResultsCount()).toBeGreaterThan(0);
});