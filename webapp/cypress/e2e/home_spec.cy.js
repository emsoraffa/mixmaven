/// <reference types="cypress" />

describe('home page', () => {
  beforeEach(() => {
    cy.intercept('GET', 'drinks', { fixture: 'drinks.json' }).as('getDrinks')
    cy.visit(`http://localhost:${Cypress.env('CLIENT_PORT') || 3000}`)
  })

  context('layout', () => {
    it('checks that elments are on the page', () => {
      cy.get('h1').should('have.text', 'MiXMaven')
      cy.get('nav li')
        .should('have.length', 2)
        .should('contain', 'Home')
        .should('contain', 'New drink')
      cy.get('.layout-footer').should('exist')
    })

    it('should navigate correctly', () => {
      cy.get('nav li').last().click()
      cy.url().should('include', '/new')
      cy.get('nav li').first().click()
      cy.url().should(
        'eq',
        `http://localhost:${Cypress.env('CLIENT_PORT') || 3000}/`
      )
    })

    it('should fetch drinks and display them correctly', () => {
      cy.get('.drink-box .drink-card').should('have.length', 3)

      cy.get('.drink-box .drink-card')
        .last()
        .within(() => {
          cy.get('.card-content').should('not.exist')
        })

      cy.get('.drink-box .drink-card').last().click()

      cy.get('.drink-box .drink-card')
        .last()
        .within(() => {
          cy.get('.card-content').should('exist')
        })
    })

    /**
     * PS: it does not actually delete the drink,
     * it only checks that the correct request is made
     */
    it('should be possible to delete a drink', () => {
      cy.intercept('DELETE', 'drinks/**', (req) => {
        req.reply({ statusCode: 200 })
      }).as('deleteDrink')

      cy.get('.drink-card').last().click()
      cy.get('.drink-card')
        .last()
        .within(() => {
          cy.get('.icon-delete').click()
        })
      cy.wait('@deleteDrink').then((interception) => {
        expect(interception.request.url).to.match(
          /.*\/drinks\/c5859006-9c3d-3e60-4346-764494f9da51/
        )
      })
    })
  })

  context('controls', () => {
    it('should filter alcoholic/non-alcoholic', () => {
      cy.get('.drink-card').should('have.length', 3)

      cy.get('#filter-alcoholic').click()
      cy.get('.drink-card').should('have.length', 1)

      cy.get('#filter-non-alcoholic').click()
      cy.get('.drink-card').should('have.length', 0)

      cy.get('#filter-alcoholic').click()
      cy.get('.drink-card').should('have.length', 2)
    })

    it('should expand/collapse all', () => {
      cy.get('.drink-card').each(($card) => {
        cy.wrap($card).find('.card-content').should('not.exist')
      })

      cy.get('.expand-all').click()
      cy.get('.drink-card').each(($card) => {
        cy.wrap($card).find('.card-content').should('exist')
      })

      cy.wait(600) // wait for animation to finish

      cy.get('.expand-all').click()
      cy.get('.drink-card').each(($card) => {
        cy.wrap($card).find('.card-content').should('not.exist')
      })
    })

    it('should sort the list alphabetically', () => {
      const alphabetically = ['Christmas soda', 'GT', 'Vodka shot']
      const reverse = [...alphabetically].reverse()

      cy.get('.drink-card').each(($card, index) => {
        cy.wrap($card)
          .find('.card-title')
          .should('have.text', alphabetically[index])
      })
      cy.get('.sort').click()
      cy.get('.drink-card').each(($card, index) => {
        cy.wrap($card).find('.card-title').should('have.text', reverse[index])
      })
    })
  })
})
