import React from 'react'
import { Datagrid, List, ReferenceField, TextField } from 'react-admin'

const ListVariants: React.FC = () => (
  <List>
    <Datagrid>
      <TextField source='name' />
      <ReferenceField source='experiment_id' reference='experiments' />
    </Datagrid>
  </List>
)

export default ListVariants;
