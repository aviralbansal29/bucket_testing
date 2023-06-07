import * as React from 'react';

import {
  Admin,
  Resource,
} from 'react-admin';
import {dataProvider} from './dataProvider';
import CreateExperiment from './experiments/CreateExperiment';
import EditExperiment from './experiments/EditExperiment';
import ListExperiments from './experiments/ListExperiments';
import ShowExperiment from './experiments/ShowExperiment';

export const App = () => (
  <Admin dataProvider={dataProvider}>
    <Resource name='experiments'
      create={CreateExperiment}
      list={ListExperiments}
      edit={EditExperiment}
      show={ShowExperiment}
    />
  </Admin>
);
