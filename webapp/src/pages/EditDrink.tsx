import { Drink } from '../types'
import {
  useMutation,
  useQueryClient,
  UseQueryResult,
} from '@tanstack/react-query'
import { useParams } from 'react-router-dom'
import { editDrink } from '../api/drinks'
import StatusMessage from '../components/StatusMessage'
import DrinkForm from '../components/DrinkForm'

const EditDrink = ({ query }: { query: UseQueryResult<Drink[], Error> }) => {
  const { id } = useParams()
  const queryClient = useQueryClient()

  const drink = ((queryClient.getQueryData(['drinks']) as Array<Drink>) || []) // Empty array avoids error if query is loading
    .find((drink) => drink.id === id)

  const editMutation = useMutation({
    mutationKey: ['editDrink'],
    mutationFn: editDrink,
  })

  return (
    <div className="edit-drink-container">
      <h2>Edit drink</h2>
      {query.isPending || query.isLoading || query.isError ? (
        <StatusMessage query={query} />
      ) : drink === undefined ? (
        <p>Drink is undefined</p>
      ) : (
        <DrinkForm submit={editMutation} INIT_VALUES={drink} id={id} />
      )}
    </div>
  )
}

export default EditDrink
