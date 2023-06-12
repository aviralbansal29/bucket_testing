import * as React from 'react';
import {Create, required, SimpleForm, TextInput} from 'react-admin';

const CreateExperiment = () => (
  <Create>
    <SimpleForm>
      <TextInput source="name" validate={[required()]} fullWidth />
      <TextInput source="description" validate={[required()]} fullWidth />
    </SimpleForm>
  </Create>
);

export default CreateExperiment;
