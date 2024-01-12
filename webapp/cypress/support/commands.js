/// <reference types="cypress" />

/**
 * Global commands for tests
 */

Cypress.Commands.add('addNormalIngredient', () => {
  cy.get('#ingredient-name').clear().type('Gin')
  cy.get('#type').select('alcohol')
  cy.get('#alcohol-percentage').clear().type('40')
  cy.get('#amount').clear().type('2')
  cy.get('#unit').select('cl')
  cy.get('#add-ingredient-btn').click()
})

Cypress.Commands.add('addMixerIngredient', () => {
  cy.get('#ingredient-name').clear().type('Tonic')
  cy.get('#type').select('mixer')
  cy.get('#amount').clear().type('2')
  cy.get('#unit').select('dl')
  cy.get('#add-ingredient-btn').click()
})
