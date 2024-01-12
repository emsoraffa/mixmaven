import { useEffect, useState } from 'react'

export default (condition: boolean): boolean => {
  const [disabled, setDisabled] = useState(true)

  useEffect(() => {
    setDisabled(condition)
  }, [condition])

  return disabled
}
