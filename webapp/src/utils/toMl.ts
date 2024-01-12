import { unit } from '../types'

export default (amount: number, u: string): number => {
  switch (u) {
    case unit.cl:
      return amount * 10
    case unit.dl:
      return amount * 100
    case unit.g:
      return 0
    default:
      return amount
  }
}
