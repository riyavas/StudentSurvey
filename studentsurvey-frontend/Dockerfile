# TO CONTAINERIZE AN ANGULAR APPLICATION ON DOCKERHUB.

# Build the Angular Application
FROM node:latest as node
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod

# Move it to the correct nginx directory
FROM nginx:alpine
COPY --from=node /app/dist/studentsurvey-frontend /usr/share/nginx/html