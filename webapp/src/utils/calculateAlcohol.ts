import { Ingredient } from './../types'
import toMl from './toMl'

export default (ingredientList: Ingredient[]): number => {
  let totalAmountMl = 0
  let totalAlcoholMl = 0

  ingredientList.forEach((ingredient) => {
    totalAmountMl += toMl(ingredient.amount, ingredient.unit)
    totalAlcoholMl +=
      toMl(ingredient.amount, ingredient.unit) *
      (ingredient.alcoholPercentage / 100)
  })

  return (totalAlcoholMl / totalAmountMl) * 100
}
