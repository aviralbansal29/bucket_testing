import * as React from 'react';
import {
  BooleanField,
  Datagrid,
  DeleteButton,
  EditButton,
  List,
  TextField,
} from 'react-admin';

const ListExperiments = () => (
  <List>
    <Datagrid rowClick="show">
      <TextField source="name" />
      <BooleanField source="is_available" />
      <EditButton />
      <DeleteButton />
    </Datagrid>
  </List>
);

export default ListExperiments;
