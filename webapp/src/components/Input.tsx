import { useState } from 'react'
import { FieldType } from '../types'

type Props = {
  type?: FieldType
  label: string
  value: string | number
  onChange: (e: string) => void
}

const Input = ({ type = FieldType.text, label, value, onChange }: Props) => {
  const [isError, setError] = useState(false)
  const [errorMsg, setErrorMsg] = useState('Invalid Field')
  const [currentTimeout, setCurrentTimeOut] = useState<NodeJS.Timeout>()
  const ERROR_TIMEOUT = 1000

  const validateNumber = (val: string): boolean => {
    const regex = /^\d*\.?\d{0,1}$/ // Only numbers, max one dot and max 1 decimal
    return (
      !(type === FieldType.number || type === FieldType.alcohol) ||
      regex.test(val)
    )
  }

  const validateAlcohol = (val: string): boolean => {
    return !(type === FieldType.alcohol && parseInt(val) > 100)
  }

  const setTimeoutError = (msg: string) => {
    setError(true)
    setErrorMsg(msg)
    clearTimeout(currentTimeout)
    setCurrentTimeOut(setTimeout(() => setError(false), ERROR_TIMEOUT))
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.value === '' && type !== FieldType.alcohol) {
      setError(true)
      setErrorMsg('This field is required')
    } else {
      setError(false)
      clearTimeout(currentTimeout)
    }

    if (!validateNumber(e.target.value)) {
      setTimeoutError('This field must be a postive number and max one decimal')
      return
    }

    if (!validateAlcohol(e.target.value)) {
      setTimeoutError('This field cannot be greater than 100')
      return
    }

    onChange(e.target.value)
  }

  return (
    <div className="form-input">
      <input
        id={label.toLowerCase().replace(' ', '-')}
        type="text"
        value={value}
        required
        onChange={handleChange}
      />
      <span>{label}</span>
      <p
        className={`invalidMsg ${isError && 'visible'}`}
        id={label.toLowerCase().replace(' ', '-') + '-error'}
      >
        {errorMsg}
      </p>
    </div>
  )
}

export default Input
