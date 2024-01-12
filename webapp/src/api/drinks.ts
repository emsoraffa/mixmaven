import { Drink } from '../types'

/**
 * Allows enviroment variables to change the url
 * Default: http://localhost:8000
 */
const baseURL =
  import.meta.env.VITE_CHE_LINK ||
  `http://${import.meta.env.VITE_SERVER_IP || 'localhost'}:${
    import.meta.env.VITE_SERVER_PORT || 8000
  }`

export const getDrinks = async (): Promise<Drink[]> => {
  return fetch(`${baseURL}/drinks`, { method: 'GET' })
    .then((res) => res.json())
    .catch((e) => console.log(e))
}

export const deleteDrink = async (id: string) => {
  await fetch(`${baseURL}/drinks/${id}`, {
    method: 'DELETE',
  }).catch((err) => console.log(err))
}

export const createDrink = async (drink: Drink) => {
  await fetch(`${baseURL}/drinks`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(drink),
  }).catch((err) => console.log(err))
}

export const editDrink = async (drink: Drink) => {
  await fetch(`${baseURL}/drinks/${drink.id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(drink),
  }).catch((err) => console.log(err))
}
