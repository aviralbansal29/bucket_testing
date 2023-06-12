import * as React from 'react'
import { Create, ReferenceInput, required, SimpleForm, TextInput } from 'react-admin'

const CreateVariant: React.FC = () => (
  <Create>
    <SimpleForm>
      <TextInput source="name" validate={[required()]} fullWidth />
      <TextInput source="description" validate={[required()]} fullWidth />
      <TextInput source="weightage" validate={[required()]} fullWidth />
      <ReferenceInput source="experiment_id" validate={[required()]} reference='experiments' fullWidth />
    </SimpleForm>
  </Create>
)

export default CreateVariant
