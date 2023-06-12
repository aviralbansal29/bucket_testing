import React from 'react'

import {
  Datagrid,
  DeleteButton,
  Edit,
  List,
  NumberField,
  Show,
  SimpleForm,
  SimpleShowLayout,
  TextField,
  TextInput,
  useRecordContext,
  useUpdate
} from 'react-admin'

const EditVariant: React.FC = () => {
  const record = useRecordContext()
  const [update] = useUpdate()
  const save = React.useCallback(
    async (values: any) => {
      try {
        await update(
          'variants',
          { id: record.id, data: values },
          { returnPromise: true }
        )
      } catch (error) {
        if (
          error instanceof Error
          && error.hasOwnProperty('body')
          && error.body
          && error.body.errors
        ) {
          return error.body.errors
        }
        throw error
      }
    },
    [update, false]
  )
  return (
    <Edit resource='variants' id={record.id} redirect={false} mutationMode='pessimistic'>
      <SimpleForm onSubmit={save}>
        <TextInput source='name' />
        <TextInput source='description' />
        <TextInput source='weightage' />
      </SimpleForm>
    </Edit>
  )
}

const VariantList: React.FC = () => {
  const record = useRecordContext()
  return (
    <List resource='variants' filter={{ experiment_id: record?.id }}>
      <Datagrid expand={<EditVariant />}>
        <TextField source='name' />
        <TextField source='description' />
        <TextField source='weightage' />
        <DeleteButton />
      </Datagrid>
    </List>
  )
}

const ShowExperiment: React.FC = () => (
  <Show>
    <SimpleShowLayout>
      <NumberField source="id" />
      <TextField source="name" />
      <TextField source="description" />
      <VariantList />
    </SimpleShowLayout>
  </Show>
)

export default ShowExperiment
