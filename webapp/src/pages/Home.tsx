import { useState } from 'react'
import { ExpandLess, ExpandMore, Straight } from '@mui/icons-material'
import { useAutoAnimate } from '@formkit/auto-animate/react'
import { UseQueryResult } from '@tanstack/react-query'
import { Drink } from '../types'
import StatusMessage from '../components/StatusMessage'
import DrinkCard from '../components/DrinkCard'
import '../styles/Home.css'

const Home = ({ query }: { query: UseQueryResult<Drink[], Error> }) => {
  const [sortAlpha, setSortAlpha] = useState(true)
  const [filterAlcohol, setFilterAlcohol] = useState(true)
  const [filterNonAlcohol, setFilterNonAlcohol] = useState(true)
  const [expandAll, setExpandAll] = useState(false)
  const [disableBtn, setDisableBtn] = useState(false)
  const [animationRef] = useAutoAnimate<HTMLDivElement>()

  const controlFilter = (drink: Drink): boolean =>
    (filterAlcohol || drink.alcoholContent < 0.7) &&
    (filterNonAlcohol || drink.alcoholContent >= 0.7)

  return (
    <>
      <div className="title-box">
        <h2>Drinks</h2>
        <div className="controls">
          <label className="checkbox alcohol" id="filter-alcoholic">
            <input
              type="checkbox"
              defaultChecked={true}
              onChange={() => setFilterAlcohol(!filterAlcohol)}
            />
            <div className="checkbox-checkmark" />
            <p>Alcoholic</p>
          </label>

          <label className="checkbox" id="filter-non-alcoholic">
            <input
              type="checkbox"
              defaultChecked={true}
              onChange={() => setFilterNonAlcohol(!filterNonAlcohol)}
            />
            <div className="checkbox-checkmark" />
            <p>Non-alcoholic</p>
          </label>

          <div className="expand-all">
            <button
              disabled={disableBtn}
              onClick={() => {
                setExpandAll(!expandAll)

                // Let the animation finish before allowing another click to avoid buggy behaviour
                setDisableBtn(true)
                setTimeout(setDisableBtn, 500, false)
              }}
            >
              <p>{!expandAll ? 'Expand' : 'Collapse'} all</p>
              <div className={expandAll ? 'collapse' : 'expand'}>
                <ExpandLess />
                <ExpandMore />
              </div>
            </button>
          </div>

          <div className="sort">
            <button onClick={() => setSortAlpha(!sortAlpha)}>
              <Straight
                fontSize="inherit"
                className={sortAlpha ? 'alpha' : ''}
              />
              <div>
                <span>A</span>
                <span>Z</span>
              </div>
            </button>
          </div>
        </div>
      </div>

      <div className="drink-box" ref={animationRef}>
        {query.status === 'pending' || query.status === 'error' ? (
          <StatusMessage query={query} />
        ) : query.data.filter(controlFilter).length === 0 ? (
          <p className="nothing">Nothing here..</p>
        ) : (
          query.data
            .filter(controlFilter)
            .sort((a, b) =>
              sortAlpha
                ? a.name.localeCompare(b.name)
                : b.name.localeCompare(a.name)
            )
            .map((drink) => (
              <DrinkCard
                content={drink}
                id={drink.id}
                key={'drink_' + drink.id}
                expandAll={expandAll}
              />
            ))
        )}
      </div>
    </>
  )
}

export default Home
