import { Drink, FieldType, Ingredient, type, unit } from '../types'
import { UseMutationResult, useQueryClient } from '@tanstack/react-query'
import { useAutoAnimate } from '@formkit/auto-animate/react'
import { useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import calculateAlcohol from '../utils/calculateAlcohol'
import useDisableButton from '../utils/useDisableButton'
import IngredientList from './IngredientList'
import Input from './Input'

type Props = {
  submit: UseMutationResult<void, Error, Drink, unknown>
  INIT_VALUES: Omit<Drink, 'id' | 'alcoholContent'>
  id?: string
}

const DrinkForm = ({ submit, INIT_VALUES, id }: Props) => {
  const [name, setName] = useState(INIT_VALUES.name)
  const [ingredientList, setIngredientList] = useState<Ingredient[]>(
    INIT_VALUES.ingredients
  )
  const [ingredient, setIngredient] = useState<Ingredient>({
    name: '',
    alcoholPercentage: 0,
    amount: 0,
    unit: unit.cl,
    type: type.alcohol,
  })
  const [editIndex, setEditIndex] = useState<number | null>(null)
  const disableAdd = useDisableButton(
    ingredient.name === '' ||
      ingredient.amount === 0 ||
      ingredient.amount === null ||
      isNaN(ingredient.amount)
  )
  const disableCreate = useDisableButton(
    name === '' || ingredientList.length === 0
  )
  const [animationRef] = useAutoAnimate<HTMLDivElement>()

  const handleAddIngredient = () => {
    setIngredientList((prev) => [...prev, ingredient])
    setIngredient({
      ...ingredient,
      name: '',
      alcoholPercentage: 0,
      amount: 0,
    })
  }

  const handleEditIngredient = () => {
    // If editIndex is null, the user is not editing an ingredient
    if (editIndex === null) return

    const copy = [...ingredientList]
    copy[editIndex] = ingredient

    setIngredientList(copy)
    setEditIndex(null)
    setIngredient({
      ...ingredient,
      name: '',
      alcoholPercentage: 0,
      amount: 0,
    })
  }

  const queryClient = useQueryClient()
  const location = useLocation()
  const navigate = useNavigate()

  const handleSubmit = () => {
    // ID is only provided when user is editing, otherwise a unique ID is generated
    const newDrink = {
      id: id || crypto.randomUUID(),
      name: name,
      ingredients: ingredientList,
      alcoholContent: calculateAlcohol(ingredientList),
    }

    submit.mutate(newDrink, {
      onSuccess: () => {
        // Updates/adds the drink to the cached data. Improves UX if the server is slow
        queryClient.setQueryData(
          ['drinks'],
          location.pathname.includes('/new')
            ? (prev: Drink[]) => [...prev, newDrink]
            : (prev: Drink[]) =>
                prev.map((drink) => (drink.id === id ? newDrink : drink))
        )

        // Ensures data is synced with server
        queryClient.invalidateQueries({ queryKey: ['drinks'], exact: true })

        navigate('/')
      },
    })
  }

  return (
    <>
      {submit.isError && (
        <p>
          Error {location.pathname.includes('/new') ? 'adding' : 'editing'} the
          drink
        </p>
      )}
      <div className="drink-form">
        <Input
          label="Drink name"
          value={name}
          onChange={(value) => {
            setName(value)
          }}
        />

        <div className="ingredient-box">
          <div className="ingredient-form" ref={animationRef}>
            <h4 style={{ textAlign: 'center' }}>
              {editIndex === null ? 'New' : 'Edit'} ingredient
            </h4>
            <Input
              label="Ingredient name"
              value={ingredient.name}
              onChange={(value) => {
                setIngredient((prev) => ({
                  ...prev,
                  name: value,
                }))
              }}
            />

            <div className="form-input">
              <label htmlFor="type">Type</label>
              <select
                id="type"
                value={ingredient.type}
                onChange={(e) =>
                  setIngredient((prev) => ({
                    ...prev,
                    type: e.target.value,
                  }))
                }
              >
                <option value={type.alcohol} key="alcohol">
                  Alcohol
                </option>
                <option value={type.mixer} key="mixer">
                  Mixer
                </option>
                <option value={type.extras} key="extras">
                  Extras
                </option>
              </select>
            </div>
            {ingredient.type === type.alcohol ? (
              <Input
                type={FieldType.alcohol}
                label="Alcohol percentage"
                value={ingredient.alcoholPercentage || ''}
                onChange={(value) => {
                  setIngredient((prev) => ({
                    ...prev,
                    alcoholPercentage: parseFloat(value),
                  }))
                }}
              />
            ) : (
              ''
            )}

            <div className="amount-box">
              <Input
                type={FieldType.number}
                label="Amount"
                value={ingredient.amount || ''}
                onChange={(value) => {
                  setIngredient((prev) => ({
                    ...prev,
                    amount: parseFloat(value),
                  }))
                }}
              />
              <div className="form-input amount-select">
                <select
                  id="unit"
                  value={ingredient.unit}
                  onChange={(e) =>
                    setIngredient((prev) => ({
                      ...prev,
                      unit: e.target.value,
                    }))
                  }
                >
                  <option value={unit.dl}>dl</option>
                  <option value={unit.cl}>cl</option>
                  <option value={unit.ml}>ml</option>
                  {ingredient.type === type.extras && (
                    <option value={unit.g}>g</option>
                  )}
                </select>
              </div>
            </div>

            <button
              className="btn"
              id="add-ingredient-btn"
              disabled={disableAdd}
              onClick={
                editIndex === null ? handleAddIngredient : handleEditIngredient
              }
            >
              {editIndex === null ? 'Add' : 'Update'}
            </button>
          </div>

          <IngredientList
            ingredients={ingredientList}
            setIngredients={setIngredientList}
            setEditIndex={setEditIndex}
            setIngredient={setIngredient}
          />
        </div>

        <button
          className="btn"
          id="create-btn"
          disabled={disableCreate || submit.status === 'pending'}
          onClick={handleSubmit}
        >
          {submit.status === 'pending'
            ? 'Loading...'
            : location.pathname.includes('/edit/')
            ? 'Update drink'
            : 'Create drink'}
        </button>
      </div>
    </>
  )
}

export default DrinkForm
