import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  server:{
proxy:{
  '/api':'http://localhost:8080'
}
  },
  plugins: [react()],
  build: {
    outDir: '../backend/src/main/resources/static/',
    emptyOutDir: true,
  }
}) 
 
// export default defineConfig({
//   plugins: [react()],
//   build: {
//     emptyOutDir: true
//   },
// });