import { defineConfig } from 'cypress'

export default defineConfig({
  viewportHeight: 1050,
  viewportWidth: 1400,
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
})
