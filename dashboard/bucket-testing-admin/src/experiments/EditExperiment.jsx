import * as React from 'react';
import {Edit, required, SimpleForm, TextInput} from 'react-admin';

const EditExperiment = () => (
  <Edit>
    <SimpleForm>
      <TextInput source="name" validate={[required()]} fullWidth />
      <TextInput source="description" validate={[required()]} fullWidth />
    </SimpleForm>
  </Edit>
);

export default EditExperiment;
