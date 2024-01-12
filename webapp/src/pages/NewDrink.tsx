import '../styles/DrinkForm.css'
import { useMutation } from '@tanstack/react-query'
import { createDrink } from '../api/drinks'
import DrinkForm from '../components/DrinkForm'

const NewDrink = () => {
  const INIT_VALUES = {
    name: '',
    ingredients: [],
  }

  const createDrinkMutation = useMutation({
    mutationKey: ['createDrink'],
    mutationFn: createDrink,
  })

  return (
    <>
      <div className="new-drink-container">
        <h2>Create a new drink</h2>
        <DrinkForm submit={createDrinkMutation} INIT_VALUES={INIT_VALUES} />
      </div>
    </>
  )
}

export default NewDrink
