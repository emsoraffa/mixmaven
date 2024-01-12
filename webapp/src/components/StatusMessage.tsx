import '../styles/StatusMessage.css'
import { UseQueryResult } from '@tanstack/react-query'
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline'
import ReactLoading from 'react-loading'

const StatusMessage = ({ query }: { query: UseQueryResult }) => (
  <div className="status-container">
    {query.isPending || query.isLoading ? (
      <ReactLoading
        type={'bubbles'}
        color={'#222'}
        height={'7%'}
        width={'7%'}
      />
    ) : (
      <div className="status-error">
        <p className="status-error-title">
          <ErrorOutlineIcon fontSize="inherit" />
          {query.error?.name}
        </p>
        <p>{query.error?.message}</p>
      </div>
    )}
  </div>
)

export default StatusMessage
