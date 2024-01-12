export type Drink = {
  id: string
  name: string
  ingredients: Ingredient[]
  alcoholContent: number
}

export type Ingredient = {
  name: string
  alcoholPercentage: number
  amount: number
  unit: unit | string
  type: unit | string
}

export enum unit {
  dl = 'dl',
  cl = 'cl',
  ml = 'ml',
  g = 'g',
}

export enum type {
  alcohol = 'alcohol',
  mixer = 'mixer',
  extras = 'extras',
}

export enum FieldType {
  text = 'text',
  number = 'number',
  alcohol = 'alcohol',
}
