import React from 'react'

import {
  Admin,
  Resource
} from 'react-admin'
import { dataProvider } from './dataProvider'
import CreateExperiment from './experiments/CreateExperiment'
import EditExperiment from './experiments/EditExperiment'
import ListExperiments from './experiments/ListExperiments'
import ShowExperiment from './experiments/ShowExperiment'
import CreateVariant from './variants/CreateVariant'
import ListVariants from './variants/ListVariants'

export const App: React.FC = () => (
  <Admin dataProvider={dataProvider}>
    <Resource name='experiments'
      create={CreateExperiment}
      list={ListExperiments}
      edit={EditExperiment}
      show={ShowExperiment}
    />
    <Resource name='variants'
      create={CreateVariant}
      list={ListVariants}
    />
  </Admin>
)
