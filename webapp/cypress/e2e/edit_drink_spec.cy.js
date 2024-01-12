/// <reference types="cypress" />

describe('edit drink page', () => {
  beforeEach(() => {
    cy.fixture('new_drink.json').as('newDrink')
    cy.intercept('GET', 'drinks', { fixture: 'drinks.json' }).as('getDrinks')
    cy.visit(`http://localhost:${Cypress.env('CLIENT_PORT') || 3000}`)

    // navigate to the edit page
    cy.get('.drink-card').first().click()
    cy.get('.drink-card')
      .first()
      .within(() => {
        cy.get('.icon-edit').click()
      })
  })

  it('should show the correct info', () => {
    cy.get('#drink-name').should('have.value', 'Christmas soda')
    cy.get('.ingredients-list li').should('have.length', 5)

    cy.get('.ingredients-list li').first().click()

    cy.get('#ingredient-name').should('have.value', 'Carbonated water')
    cy.get('#type').should('have.value', 'mixer')
    cy.get('#alcohol-percentage').should('not.exist')
    cy.get('#amount').should('have.value', 4)
    cy.get('#unit').should('have.value', 'dl')
  })

  it('should update the drink', () => {
    cy.get('#drink-name').clear().type('Gin & Tonic')

    // delete all but first ingredient
    for (let i = 0; i < 4; i++) {
      cy.get('.ingredients-list li')
        .last()
        .within(() => {
          cy.get('#delete-ingredient').click()
        })
    }
    cy.get('.ingredients-list li').should('have.length', 1)

    // update the first ingredient
    cy.get('.ingredients-list li').first().click()
    cy.addNormalIngredient()

    cy.addMixerIngredient()

    cy.get('.ingredients-list li').should('have.length', 2)
    cy.get('.ingredients-list li').first().should('contain', 'Gin')
    cy.get('.ingredients-list li').last().should('contain', 'Tonic')

    // simulate a successful PUT request
    cy.get('@newDrink').then((newDrink) => {
      cy.intercept('PUT', 'drinks/**', (req) => {
        req.reply({
          statusCode: 200,
          body: newDrink,
        })
      }).as('editDrink')

      cy.get('#create-btn').click()

      // check that the correct request was made
      cy.wait('@editDrink').then((interception) => {
        expect(interception.request.url).to.match(
          /.*\/drinks\/c5859006-9c3d-3e60-4346-764494f9da52/
        )
        expect(interception.request.body).to.deep.equal(newDrink)
      })
    })

    cy.url().should(
      'eq',
      `http://localhost:${Cypress.env('CLIENT_PORT') || 3000}/`
    )
  })

  it('should show error if id is wrong', () => {
    cy.visit(
      `http://localhost:${Cypress.env('CLIENT_PORT') || 3000}/edit/wrong-id`
    )
    cy.get('.edit-drink-container').contains('Drink is undefined')
  })
})
