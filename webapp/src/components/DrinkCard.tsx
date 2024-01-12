import '../styles/DrinkCard.css'
import { Drink, type } from '../types'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import { useAutoAnimate } from '@formkit/auto-animate/react'
import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import {
  DeleteOutline,
  Edit,
  ExpandLess,
  ExpandMore,
  LocalBar,
} from '@mui/icons-material'
import { deleteDrink } from '../api/drinks'

type Props = {
  content: Drink
  id: string
  expandAll: boolean
}

const DrinkCard = ({ content, id, expandAll }: Props) => {
  const [expand, setExpand] = useState<boolean>(false)
  const [animateRef] = useAutoAnimate<HTMLDivElement>()
  const queryClient = useQueryClient()

  const deleteMutation = useMutation({
    mutationKey: ['deleteDrink'],
    mutationFn: deleteDrink,
    onSuccess: () => {
      // Removes the drink from the cached data. Improves UX if the server is slow
      queryClient.setQueryData(['drinks'], (prev: Drink[]) =>
        [...prev].filter((drink) => drink.id !== id)
      )

      // Ensures that the cached data is synced with the server
      queryClient.invalidateQueries({ queryKey: ['drinks'], exact: true })
    },
  })

  useEffect(() => {
    setExpand(expandAll)
  }, [expandAll])

  return (
    <div
      className={`drink-card type-${
        content.alcoholContent >= 0.7 && 'alcohol'
      }`}
      ref={animateRef}
    >
      <div className="card-title" onClick={() => setExpand(!expand)}>
        <LocalBar fontSize="large" />
        <h3>{content.name}</h3>
        {expand ? (
          <ExpandLess fontSize="medium" />
        ) : (
          <ExpandMore fontSize="medium" />
        )}
      </div>
      {expand && (
        <div className="card-content">
          <div className="card-alcohol-content">
            <h4>Alcohol: </h4>
            <p>
              {Math.round(content.alcoholContent * 10) / 10}%{' '}
              {/* Rounds to 1 or 0 decimals */}
            </p>
          </div>
          <h4>Ingredients:</h4>
          <ul>
            {content.ingredients.map((ingredient, ingredient_index) => (
              <li
                key={'ingredient' + ingredient_index}
                className="ingredient-item"
              >
                {`${ingredient.amount + ingredient.unit} ${ingredient.name} ${
                  ingredient.type === type.alcohol
                    ? Math.round(ingredient.alcoholPercentage * 10) / 10 + '%'
                    : ''
                } `}
              </li>
            ))}
          </ul>
          <div className="icon-box">
            <Link to={`/edit/${id}`} className="icon-edit">
              <Edit fontSize="inherit" />
              <p>EDIT</p>
            </Link>
            <button
              className="icon-delete"
              onClick={() => deleteMutation.mutate(id)}
              disabled={deleteMutation.status === 'pending'}
            >
              <DeleteOutline fontSize="inherit" />
              <p>
                {deleteMutation.status === 'pending' ? 'Loading...' : 'DELETE'}
              </p>
            </button>
          </div>
        </div>
      )}
    </div>
  )
}

export default DrinkCard
