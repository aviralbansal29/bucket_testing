import {NumberField, Show, SimpleShowLayout, TextField} from 'react-admin';

const ShowExperiment = () => (
  <Show>
    <SimpleShowLayout>
      <NumberField source="id" />
      <TextField source="name" />
      <TextField source="description" />
    </SimpleShowLayout>
  </Show>
);

export default ShowExperiment;
