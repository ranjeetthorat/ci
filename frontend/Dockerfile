FROM node:20-alpine AS build
WORKDIR /app
COPY pacakge*.json ./
RUN npm install
COPY . .
RUN npm build

FROM node:20-alpine
WORKDIR /app
COPY --from=build /app/pacakge*.json ./
COPY --from=build /app/.next ./.next
COPY --from=build /app/public ./public
RUN npm install --production
EXPOSE 3000
CMD ["npm","start"]
