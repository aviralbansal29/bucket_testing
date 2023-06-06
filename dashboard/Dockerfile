FROM node:20 as react-build

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY bucket-testing-admin/package.json ./
RUN yarn install

COPY bucket-testing-admin ./

CMD ["yarn", "dev"]
