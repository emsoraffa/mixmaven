/// <reference types="cypress" />

describe('new drink page', () => {
  beforeEach(() => {
    cy.fixture('new_drink.json').as('newDrink')
    cy.intercept('GET', 'drinks', { fixture: 'drinks.json' }).as('getDrinks')
    cy.visit(`http://localhost:${Cypress.env('CLIENT_PORT') || 3000}/new`)
  })

  it('should be possible to add a normal drink', () => {
    cy.get('#drink-name').type('Gin & Tonic')
    cy.addNormalIngredient()
    cy.addMixerIngredient()

    // simulate a successful POST request
    cy.get('@newDrink').then((newDrink) => {
      cy.intercept('POST', 'drinks', (req) => {
        req.reply({
          statusCode: 201,
          body: newDrink,
        })
      }).as('createDrink')

      cy.get('#create-btn').click()

      cy.wait('@createDrink').then((interception) => {
        expect(interception.request.url).to.match(/.*\/drinks/)
        expect(Cypress._.omit(interception.request.body, ['id'])).to.deep.equal(
          Cypress._.omit(newDrink, ['id'])
        )
      })
    })

    cy.url().should(
      'eq',
      `http://localhost:${Cypress.env('CLIENT_PORT') || 3000}/`
    )
  })

  context('check that buttons are disabled properly', () => {
    beforeEach(() => {
      cy.get('.btn').each(($btn) => {
        cy.wrap($btn).should('be.disabled')
      })
    })

    it('disables/enables add ingredient button correctly', () => {
      cy.get('#ingredient-name').type('Vodka')

      cy.get('#add-ingredient-btn').should('be.disabled')

      cy.get('#amount').type('5')
      cy.get('#add-ingredient-btn').should('be.enabled')

      cy.get('#alcohol-percentage').type('40')
      cy.get('#add-ingredient-btn').should('be.enabled')

      cy.get('#amount').clear()
      cy.get('#add-ingredient-btn').should('be.disabled')

      cy.get('#amount').type('5')
      cy.get('#ingredient-name').clear()
      cy.get('#add-ingredient-btn').should('be.disabled')
    })

    it('disables/enables create drink button correctly', () => {
      cy.get('#drink-name').type('My very good drink')
      cy.get('#create-btn').should('be.disabled')

      cy.addNormalIngredient()
      cy.get('#create-btn').should('be.enabled')

      cy.get('.ingredients-list li')
        .first()
        .within(() => {
          cy.get('button').click()
        })
      cy.get('#create-btn').should('be.disabled')
    })
  })

  context('check input functionality', () => {
    it('should display errors when fields are wrong', () => {
      cy.get('#drink-name').type('GT').clear()
      cy.wait(300)
      cy.get('#drink-name-error')
        .should('have.class', 'visible')
        .should('have.text', 'This field is required')

      cy.get('#ingredient-name').type('Gin').clear()
      cy.wait(300)
      cy.get('#ingredient-name-error')
        .should('have.class', 'visible')
        .should('have.text', 'This field is required')

      cy.get('#alcohol-percentage').type('a')
      cy.wait(300)
      cy.get('#alcohol-percentage-error')
        .should('have.class', 'visible')
        .should(
          'have.text',
          'This field must be a postive number and max one decimal'
        )

      cy.get('#alcohol-percentage').type('101')
      cy.wait(300)
      cy.get('#alcohol-percentage-error').should(
        'have.text',
        'This field cannot be greater than 100'
      )

      cy.get('#alcohol-percentage').clear()
      cy.wait(300)
      cy.get('#alcohol-percentage-error').should('not.have.class', 'visible')

      cy.get('#amount').type('a')
      cy.wait(300)
      cy.get('#amount-error')
        .should('have.class', 'visible')
        .should(
          'have.text',
          'This field must be a postive number and max one decimal'
        )
      cy.get('#amount').type('1').clear()
      cy.wait(300)
      cy.get('#amount-error')
        .should('have.class', 'visible')
        .should('have.text', 'This field is required')
    })

    it('should be possible to add/remove/edit ingredients', () => {
      cy.get('.ingredients-list li').should('have.text', 'So empty...')

      cy.addNormalIngredient()
      cy.get('.ingredients-list li')
        .should('have.length', 1)
        .first()
        .contains('Gin')

      cy.addMixerIngredient()
      cy.get('.ingredients-list li')
        .should('have.length', 2)
        .last()
        .contains('Tonic')

      it('should be possible to edit ingredients')
      it('should be possible to remove ingredients')
    })
  })
})
