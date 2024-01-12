import '../styles/Layout.css'
import { FavoriteBorderOutlined } from '@mui/icons-material'
import { Link, NavLink, Outlet } from 'react-router-dom'

const Layout = () => {
  return (
    <div className="layout-container">
      <nav>
        <Link to="/">
          <h1>MiXMaven</h1>
        </Link>
        <ul>
          <li>
            <NavLink to="/">Home</NavLink>
          </li>
          <li>
            <NavLink to="/new">New drink</NavLink>
          </li>
        </ul>
      </nav>

      <div className="layout-content">
        <Outlet />
      </div>

      <div className="layout-footer">
        <a
          href="https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2331/gr2331"
          target="_blank"
        >
          Made with <FavoriteBorderOutlined /> by GR2331
        </a>
      </div>
    </div>
  )
}

export default Layout
