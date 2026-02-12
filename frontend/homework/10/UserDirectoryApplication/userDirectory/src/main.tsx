import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import ErrorBoundary from './Components/ErrorBoundary.tsx'
import { Provider } from 'react-redux'
import { store } from './UsersRedux/store.ts'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Provider store={store}>
    <ErrorBoundary fallback = "Something went wrong">
      <App />
    </ErrorBoundary>
    </Provider>
  </StrictMode>,
)
