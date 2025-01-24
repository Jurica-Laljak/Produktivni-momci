# FROM nginx:alpine

# # Kopira dist folder sadržaj sa stroja u nginx folder u Docker containeru
# COPY dist /usr/share/nginx/html

# # Izlozi port
# EXPOSE 80

# # Kopira konfiguraciju nginx-a
# COPY nginx.conf /etc/nginx/conf.d/default.template

# # postavlja entrypoint
# ENTRYPOINT ["/bin/sh", "-c", "envsubst '${VITE_BACKEND_URL}' < /etc/nginx/conf.d/default.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]

# Build stage
FROM node:18 AS build

WORKDIR /app

# Copy package.json and package-lock.json to the container
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the React app
RUN npm run build

# Serve stage
FROM nginx:alpine

# Copy the custom nginx.conf file to the container
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy the built React app from the build stage to the nginx container
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]